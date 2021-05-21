package tbc.uncagedmist.sarkarisahayata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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


import java.util.ArrayList;
import java.util.List;

import tbc.uncagedmist.sarkarisahayata.Adapter.DetailAdapter;
import tbc.uncagedmist.sarkarisahayata.Common.Common;
import tbc.uncagedmist.sarkarisahayata.Helper.CustomLoadDialog;
import tbc.uncagedmist.sarkarisahayata.Model.Detail;
import tbc.uncagedmist.sarkarisahayata.Service.IDetailsLoadListener;

public class DetailActivity extends AppCompatActivity implements IDetailsLoadListener {

    AdView detailBanner, aboveBanner;
    RecyclerView recyclerDetail;

    CollectionReference refDetails;

    FloatingActionButton detailShare;

    IDetailsLoadListener iDetailsLoadListener;


    CustomLoadDialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        loadDialog = new CustomLoadDialog(this);

        recyclerDetail = findViewById(R.id.recycler_detail);
        detailBanner = findViewById(R.id.detailBanner);
        detailShare = findViewById(R.id.detailShare);
        aboveBanner = findViewById(R.id.detailAboveBanner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Common.CurrentService.getName());

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
                getSupportActionBar().setTitle(Common.CurrentService.getName());
                getDetails();
            }
        });
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
}