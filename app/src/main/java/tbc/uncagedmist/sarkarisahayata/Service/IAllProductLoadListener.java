package tbc.uncagedmist.sarkarisahayata.Service;

import java.util.List;

import tbc.uncagedmist.sarkarisahayata.Model.Service;

public interface IAllProductLoadListener {
  void onAllProductLoadSuccess(List<Service> allProductList);
  void onAllProductLoadFailed(String message);
}