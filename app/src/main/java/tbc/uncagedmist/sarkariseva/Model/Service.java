package tbc.uncagedmist.sarkariseva.Model;

import com.google.firebase.firestore.Exclude;

public class Service {

    private String id,name, image;

    public Service() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
