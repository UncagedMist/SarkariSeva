package tbc.uncagedmist.sarkariseva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;
import tbc.uncagedmist.sarkariseva.Adapter.BannerSliderAdapter;
import tbc.uncagedmist.sarkariseva.Adapter.ProductAdapter;
import tbc.uncagedmist.sarkariseva.Common.Common;
import tbc.uncagedmist.sarkariseva.Model.Banner;
import tbc.uncagedmist.sarkariseva.Model.Product;
import tbc.uncagedmist.sarkariseva.Service.IBannerLoadListener;
import tbc.uncagedmist.sarkariseva.Service.IProductLoadListener;
import tbc.uncagedmist.sarkariseva.Service.PicassoImageLoadingService;

public class MainActivity extends AppCompatActivity implements IProductLoadListener, IBannerLoadListener {

    AdView mainBanner;

    Slider bannerSlider;
    RecyclerView recyclerView;

    CollectionReference refProducts,refBanner;

    IProductLoadListener iProductLoadListener;
    IBannerLoadListener iBannerLoadListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refProducts = FirebaseFirestore.getInstance().collection("Sarkari");
        refBanner = FirebaseFirestore.getInstance().collection("Banner");

        Slider.init(new PicassoImageLoadingService());

        mainBanner = findViewById(R.id.mainBanner);
        bannerSlider = findViewById(R.id.banner_slider);
        recyclerView = findViewById(R.id.recyclerView);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(recyclerView.getContext(),
                R.anim.layout_fall_down);
        recyclerView.setLayoutAnimation(controller);

        AdRequest adRequest = new AdRequest.Builder().build();
        mainBanner.loadAd(adRequest);

        iProductLoadListener = this;
        iBannerLoadListener = this;

        loadBanners();
        loadProducts();

        mainBanner.setAdListener(new AdListener()   {
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

    private void loadBanners() {
        refBanner.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Banner> banners = new ArrayList<>();
                        if (task.isSuccessful())    {
                            for (QueryDocumentSnapshot bannerSnapshot : task.getResult())   {
                                Banner banner = bannerSnapshot.toObject(Banner.class);
                                banners.add(banner);
                            }
                            iBannerLoadListener.onBannerLoadSuccess(banners);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iBannerLoadListener.onBannerLoadFailed(e.getMessage());
            }
        });
    }

    private void loadProducts() {
        refProducts.get()
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
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        recyclerView.setAdapter(new ProductAdapter(this,products));
    }

    @Override
    public void onProductLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBannerLoadSuccess(List<Banner> banners) {
        bannerSlider.setAdapter(new BannerSliderAdapter(banners));
    }

    @Override
    public void onBannerLoadFailed(String message) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
    }
}