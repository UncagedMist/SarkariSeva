package tbc.uncagedmist.sarkarisahayata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tbc.uncagedmist.sarkarisahayata.Adapter.ServiceAdapter;
import tbc.uncagedmist.sarkarisahayata.Common.Common;
import tbc.uncagedmist.sarkarisahayata.Helper.CustomLoadDialog;
import tbc.uncagedmist.sarkarisahayata.Model.Service;
import tbc.uncagedmist.sarkarisahayata.Service.IAllProductLoadListener;

public class ProductsActivity extends AppCompatActivity
        implements IAllProductLoadListener {

    AdView productBanner,aboveBanner;
    RecyclerView recyclerService;

    FloatingActionButton productShare;
    CollectionReference refAllProducts;

    IAllProductLoadListener iAllProductLoadListener;

    CustomLoadDialog loadDialog;

    private RewardedAd mRewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        loadDialog = new CustomLoadDialog(this);

        recyclerService = findViewById(R.id.recycler_service);
        productBanner = findViewById(R.id.productBanner);
        productShare = findViewById(R.id.productShare);
        aboveBanner = findViewById(R.id.productAboveBanner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-5860770870597755/62600193060",
                adRequest, new RewardedAdLoadCallback(){
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("TAG", loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                mRewardedAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Don't forget to set the ad reference to null so you
                                // don't show the ad a second time.
                            }
                        });
                    }
                });

        getSupportActionBar().setTitle(Common.CurrentProduct.getName());

        loadRewardedVideoAd();

        productBanner.loadAd(adRequest);
        aboveBanner.loadAd(adRequest);

        getAllProducts();

        productShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String message = "Never Miss A Sarkari Update. Install Sarkari Sahayata and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.sarkarisahayata";
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Share Sarkari Sahayata Using"));
            }
        });

        iAllProductLoadListener = this;

        aboveBanner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }


            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        productBanner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        refAllProducts.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)  {
                    return;
                }
                getSupportActionBar().setTitle(Common.CurrentProduct.getName());
                getAllProducts();
            }
        });

    }

    private void loadRewardedVideoAd() {
        if (mRewardedAd != null) {
            Activity activityContext = ProductsActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                }
            });
        }
        else {
            Log.d("TAG", "loadRewardedVideoAd: Ad Not Loaded");
        }
    }

    private void getAllProducts() {
        loadDialog.showDialog();
        refAllProducts = FirebaseFirestore.getInstance()
                .collection("Sarkari")
                .document(Common.CurrentProduct.getId())
                .collection("Services");

        refAllProducts
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Service> services = new ArrayList<>();
                        if (task.isSuccessful())    {

                            for (QueryDocumentSnapshot productSnapshot : task.getResult())  {
                                Service service = productSnapshot.toObject(Service.class);
                                service.setId(productSnapshot.getId());
                                services.add(service);

                            }
                            iAllProductLoadListener.onAllProductLoadSuccess(services);
                            loadDialog.hideDialog();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        iAllProductLoadListener.onAllProductLoadFailed(e.getMessage());
                    }
                });
    }

    @Override
    public void onAllProductLoadSuccess(List<Service> allProductList) {
        recyclerService.setHasFixedSize(true);
        recyclerService.setLayoutManager(new GridLayoutManager(this,2));

        recyclerService.setAdapter(new ServiceAdapter(this,allProductList));
    }

    @Override
    public void onAllProductLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }
}