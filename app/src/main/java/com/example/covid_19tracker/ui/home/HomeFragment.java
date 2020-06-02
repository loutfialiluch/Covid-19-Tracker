package com.example.covid_19tracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19tracker.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HomeFragment extends Fragment {

    private TextView totalConfirmed, totalDeaths, totalRecovered, lastUpdated;
    private ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        totalConfirmed = root.findViewById(R.id.totalConfirmed);
        totalDeaths = root.findViewById(R.id.totalDeaths);
        totalRecovered = root.findViewById(R.id.totalRecovered);
        progressBar = root.findViewById(R.id.progress_circular_home);
        lastUpdated = root.findViewById(R.id.lastUpdated);
        //Retrieve data from co-vid19 api
        getData();
        return root;
    }
    public String getDate(long time)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MM yyyy hh:mm:ss aaa");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return simpleDateFormat.format(calendar.getTime());
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://corona.lmao.ninja/v2/all";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    totalConfirmed.setText(formatter.format(Integer.parseInt(jsonObject.getString("cases"))));
                    totalDeaths.setText(formatter.format(Integer.parseInt(jsonObject.getString("deaths"))));
                    totalRecovered.setText(formatter.format(Integer.parseInt(jsonObject.getString("recovered"))));
                    lastUpdated.setText(getDate(Long.parseLong(jsonObject.getString("updated"))));
                } catch (JSONException e) {
                    e.printStackTrace();
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
