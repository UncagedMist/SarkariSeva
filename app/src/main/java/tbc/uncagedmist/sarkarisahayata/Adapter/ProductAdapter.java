package tbc.uncagedmist.sarkarisahayata.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

import tbc.uncagedmist.sarkarisahayata.Common.Common;
import tbc.uncagedmist.sarkarisahayata.Model.Product;
import tbc.uncagedmist.sarkarisahayata.ProductsActivity;
import tbc.uncagedmist.sarkarisahayata.R;
import tbc.uncagedmist.sarkarisahayata.Service.IRecyclerItemSelectListener;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

  Context context;
  List<Product> productList;
  List<CardView> cardViewList;
  private InterstitialAd mInterstitialAd;

  public ProductAdapter(Context context, List<Product> productList) {
    this.context = context;
    this.productList = productList;
    cardViewList = new ArrayList<>();
  }

  @NonNull
  @Override
  public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context)
            .inflate(R.layout.layout_products,parent,false);

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

    return new ProductViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
    Picasso.get()
            .load(productList.get(position).getImage())
            .into(holder.productImage);

    holder.productName.setText(productList.get(position).getName());

    holder.cardProducts.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (mInterstitialAd != null) {
          mInterstitialAd.show((Activity) context);
        }
        else {
          Log.d("Ad Error", "The interstitial wasn't loaded yet.");
          Intent intent = new Intent(context, ProductsActivity.class);
          Common.CurrentProduct = productList.get(position);
          context.startActivity(intent);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return productList.size();
  }

  public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView productImage;
    TextView productName;
    CardView cardProducts;

    IRecyclerItemSelectListener iRecyclerItemSelectListener;

    public void setiRecyclerItemSelectListener(IRecyclerItemSelectListener iRecyclerItemSelectListener) {
      this.iRecyclerItemSelectListener = iRecyclerItemSelectListener;
    }

    public ProductViewHolder(@NonNull View itemView) {
      super(itemView);
      productImage = itemView.findViewById(R.id.product_image);
      productName = itemView.findViewById(R.id.product_name);
      cardProducts = itemView.findViewById(R.id.card_products);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      iRecyclerItemSelectListener.onItemSelected(view,getAdapterPosition());
    }
  }
}