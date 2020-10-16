package tbc.uncagedmist.sarkarisahayata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
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
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import tbc.uncagedmist.sarkarisahayata.Adapter.DetailAdapter;
import tbc.uncagedmist.sarkarisahayata.Common.Common;
import tbc.uncagedmist.sarkarisahayata.Helper.CustomLoadDialog;
import tbc.uncagedmist.sarkarisahayata.Model.Detail;
import tbc.uncagedmist.sarkarisahayata.Service.IDetailsLoadListener;

public class DetailActivity extends AppCompatActivity implements IDetailsLoadListener, View.OnClickListener {

    AdView detailBanner, aboveBanner;
    RecyclerView recyclerDetail;

    CollectionReference refDetails;

    FloatingActionButton detailShare;

    IDetailsLoadListener iDetailsLoadListener;

    NoInternetDialog noInternetDialog;

    private InterstitialAd mInterstitialAd;

    CustomLoadDialog loadDialog;

    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemAbout;
    private ResideMenuItem itemPrivacy;
    private ResideMenuItem itemSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        loadDialog = new CustomLoadDialog(this);

        noInternetDialog = new NoInternetDialog.Builder(DetailActivity.this).build();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5860770870597755/3145207391");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        recyclerDetail = findViewById(R.id.recycler_detail);
        detailBanner = findViewById(R.id.detailBanner);
        detailShare = findViewById(R.id.detailShare);
        aboveBanner = findViewById(R.id.detailAboveBanner);

        TextView txtTitle = findViewById(R.id.txtTitle);

        txtTitle.setText(Common.CurrentService.getName());

        setUpResideMenu();

        AdRequest adRequest = new AdRequest.Builder().build();

        detailBanner.loadAd(adRequest);
        aboveBanner.loadAd(adRequest);

        detailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String message = "Never Miss A Sarkari Update. Install Sarkari Sahayata and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.sarkarisahayata";
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Share Sarkari Sahayata Using"));
            }
        });

        loadInterstitial();
        getDetails();

        iDetailsLoadListener = this;

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
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

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
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
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
        refDetails.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)  {
                    return;
                }
                loadInterstitial();
                getDetails();
            }
        });
    }

    private void loadInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    private void getDetails() {

        loadDialog.showDialog();

        refDetails = FirebaseFirestore.getInstance()
                .collection("Sarkari")
                .document(Common.CurrentProduct.getId())
                .collection("Services")
                .document(Common.CurrentService.getId())
                .collection("Details");

        refDetails
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Detail> details = new ArrayList<>();
                        if (task.isSuccessful())    {
                            for (QueryDocumentSnapshot detailSnapshot : task.getResult())   {
                                Detail detail = detailSnapshot.toObject(Detail.class);
                                detail.setId(detailSnapshot.getId());
                                details.add(detail);
                            }
                            iDetailsLoadListener.onDetailLoadSuccess(details);
                            loadDialog.hideDialog();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iDetailsLoadListener.onDetailLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onDetailLoadSuccess(List<Detail> details) {
        recyclerDetail.setHasFixedSize(true);
        recyclerDetail.setLayoutManager(new LinearLayoutManager(this));

        recyclerDetail.setAdapter(new DetailAdapter(this,details));
    }

    @Override
    public void onDetailLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }

    private void setUpResideMenu() {

    resideMenu = new ResideMenu(this);
//        resideMenu.setUse3D(true);
    resideMenu.setBackground(R.drawable.menu_background);
    resideMenu.attachToActivity(this);
    resideMenu.setMenuListener(menuListener);

    resideMenu.setScaleValue(0.6f);

    itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
    itemAbout  = new ResideMenuItem(this, R.drawable.icon_profile,  "About");
    itemPrivacy = new ResideMenuItem(this, R.drawable.icon_profile, "Privacy");
    itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");

    itemHome.setOnClickListener(this);
    itemAbout.setOnClickListener(this);
    itemPrivacy.setOnClickListener(this);
    itemSettings.setOnClickListener(this);

    resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
    resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_LEFT);
    resideMenu.addMenuItem(itemPrivacy, ResideMenu.DIRECTION_RIGHT);
    resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);

    findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
        }
    });
    findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
        }
    });
}

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
        }
        else if (view == itemAbout){
            startActivity(new Intent(DetailActivity.this,AboutActivity.class));
        }
        else if (view == itemPrivacy){
            startActivity(new Intent(DetailActivity.this,PrivacyActivity.class));

        }
        else if (view == itemSettings){
            startActivity(new Intent(DetailActivity.this,SettingActivity.class));
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        }

        @Override
        public void closeMenu() {
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}