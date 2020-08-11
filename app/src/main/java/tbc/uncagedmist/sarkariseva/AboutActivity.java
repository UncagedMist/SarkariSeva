package tbc.uncagedmist.sarkariseva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;

public class AboutActivity extends AppCompatActivity {

   FancyAboutPage aboutPage;
   String version;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_about);

      aboutPage = findViewById(R.id.aboutPage);
      aboutPage.setCover(R.drawable.coverimg);
      aboutPage.setName("Kundan Kumar");
      aboutPage.setDescription("Android Developer | Android App, Game and Software Developer.");
      aboutPage.setAppIcon(R.mipmap.ic_logo);

      aboutPage.setAppName(getString(R.string.app_name));
      try {
         version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
      }
      catch (PackageManager.NameNotFoundException e) {
         e.printStackTrace();
      }
      aboutPage.setVersionNameAsAppSubTitle(version);
      aboutPage.setAppDescription("" +
              "Sarkari Seva is an Android app Designed to Help People .\n\n" +
              "This app Provides Information on All the latest Services offered by Central and State Government with beautiful ui. " +
              "People can opt for any available government services and Get benefited." +
              "It also offers to apply for available and keep the track of your Applied application.\n\n"+
              "A fresh new take on Material layouts. " +
              "It offers a beautiful ui and daily basis reminder notification to never miss to get any Government Updates.");

      aboutPage.addEmailLink("Kundan_kk52@outlook.com");
      aboutPage.addFacebookLink("https://www.facebook.com/TechByteCare/");
      aboutPage.addTwitterLink("https://twitter.com/TechByteCare");
      aboutPage.addLinkedinLink("https://www.linkedin.com/in/kundan-kumar-a82472167/");
      aboutPage.addGitHubLink("https://github.com/UncagedMist");
   }
}