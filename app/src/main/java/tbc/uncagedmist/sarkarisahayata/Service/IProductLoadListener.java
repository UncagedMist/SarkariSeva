package tbc.uncagedmist.sarkarisahayata.Service;

import java.util.List;

import tbc.uncagedmist.sarkarisahayata.Model.Product;

public interface IProductLoadListener {

    void onProductLoadSuccess(List<Product> products);
    void onProductLoadFailed(String message);
}