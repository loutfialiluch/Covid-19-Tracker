package com.example.covid_19tracker.ui.country;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountryFragment extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<CovidCountry> covidCountries;
    CovidCountryAdapter covidCountryAdapter;
    SearchView searchView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_country, container, false);
        recyclerView = root.findViewById(R.id.rvCovidCountry);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = root.findViewById(R.id.progress_circular_country);
        searchView = root.findViewById(R.id.mySearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(covidCountryAdapter != null)
                {
                    covidCountryAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });


        //Retrieve data from co-vid19 api
        getDataFromApi();
        //Fill and the recycler view and show it

        covidCountries = new ArrayList<>();

        return root;
    }

    private void getDataFromApi() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://corona.lmao.ninja/v2/countries";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            covidCountries.add(new CovidCountry(jsonObject.getString("country"), jsonObject.getString("cases")
                                    , jsonObject.getString("deaths")
                                    , jsonObject.getString("recovered"), jsonObject.getJSONObject("countryInfo").getString("flag")));
                        }
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext()
                                , DividerItemDecoration.VERTICAL);
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        Collections.sort(covidCountries);
                        covidCountryAdapter = new CovidCountryAdapter(covidCountries, getContext());
                        recyclerView.setAdapter(covidCountryAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

    }

}
