package com.example.fujimiya.farmmart;


import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOError;
import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TambahDataFragment extends Fragment {


    boolean Fcek = false;
    EditText Fnama, Fkomoditi, Fnope, Falamat;
    Button Fsimpan;
    DialogInterface.OnClickListener listener;

    LatLng latLng;
    Double Flatitude, Flongitude;

    //firebase
    Firebase Fref;
    IsiData isiData;

    public TambahDataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tambah_data, container, false);

        Firebase.setAndroidContext(this.getActivity());
        Fref = new Firebase("https://farmart-8d3e5.firebaseio.com/petani");
        Fref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Fnama = (EditText) view.findViewById(R.id.input_nama);
        Fkomoditi = (EditText) view.findViewById(R.id.input_komoditi);
        Fnope = (EditText) view.findViewById(R.id.input_nope);
        Falamat = (EditText) view.findViewById(R.id.input_alamat);
        Fsimpan = (Button) view.findViewById(R.id.btn_signup);

        Fsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formcek();
                String F = Falamat.getText().toString();
                Geocoder geocoder = new Geocoder(getActivity().getBaseContext());
                List<Address> addresses = null;

                try {
                    // Getting a maximum of 3 Address that matches the input
                    // text
                    addresses = geocoder.getFromLocationName(F, 3);
                    if (addresses != null && !addresses.equals(""))
                        konversi(addresses);


                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Alamat anda tidak ditemukan, silahkan periksa kembali !");
                    builder.setCancelable(false);

                    listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                Falamat.setText(null);
                            }

                        }
                    };
                    builder.setPositiveButton("Ya", listener);
                    builder.show();
                }

            }
        });

        return view;
    }

    private void konversi(List<Address> addresses) {
        Address address = (Address) addresses.get(0);
        latLng = new LatLng(address.getLatitude(), address.getLongitude());
        Flatitude = address.getLatitude();
        Flongitude = address.getLongitude();

        isiData = new IsiData(Fnama.getText().toString(),Fnope.getText().toString(),Fkomoditi.getText().toString(),Falamat.getText().toString(),Flatitude,Flongitude);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage("Apakah anda setuju untuk menambah data?");
        builder.setCancelable(false);

        listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    //Toast.makeText(getActivity().getApplicationContext(), latLng.toString(), Toast.LENGTH_SHORT).show();
                    Fref.push().setValue(isiData, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                Log.e("firebase", firebaseError.toString());
                            }
                        }
                    });

                    Fnama.setText(null);
                    Fnope.setText(null);
                    Fkomoditi.setText(null);
                    Falamat.setText(null);

                    LihatDataFragment lihatDataFragment = new LihatDataFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmen_maps,lihatDataFragment);
                    fragmentTransaction.commit();

                }

                if (which == DialogInterface.BUTTON_NEGATIVE) {

                }

            }
        };
        builder.setPositiveButton("Setuju", listener);
        builder.setNegativeButton("Tidak", listener);
        builder.show();


    }


    private boolean validateName() {
        if (Fnama.getText().toString().trim().isEmpty()) {
            Fnama.setError("Tidak boleh kosong!");
            Fnama.requestFocus();
            return false;
        } else {

        }

        return true;
    }

    private boolean validateKomoditi() {
        if (Fkomoditi.getText().toString().trim().isEmpty()) {
            Fkomoditi.setError("Tidak boleh kosong!");
            Fkomoditi.requestFocus();
            return false;
        } else {

        }

        return true;
    }

    private boolean validateNope() {
        if (Fnope.getText().toString().trim().isEmpty()) {
            Fnope.setError("Tidak boleh kosong!");
            Fnope.requestFocus();
            return false;
        } else {

        }

        return true;
    }

    private boolean validateAlamat() {
        if (Falamat.getText().toString().trim().isEmpty()) {
            Falamat.setError("Tidak boleh kosong!");
            Falamat.requestFocus();
            return false;
        } else {

        }

        return true;
    }

    private void formcek() {

        if (!validateName()) {
            return;
        }
        if (!validateNope()) {
            return;
        }

        if (!validateKomoditi()) {
            return;
        }

        if (!validateAlamat()) {
            return;
        }
    }


}
