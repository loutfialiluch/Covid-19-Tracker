package com.example.covid_19tracker.ui.country;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.covid_19tracker.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CovidCountryAdapter extends RecyclerView.Adapter<CovidCountryAdapter.ViewHolder> implements Filterable {
    List<CovidCountry> covidCountries;
    List<CovidCountry> allCovidCountries;
    Context context;

    public CovidCountryAdapter(List<CovidCountry> covidCountries, Context context) {
        this.covidCountries = covidCountries;
        this.context = context;
        allCovidCountries = new ArrayList<>(covidCountries);
    }

    @NonNull
    @Override
    public CovidCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_covid_country, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CovidCountry covidCountry = covidCountries.get(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.tvTotalCases.setText(formatter.format(Integer.parseInt(covidCountry.getCases())));
        holder.tvCountryName.setText(covidCountry.getCovidCountry());
        RequestOptions options = new RequestOptions().override(240,160);

        Glide.with(context).load(covidCountry.getFlag()).apply(options).into(holder.countryFlag);

    }

    @Override
    public int getItemCount() {
        return covidCountries.size();
    }

    @Override
    public Filter getFilter() {
        return covidCountriesFilter;
    }
    private Filter covidCountriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CovidCountry> filteredCovidCountry = new ArrayList<>();

            if(constraint == null || constraint.length()==0)
            {
                filteredCovidCountry.addAll(allCovidCountries);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(CovidCountry country : allCovidCountries)
                {
                    if(country.getCovidCountry().toLowerCase().contains(filterPattern))
                        filteredCovidCountry.add(country);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredCovidCountry;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            covidCountries.clear();
            covidCountries.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTotalCases, tvCountryName;
        ImageView countryFlag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTotalCases = itemView.findViewById(R.id.totalCases);
            tvCountryName = itemView.findViewById(R.id.countryName);
            countryFlag = itemView.findViewById(R.id.countryFlag);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            CovidCountry covidCountry = covidCountries.get(getPosition());
            Intent intent = new Intent(v.getContext(), CovidCountryDetails.class);
            intent.putExtra("cases", covidCountry.getCases());
            intent.putExtra("deaths", covidCountry.getDeaths());
            intent.putExtra("recovered", covidCountry.getRecovered());
            intent.putExtra("countryName", covidCountry.getCovidCountry());
            v.getContext().startActivity(intent);
        }
    }

}
