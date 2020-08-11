package tbc.uncagedmist.sarkariseva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tbc.uncagedmist.sarkariseva.Adapter.ServiceAdapter;
import tbc.uncagedmist.sarkariseva.Common.Common;
import tbc.uncagedmist.sarkariseva.Model.Product;
import tbc.uncagedmist.sarkariseva.Model.Service;
import tbc.uncagedmist.sarkariseva.Service.IAllProductLoadListener;

public class ProductsActivity extends AppCompatActivity implements IAllProductLoadListener {

    AdView productBanner;
    RecyclerView recyclerService;

    FloatingActionButton productShare;
    CollectionReference refAllProducts;

    IAllProductLoadListener iAllProductLoadListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        recyclerService = findViewById(R.id.recycler_service);
        productBanner = findViewById(R.id.productBanner);
        productShare = findViewById(R.id.productShare);

        AppBarLayout toolbar = findViewById(R.id.app_bar);
        TextView txtTitle = toolbar.findViewById(R.id.tool_title);

        txtTitle.setText(Common.CurrentProduct.getName());

        AdRequest adRequest = new AdRequest.Builder().build();
        productBanner.loadAd(adRequest);

        getAllProducts();

        productShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String message = "Never Miss an Sarkari Updates. Install Sarkari Seva and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.sarkariseva";
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Share Sarkari Seva Using"));
            }
        });

        iAllProductLoadListener = this;

        productBanner.setAdListener(new AdListener()   {
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

    private void getAllProducts() {
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