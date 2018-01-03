package ke.co.toshngure.gikosh.view.autocomplete;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ke.co.toshngure.basecode.log.BeeLog;

/**
 * Created by Anthony Ngure on 6/24/15.
 * Email : anthonyngure25@gmail.com.
 */
public class AutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = "AutoCompleteAdapter";

    private String mAutocompleteUrl;
    private Context mContext;

    private List<String> resultList = new ArrayList<>();
    private AutoCompleteView.Parser mParser;

    AutoCompleteAdapter(Context context, String autocompleteUrl) {
        this.mAutocompleteUrl = autocompleteUrl;
        this.mContext = context;
    }

    private static String connect(String urlString) {
        BeeLog.i(TAG, "URL ==> " + urlString);
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {

            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[512];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }

        } catch (MalformedURLException e) {
            BeeLog.i(TAG, "Error processing Autocomplete API URL");
            BeeLog.e(TAG, e);
        } catch (IOException e) {
            BeeLog.i(TAG, "Error connecting to Autocomplete API");
            BeeLog.e(TAG, e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        BeeLog.i(TAG, "Result " + jsonResults.toString());
        return jsonResults.toString();
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        ((TextView) convertView.findViewById(android.R.id.text1)).setText(resultList.get(position));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence != null) {
                    List<String> suggestions = getResponse(charSequence.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private List<String> getResponse(final String query) {
        String url = appendInput(query);
        String response = connect(url);
        List<String> displayList = mParser.parse(response);
        return displayList;
    }

    private String appendInput(String query) {
        return mAutocompleteUrl + Uri.encode(query);
    }

    void setAutocompleteUrl(String autocompleteUrl) {
        this.mAutocompleteUrl = autocompleteUrl;
    }

    public void setParser(AutoCompleteView.Parser parser) {
        this.mParser = parser;
    }
}
