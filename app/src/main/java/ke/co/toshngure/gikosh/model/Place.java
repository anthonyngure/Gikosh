package ke.co.toshngure.gikosh.model;

import com.mobisys.android.autocompleteview.annotations.ViewId;

import ke.co.toshngure.gikosh.R;

/**
 * Created by Anthony Ngure on 03/01/2018.
 * Email : anthonyngure25@gmail.com.
 */

public class Place {
    private static final String PLACE_IMAGE_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photoreference=%s&sensor=false&key=AIzaSyDhFGUWlyd0KsjPQ59ATr-yL0bQKujHmeg";
    private String name;
    private String photoReference;

    @ViewId(id = R.id.name)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    @ViewId(id = R.id.image)
    public String getImageUrl() {
        String url = String.format(PLACE_IMAGE_URL, photoReference);
        return url;
    }
}
