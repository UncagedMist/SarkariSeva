package tbc.uncagedmist.sarkarisahayata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import tbc.uncagedmist.sarkarisahayata.Adapter.ServiceAdapter;
import tbc.uncagedmist.sarkarisahayata.Common.Common;
import tbc.uncagedmist.sarkarisahayata.Model.Service;
import tbc.uncagedmist.sarkarisahayata.Service.IAllProductLoadListener;

public class ProductsActivity extends AppCompatActivity implements IAllProductLoadListener {

    AdView productBanner,aboveBanner;
    RecyclerView recyclerService;

    FloatingActionButton productShare;
    CollectionReference refAllProducts;

    IAllProductLoadListener iAllProductLoadListener;

    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        alertDialog = new SpotsDialog(this);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        recyclerService = findViewById(R.id.recycler_service);
        productBanner = findViewById(R.id.productBanner);
        productShare = findViewById(R.id.productShare);
        aboveBanner = findViewById(R.id.productAboveBanner);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        TextView txtTitle = toolbar.findViewById(R.id.tool_title);

        txtTitle.setText(Common.CurrentProduct.getName());

        AdRequest adRequest = new AdRequest.Builder().build();
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
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
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
        refAllProducts.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)  {
                    return;
                }

                getAllProducts();
            }
        });
    }

    private void getAllProducts() {
        alertDialog.show();
        refAllProducts = FirebaseFirestore.getInstance()
                .collection("Sarkari")
                .document(Common.CurrentProduct.getId())
                .collection("Services");

        refAllProducts.get()
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
                            alertDialog.dismiss();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_about)  {
            startActivity(new Intent(ProductsActivity.this,AboutActivity.class));
        }
        else if (id == R.id.action_privacy) {
            startActivity(new Intent(ProductsActivity.this,PrivacyActivity.class));
        }
        return super.onOptionsItemSelected(item);
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