package tbc.uncagedmist.sarkariseva.Service;

import java.util.List;

import tbc.uncagedmist.sarkariseva.Model.Service;

public interface IAllProductLoadListener {
  void onAllProductLoadSuccess(List<Service> allProductList);
  void onAllProductLoadFailed(String message);
}