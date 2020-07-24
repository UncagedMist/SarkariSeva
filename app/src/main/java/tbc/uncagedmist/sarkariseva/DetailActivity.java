package tbc.uncagedmist.sarkariseva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tbc.uncagedmist.sarkariseva.Adapter.DetailAdapter;
import tbc.uncagedmist.sarkariseva.Common.Common;
import tbc.uncagedmist.sarkariseva.Model.Detail;
import tbc.uncagedmist.sarkariseva.Service.IDetailsLoadListener;

public class DetailActivity extends AppCompatActivity implements IDetailsLoadListener {

    AdView detailBanner;
    RecyclerView recyclerDetail;

    CollectionReference refDetails;

    TextView txtTitle;

    IDetailsLoadListener iDetailsLoadListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        recyclerDetail = findViewById(R.id.recycler_detail);
        detailBanner = findViewById(R.id.detailBanner);

        AppBarLayout toolbar = findViewById(R.id.app_bar);
        txtTitle = toolbar.findViewById(R.id.tool_title);

        txtTitle.setText(Common.CurrentService.getName());

        AdRequest adRequest = new AdRequest.Builder().build();
        detailBanner.loadAd(adRequest);

        getDetails();

        iDetailsLoadListener = this;

        detailBanner.setAdListener(new AdListener()   {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
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

    private void getDetails() {
        refDetails = FirebaseFirestore.getInstance()
                .collection("Sarkari")
                .document(Common.CurrentProduct.getId())
                .collection("Services")
                .document(Common.CurrentService.getId())
                .collection("Details");

        refDetails.get()
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