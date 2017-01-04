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
public class BeritaEdit extends Fragment {


    public BeritaEdit() {
        // Required empty public constructor
    }


    EditText GjudulEdit, GisiEdit;
    Button GbtnUbahBerita;
    Firebase Gref;
    String judulTerima,isiTerima,keyTerima;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       final View view = inflater.inflate(R.layout.fragment_berita_edit, container, false);
        GjudulEdit = (EditText) view.findViewById(R.id.GJudulEdit);
        GisiEdit = (EditText) view.findViewById(R.id.GisiEdit);
        GbtnUbahBerita = (Button) view.findViewById(R.id.GbtnEditberita);
        Gref = new Firebase("https://farmart-8d3e5.firebaseio.com/berita");

        keyTerima = getArguments().getString("key");
        judulTerima = getArguments().getString("judul");
        isiTerima = getArguments().getString("isi");

        GjudulEdit.setText(judulTerima);
        GisiEdit.setText(isiTerima);

        GbtnUbahBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               UbahData();
            }
        });


        return  view;
    }


    private void UbahData(){

        Gref = new Firebase("https://farmart-8d3e5.firebaseio.com/berita");

        Gref.child(keyTerima).child("isi").setValue(GisiEdit.getText().toString());
        Gref.child(keyTerima).child("judul").setValue(GjudulEdit.getText().toString());


        GjudulEdit.setText(null);
        GisiEdit.setText(null);

        BeritaFragment beritaFragment = new BeritaFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmen_maps,beritaFragment);
        fragmentTransaction.commit();



    }


}
