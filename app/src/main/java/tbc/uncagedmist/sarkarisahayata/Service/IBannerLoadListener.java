package tbc.uncagedmist.sarkarisahayata.Service;

import java.util.List;

import tbc.uncagedmist.sarkarisahayata.Model.Banner;

public interface IBannerLoadListener {

  void onBannerLoadSuccess(List<Banner> banners);
  void onBannerLoadFailed(String message);
}