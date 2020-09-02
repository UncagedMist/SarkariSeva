package tbc.uncagedmist.sarkarisahayata.Service;

import java.util.List;

import tbc.uncagedmist.sarkarisahayata.Model.Detail;

public interface IDetailsLoadListener {

    void onDetailLoadSuccess(List<Detail> details);
    void onDetailLoadFailed(String message);
}