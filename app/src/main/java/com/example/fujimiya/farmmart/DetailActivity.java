package com.example.fujimiya.farmmart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class DetailActivity extends AppCompatActivity {

    TextView nama,komoditi,nope,alamat;
    Firebase Fref;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Firebase.setAndroidContext(this);
        Fref = new Firebase("https://farmart-8d3e5.firebaseio.com/petani");

        nama = (TextView) findViewById(R.id.input_nama);
        komoditi = (TextView) findViewById(R.id.input_komoditi);
        nope = (TextView) findViewById(R.id.input_nope);
        alamat = (TextView) findViewById(R.id.input_alamat);




        Toast.makeText(this,key, Toast.LENGTH_LONG).show();
    }

    public void ubah(View view) {

    }

    public void hapus(View view) {
        Fref.child(key).removeValue();
        LihatDataFragment lihatDataFragment = new LihatDataFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmen_maps, lihatDataFragment);
        fragmentTransaction.commit();
    }
}
