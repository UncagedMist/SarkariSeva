package tbc.uncagedmist.sarkarisahayata.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tbc.uncagedmist.sarkarisahayata.Adapter.ProductAdapter;
import tbc.uncagedmist.sarkarisahayata.Helper.CustomLoadDialog;
import tbc.uncagedmist.sarkarisahayata.Model.Product;
import tbc.uncagedmist.sarkarisahayata.R;
import tbc.uncagedmist.sarkarisahayata.Service.IProductLoadListener;

public class HomeFragment extends Fragment implements IProductLoadListener {

    View myFragment;

    AdView mainBanner,aboveBanner;

    private static HomeFragment INSTANCE = null;

    RecyclerView recyclerView;
    FloatingActionButton mainShare;

    CollectionReference refProducts;

    IProductLoadListener iProductLoadListener;

    CustomLoadDialog loadDialog;

    public static HomeFragment getInstance()    {

        if (INSTANCE == null)   {
            INSTANCE = new HomeFragment();
        }
        return INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        loadDialog = new CustomLoadDialog(getContext());

        refProducts = FirebaseFirestore.getInstance().collection("Sarkari");

        mainBanner = myFragment.findViewById(R.id.mainBanner);
        aboveBanner = myFragment.findViewById(R.id.mainAboveBanner);
        recyclerView = myFragment.findViewById(R.id.recyclerView);
        mainShare = myFragment.findViewById(R.id.mainShare);

        AdRequest adRequest = new AdRequest.Builder().build();

        mainBanner.loadAd(adRequest);
        aboveBanner.loadAd(adRequest);

        mainShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String message = "Never Miss A Sarkari Update. Install Sarkari Sahayata and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.sarkarisahayata";
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Share Sarkari Sahayata Using"));
            }
        });

        iProductLoadListener = this;
        loadProducts();

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

        mainBanner.setAdListener(new AdListener() {
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

        return myFragment;
    }

    private void loadProducts() {
        loadDialog.showDialog();
        refProducts
                .orderBy("name", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Product> products = new ArrayList<>();
                        if (task.isSuccessful())    {
                            for (QueryDocumentSnapshot productSnapshot : task.getResult())  {
                                Product product = productSnapshot.toObject(Product.class);
                                product.setId(productSnapshot.getId());
                                products.add(product);
                            }
                            iProductLoadListener.onProductLoadSuccess(products);
                            loadDialog.hideDialog();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iProductLoadListener.onProductLoadFailed(e.getMessage());
            }
        });
    }

    @Override
    public void onProductLoadSuccess(List<Product> products) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        recyclerView.setAdapter(new ProductAdapter(getContext(),products));
    }

    @Override
    public void onProductLoadFailed(String message) {
        Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
    }
}