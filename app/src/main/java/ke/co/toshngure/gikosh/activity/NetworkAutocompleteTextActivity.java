package ke.co.toshngure.gikosh.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mobisys.android.autocompleteview.AutoCompleteView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ke.co.toshngure.gikosh.R;
import ke.co.toshngure.gikosh.model.Place;
import ke.co.toshngure.gikosh.view.autocomplete.NetworkAutocompleteTextView;
import ke.co.toshngure.gikosh.view.autocomplete.NetworkAutocompleteTextViewAdapter;

public class NetworkAutocompleteTextActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, NetworkAutocompleteTextActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_autocomplete_text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NetworkAutocompleteTextView networkAutocompleteTextView = findViewById(R.id.networkAutoCompleteTV);
        networkAutocompleteTextView.setThreshold(3);
        networkAutocompleteTextView.setAdapter(new NetworkAutocompleteTextViewAdapter(this)); // 'this' is Activity instance
        networkAutocompleteTextView.setLoadingIndicator(findViewById(R.id.progressBar));
        networkAutocompleteTextView.setOnItemClickListener((adapterView, view, position, id)
                -> toastDebug((String) adapterView.getItemAtPosition(position)));

        AutoCompleteView autoCompleteView = findViewById(R.id.networkAutoCompleteTV2);
        autoCompleteView.setParser(new AutoCompleteView.AutoCompleteResponseParser() {
            @Override
            public ArrayList<? extends Object> parseAutoCompleteResponse(String response) {
                ArrayList<Place> places = null;
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    final JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

                    places = new ArrayList<Place>();
                    for (int i = 0; i < predsJsonArray.length(); i++) {
                        String placeName = predsJsonArray.getJSONObject(i).getString("description");
                        String placeReference = predsJsonArray.getJSONObject(i).getString("reference");

                        Place place = new Place();
                        place.setName(placeName);
                        place.setPhotoReference(placeReference);
                        places.add(place);
                    }
                } catch (JSONException e) {
                    Log.e("AppUtil", "Cannot process JSON results", e);
                }

                return places;
            }
        });
    }

}
