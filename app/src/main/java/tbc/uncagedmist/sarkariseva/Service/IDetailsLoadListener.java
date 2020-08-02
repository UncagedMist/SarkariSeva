package tbc.uncagedmist.sarkariseva.Service;

import java.util.List;

import tbc.uncagedmist.sarkariseva.Model.Detail;

public interface IDetailsLoadListener {

    void onDetailLoadSuccess(List<Detail> details);
    void onDetailLoadFailed(String message);
}