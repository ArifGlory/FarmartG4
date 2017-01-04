package com.example.fujimiya.farmmart;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UbahFragment extends Fragment {

    EditText Fnama, Fkomoditi, Fnope, Falamat;
    Button FUbah;
    DialogInterface.OnClickListener listener;
    Firebase ref;
    public View view1;
    public String terima, alamat;

    LatLng latLng;
    Double Flatitude, Flongitude;

      public UbahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_ubah, container, false);
        Firebase.setAndroidContext(this.getContext());


        terima = getArguments().getString("kunci");
        alamat = getArguments().getString("alamat");

        Fnama = (EditText) view.findViewById(R.id.input_nama);
        Fkomoditi = (EditText) view.findViewById(R.id.input_komoditi);
        Fnope = (EditText) view.findViewById(R.id.input_nope);
        Falamat = (EditText) view.findViewById(R.id.input_alamat);

        Fnama.setText(getArguments().getString("nama"));
        Fkomoditi.setText(getArguments().getString("komoditi"));
        Fnope.setText(getArguments().getString("nope"));
        Falamat.setText(getArguments().getString("alamat"));

        FUbah = (Button) view.findViewById(R.id.btn_ubah);
        FUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //Toast.makeText(view.getContext(),alamat+"\n"+Falamat.getText(), Toast.LENGTH_LONG).show();
                if (alamat.equals(Falamat.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Data akan terubah, apakah anda setuju?");
                    builder.setCancelable(false);

                    listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_POSITIVE) {


                                final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                                        R.style.AppTheme_Dark_Dialog);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("Loading...");
                                progressDialog.show();

                                ref = new Firebase("https://farmart-8d3e5.firebaseio.com/petani");
                                ref.child(terima).child("nama").setValue(Fnama.getText().toString());
                                ref.child(terima).child("komoditi").setValue(Fkomoditi.getText().toString());
                                ref.child(terima).child("nope").setValue(Fnope.getText().toString());


                                LihatDataFragment lihatDataFragment = new LihatDataFragment();
                                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragmen_maps, lihatDataFragment);
                                fragmentTransaction.commit();
                                //Toast.makeText(view.getContext(),Fnama.getText(), Toast.LENGTH_LONG).show();
                                progressDialog.cancel();

                            }

                            if (which == DialogInterface.BUTTON_NEGATIVE) {
                                dialog.cancel();
                            }
                        }
                    };
                    builder.setPositiveButton("Ya", listener);
                    builder.setNegativeButton("Tidak", listener);
                    builder.show();

                } else if (alamat != Falamat.getText().toString()) {
                    //Toast.makeText(view.getContext(), alamat + "\n" + Falamat.getText(), Toast.LENGTH_LONG).show();



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


            }
        });
        return view;
    }

    private void konversi(List<Address> addresses) {

        Address address = (Address) addresses.get(0);
        latLng = new LatLng(address.getLatitude(), address.getLongitude());
        Flatitude = address.getLatitude();
        Flongitude = address.getLongitude();

        ref = new Firebase("https://farmart-8d3e5.firebaseio.com/petani");
        ref.child(terima).child("nama").setValue(Fnama.getText().toString());
        ref.child(terima).child("komoditi").setValue(Fkomoditi.getText().toString());
        ref.child(terima).child("nope").setValue(Fnope.getText().toString());
        ref.child(terima).child("alamat").setValue(Falamat.getText().toString());
        ref.child(terima).child("lat").setValue(Flatitude);
        ref.child(terima).child("lon").setValue(Flongitude);


        Fnama.setText(null);
        Fnope.setText(null);
        Fkomoditi.setText(null);
        Falamat.setText(null);

        LihatDataFragment lihatDataFragment = new LihatDataFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmen_maps, lihatDataFragment);
        fragmentTransaction.commit();



    }


}
