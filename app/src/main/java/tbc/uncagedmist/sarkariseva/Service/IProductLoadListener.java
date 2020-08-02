package tbc.uncagedmist.sarkariseva.Service;

import java.util.List;

import tbc.uncagedmist.sarkariseva.Model.Product;

public interface IProductLoadListener {

    void onProductLoadSuccess(List<Product> products);
    void onProductLoadFailed(String message);
}