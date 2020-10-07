package tbc.uncagedmist.sarkarisahayata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import dmax.dialog.SpotsDialog;
import tbc.uncagedmist.sarkarisahayata.Adapter.ProductAdapter;
import tbc.uncagedmist.sarkarisahayata.Model.Product;
import tbc.uncagedmist.sarkarisahayata.Service.IProductLoadListener;
import tbc.uncagedmist.sarkarisahayata.Service.NetworkStatusReceiver;

public class MainActivity extends AppCompatActivity implements IProductLoadListener {

   AdView mainBanner,aboveBanner;

   RecyclerView recyclerView;
   FloatingActionButton mainShare;

   CollectionReference refProducts;

   IProductLoadListener iProductLoadListener;

   AlertDialog alertDialog;
   NoInternetDialog noInternetDialog;

   NetworkStatusReceiver networkStatusReceiver;

   private InterstitialAd mInterstitialAd;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      MobileAds.initialize(this, "ca-app-pub-7920815986886474~5642992812");

      MobileAds.initialize(this, new OnInitializationCompleteListener() {
         @Override
         public void onInitializationComplete(InitializationStatus initializationStatus) {
         }
      });

      MobileAds.initialize(this, new OnInitializationCompleteListener() {
         @Override
         public void onInitializationComplete(InitializationStatus initializationStatus) {}
      });

      mInterstitialAd = new InterstitialAd(this);
      mInterstitialAd.setAdUnitId("ca-app-pub-7920815986886474/2058971028");
      mInterstitialAd.loadAd(new AdRequest.Builder().build());

      mInterstitialAd.setAdListener(new AdListener() {
         @Override
         public void onAdClosed() {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
         }

      });

      alertDialog = new SpotsDialog(this);
      alertDialog.setCanceledOnTouchOutside(false);
      alertDialog.setCancelable(false);

      Toolbar toolbar = findViewById(R.id.app_bar);
      setSupportActionBar(toolbar);
      TextView txtTitle = toolbar.findViewById(R.id.tool_title);

      txtTitle.setText(R.string.app_name);

      refProducts = FirebaseFirestore.getInstance().collection("Sarkari");

      mainBanner = findViewById(R.id.mainBanner);
      aboveBanner = findViewById(R.id.mainAboveBanner);
      recyclerView = findViewById(R.id.recyclerView);
      mainShare = findViewById(R.id.mainShare);

      LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this,
              R.anim.layout_fall_down);
      recyclerView.setLayoutAnimation(controller);

      AdRequest adRequest = new AdRequest.Builder().build();

      List<String> testDeviceIds = Arrays.asList("2E44FF2FE41B4A84DA0690667AF9595B","C28D3F7858AFA52D217602BDA4D22F8F");
      RequestConfiguration configuration =
              new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
      MobileAds.setRequestConfiguration(configuration);

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
      loadInterstitial();
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
         public void onAdLeftApplication() {
            // Code to be executed when the user has left the app.
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
      refProducts.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
         @Override
         public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
            if (error != null)  {
               return;
            }
            noInternetDialog = new NoInternetDialog.Builder(MainActivity.this).build();
            loadInterstitial();
            loadProducts();
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

   private void loadProducts() {
      alertDialog.show();
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
                       alertDialog.dismiss();
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
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.options_menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {

      int id = item.getItemId();

      if (id == R.id.action_about)  {
         startActivity(new Intent(MainActivity.this,AboutActivity.class));
      }
      else if (id == R.id.action_privacy) {
         startActivity(new Intent(MainActivity.this,PrivacyActivity.class));
      }
      return true;
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
   public void onBackPressed() {
      new TTFancyGifDialog.Builder(MainActivity.this)
              .setTitle("Good-Bye")
              .setMessage("Do You Want to Step Out?")
              .setPositiveBtnText("Rate US")
              .setPositiveBtnBackground("#22b573")
              .setNegativeBtnText("Exit")
              .setNegativeBtnBackground("#c1272d")
              .setGifResource(R.drawable.gif3)
              .isCancellable(false)
              .OnPositiveClicked(new TTFancyGifDialogListener() {
                 @Override
                 public void OnClick() {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.sarkarisahayata")));
                 }
              })
              .OnNegativeClicked(new TTFancyGifDialogListener() {
                 @Override
                 public void OnClick() {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                 }
              }).build();
   }

   @Override
   protected void onResume() {
      super.onResume();
      IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
      registerReceiver(networkStatusReceiver, intentFilter);
   }

   @Override
   protected void onPause() {
      super.onPause();
      if (networkStatusReceiver != null)
         unregisterReceiver(networkStatusReceiver);
   }

   @Override
   protected void onStop() {
      super.onStop();
      if (networkStatusReceiver != null)
         unregisterReceiver(networkStatusReceiver);
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      noInternetDialog.onDestroy();
   }
}