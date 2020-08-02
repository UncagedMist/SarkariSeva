package tbc.uncagedmist.sarkariseva.Service;

import java.util.List;

import tbc.uncagedmist.sarkariseva.Model.Banner;

public interface IBannerLoadListener {

    void onBannerLoadSuccess(List<Banner> banners);
    void onBannerLoadFailed(String message);
}