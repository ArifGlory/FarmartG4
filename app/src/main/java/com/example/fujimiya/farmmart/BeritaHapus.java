package com.example.fujimiya.farmmart;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeritaHapus extends Fragment {


    public BeritaHapus() {
        // Required empty public constructor
    }

    EditText GjudulHapus, GisiHapus;
    Button GbtnHapusBerita;
    Firebase Gref;
    String judulTerima,isiTerima,keyTerima;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_berita_hapus, container, false);

        GjudulHapus = (EditText) view.findViewById(R.id.GJudulHapus);
        GisiHapus = (EditText) view.findViewById(R.id.GisiHapus);
        GbtnHapusBerita = (Button) view.findViewById(R.id.GbtnHapusberita);

        keyTerima = getArguments().getString("key");
        judulTerima = getArguments().getString("judul");
        isiTerima = getArguments().getString("isi");

        GjudulHapus.setText(judulTerima);
        GisiHapus.setText(isiTerima);

        GbtnHapusBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HapusData();
            }
        });




        return  view;
    }

    private void HapusData(){
        Gref = new Firebase("https://farmart-8d3e5.firebaseio.com/berita");
        Gref.child(keyTerima).setValue(null);

        BeritaFragment beritaFragment = new BeritaFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmen_maps,beritaFragment);
        fragmentTransaction.commit();




    }

}
