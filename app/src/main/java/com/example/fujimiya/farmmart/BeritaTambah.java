package com.example.fujimiya.farmmart;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeritaTambah extends Fragment {


    public BeritaTambah() {
        // Required empty public constructor
    }


    EditText Gjudul, Gisi;
    Button GbtnKirimBerita;
    String Gjdl_berita, Gisi_berita;
    int Gtemp_id;
    Firebase Gref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_berita_tambah, container, false);

        Gjudul = (EditText) view.findViewById(R.id.GJudul);
        Gisi = (EditText) view.findViewById(R.id.Gisi);
        GbtnKirimBerita = (Button) view.findViewById(R.id.GbtnKirimberita);


        Gref = new Firebase("https://farmart-8d3e5.firebaseio.com/berita");
        Gref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(view.getContext(), "Data di Input", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        GbtnKirimBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                formcek();
                simpanBerita();

                //labgsung kembali ke daftar berita
                BeritaFragment beritaFragment = new BeritaFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmen_maps,beritaFragment);
                fragmentTransaction.commit();


            }
        });


        return view;
    }

    private boolean validateJudul() {
        if (Gjudul.getText().toString().trim().isEmpty()) {
            Gjudul.setError("Tidak boleh kosong!");
            Gjudul.requestFocus();
            return false;
        } else {

        }

        return true;
    }

    private boolean validateIsi() {
        if (Gisi.getText().toString().trim().isEmpty()) {
            Gisi.setError("Tidak boleh kosong!");
            Gisi.requestFocus();
            return false;
        } else {

        }

        return true;


    }


    private void formcek(){

        if (!validateJudul()) {
            return;
        }
        if (!validateIsi()) {
            return;
        }

    }

    public void simpanBerita(){


        //ini buat data pegawai
        Berita berita = new Berita(Gjudul.getText().toString(),Gisi.getText().toString());
        Gref.push().setValue(berita, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Log.e("firebase", firebaseError.toString());
                }
            }
        });

        ///////////////


    }
}