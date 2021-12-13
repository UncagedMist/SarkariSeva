package tbc.uncagedmist.sarkarisahayata.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tbc.uncagedmist.sarkarisahayata.AdUtility.GoogleAds;
import tbc.uncagedmist.sarkarisahayata.Common.Common;
import tbc.uncagedmist.sarkarisahayata.Common.MyApplicationClass;
import tbc.uncagedmist.sarkarisahayata.Fragments.ResultFragment;
import tbc.uncagedmist.sarkarisahayata.Model.State;
import tbc.uncagedmist.sarkarisahayata.R;
import tbc.uncagedmist.sarkarisahayata.Service.IRecyclerItemSelectListener;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {

    Context context;
    ArrayList<State> states;

    public StateAdapter(Context context, ArrayList<State> states) {
        this.context = context;
        this.states = states;
    }

    @NonNull
    @Override
    public StateAdapter.StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_details,parent,false);

        if (MyApplicationClass.getInstance().isShowAds())   {
            GoogleAds.loadGoogleFullscreen(context);
        }

        return new StateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.StateViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.stateName.setText(states.get(position).getStateName());
        holder.stateDesc.setText(states.get(position).getStateDesc());

        Picasso.get()
                .load(states.get(position).getStateImage())
                .into(holder.stateImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(R.mipmap.ic_launcher)
                                .into(holder.stateImage);
                    }
                });

        holder.stateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GoogleAds.mInterstitialAd != null)  {
                    GoogleAds.mInterstitialAd.show((Activity) context);
                }
                else {
                    ResultFragment detailFragment = new ResultFragment();
                    FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();

                    Common.CurrentStateId = states.get(position).getStateId();
                    Common.CurrentStateName = states.get(position).getStateName();
                    Common.CurrentStateUrl = states.get(position).getStateUrl();
                    transaction.replace(R.id.main_frame,detailFragment).commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class StateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView stateImage;
        TextView stateName, stateDesc;
        RelativeLayout stateCard;

        IRecyclerItemSelectListener iRecyclerItemSelectListener;

        public void setiRecyclerItemSelectListener(IRecyclerItemSelectListener iRecyclerItemSelectListener) {
            this.iRecyclerItemSelectListener = iRecyclerItemSelectListener;
        }

        public StateViewHolder(@NonNull View itemView) {
            super(itemView);

            stateImage = itemView.findViewById(R.id.stateImg);
            stateName = itemView.findViewById(R.id.stateName);
            stateDesc = itemView.findViewById(R.id.stateDesc);
            stateCard = itemView.findViewById(R.id.stateCard);

            stateName.setSelected(true);
            stateDesc.setSelected(true);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectListener.onItemSelected(view,getAdapterPosition());
        }
    }
}
