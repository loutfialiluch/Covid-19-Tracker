package com.example.covid_19tracker.ui.country;

public class CovidCountry implements Comparable<CovidCountry> {
    String covidCountry, cases, deaths, recovered, flag;

    public CovidCountry(String covidCountry, String cases, String deaths, String recovered, String flag) {
        this.covidCountry = covidCountry;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.flag = flag;
    }

    public String getCovidCountry() {
        return covidCountry;
    }

    public void setCovidCountry(String covidCountry) {
        this.covidCountry = covidCountry;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public int compareTo(CovidCountry o) {
        return Integer.parseInt(o.cases)-Integer.parseInt(cases);
    }
}
