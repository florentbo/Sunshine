package be.broche.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    ArrayAdapter<String> adapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            updateWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateWeather() {
        FetchWeatherTask task = new FetchWeatherTask(adapter);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String cityId = preferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
        Log.v("cityId", "XXXXXXXXXXXXXXXXX " + cityId);
        task.execute(cityId);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //List<String> weeksForecast = Arrays.asList("Today", "Tomorow", "Weds");
        List<String> weeksForecast = new ArrayList<>();

        //ArrayAdapter<String>
         adapter = new ArrayAdapter<>
                    (getActivity(),
                        R.layout.list_item_forecast,
                        R.id.list_item_forecast_textview,
                            weeksForecast);


        ListView l = (ListView) rootView.findViewById(R.id.listview_forecast);
        l.setAdapter(adapter);


        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String dayItem = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, dayItem);
                startActivity(intent);
            }
        });


        return rootView;
    }

}
