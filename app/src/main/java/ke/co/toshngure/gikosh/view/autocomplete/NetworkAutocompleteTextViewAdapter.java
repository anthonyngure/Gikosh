package ke.co.toshngure.gikosh.view.autocomplete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ke.co.toshngure.gikosh.R;

/**
 * Created by Anthony Ngure on 03/01/2018.
 * Email : anthonyngure25@gmail.com.
 */

public class NetworkAutocompleteTextViewAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<String> resultList = new ArrayList<>();

    public NetworkAutocompleteTextViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.network_autocomplete_text_view_list_item, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.textTV)).setText(getItem(position));
        return convertView;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<String> strings = findBooks(mContext, constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = strings;
                    filterResults.count = strings.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
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

    private List<String> findBooks(Context C, String s) {
        // GoogleBooksProtocol is a wrapper for the Google Books API
        /*GoogleBooksProtocol protocol = new GoogleBooksProtocol(context, MAX_RESULTS);
        return protocol.findBooks(bookTitle);*/
        return Arrays.asList(mContext.getResources().getStringArray(R.array.counties));
    }
}
