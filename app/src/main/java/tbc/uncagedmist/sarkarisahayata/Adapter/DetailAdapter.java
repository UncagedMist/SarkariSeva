package tbc.uncagedmist.sarkarisahayata.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tbc.uncagedmist.sarkarisahayata.Common.Common;
import tbc.uncagedmist.sarkarisahayata.Model.Detail;
import tbc.uncagedmist.sarkarisahayata.R;
import tbc.uncagedmist.sarkarisahayata.ResultActivity;
import tbc.uncagedmist.sarkarisahayata.Service.IRecyclerItemSelectListener;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    Context context;
    List<Detail> detailList;

    private InterstitialAd mInterstitialAd;

    public DetailAdapter(Context context, List<Detail> detailList) {
        this.context = context;
        this.detailList = detailList;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_details,parent,false);

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(
                context,
                context.getString(R.string.Fullscreen),
                adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Log.d("TAG", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Log.d("TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });

        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, final int position) {
        Picasso.get()
                .load(detailList.get(position).getImage())
                .into(holder.imgDetail);

        holder.txtDetailName.setText(detailList.get(position).getName());

        holder.cardDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show((Activity) context);
                }
                else {
                    Log.d("Ad Error", "The interstitial wasn't loaded yet.");
                    Intent intent = new Intent(context, ResultActivity.class);
                    Common.CurrentDetail = detailList.get(position);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardDetail;
        TextView txtDetailName;
        CircleImageView imgDetail;

        IRecyclerItemSelectListener iRecyclerItemSelectListener;

        public void setiRecyclerItemSelectListener(IRecyclerItemSelectListener iRecyclerItemSelectListener) {
            this.iRecyclerItemSelectListener = iRecyclerItemSelectListener;
        }

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);

            cardDetail = itemView.findViewById(R.id.card_details);
            txtDetailName = itemView.findViewById(R.id.txtTitle);
            imgDetail = itemView.findViewById(R.id.avatar_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectListener.onItemSelected(view,getAdapterPosition());
        }
    }
}