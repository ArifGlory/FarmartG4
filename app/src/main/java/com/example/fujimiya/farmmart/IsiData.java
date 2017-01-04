package com.example.fujimiya.farmmart;

/**
 * Created by fujimiya on 12/24/16.
 */

public class IsiData {

    String nama;
    String nope;
    String komoditi;
    String alamat;
    Double lat;
    Double lon;

    public IsiData(String nama, String nope, String komoditi, String alamat, Double lat, Double lon ){
        this.nama = nama;
        this.nope = nope;
        this.komoditi = komoditi;
        this.alamat = alamat;
        this.lat = lat;
        this.lon = lon;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNope() {
        return nope;
    }

    public void setNope(String nope) {
        this.nope = nope;
    }

    public String getKomoditi() {
        return komoditi;
    }

    public void setKomoditi(String komoditi) {
        this.komoditi = komoditi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
