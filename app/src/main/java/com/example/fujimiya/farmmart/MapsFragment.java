package com.example.fujimiya.farmmart;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * ini java class halaman utama
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;

    public Marker marker_ghost;

    public Firebase Fref;


    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_maps, container, false);

        Firebase.setAndroidContext(this.getActivity());
        Fref = new Firebase("https://farmart-8d3e5.firebaseio.com/petani");
        //Fref.child("").removeValue();
        final FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();

        fragment.getMapAsync(this);
        // Inflate the layout for this fragment

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //mMap.setMyLocationEnabled(true);

        mMap.clear();
        LatLng lampung = new LatLng(-5.382351, 105.257791);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CircleOptions mOptions = new CircleOptions()
                .center(lampung).radius(100)
                .strokeColor(0x110000FF).strokeWidth(8).fillColor(0x110000FF);
        mMap.addCircle(mOptions);

        mMap.addMarker(new MarkerOptions().position(lampung).title("lokasi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lampung, 17));

        Fambil();

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.windowlayout, null);

                TextView nama = (TextView) v.findViewById(R.id.Nama);
                TextView isi = (TextView) v.findViewById(R.id.Nope);
                isi.setText(marker.getTitle());
                //nama.setText("Petani      : "+marker.getTitle());
                //Toast.makeText(getActivity().getApplication(),marker.getSnippet(),Toast.LENGTH_LONG).show();
                //nope.setText(Snope);
                return v;
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {


                marker_ghost = marker;
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setView(v);
                TextView textView = (TextView) v.findViewById(R.id.isidata);

                ImageButton call = (ImageButton) v.findViewById(R.id.user_call);
                textView.setText(marker.getTitle());

                AlertDialog alert = alertDialogBuilder.create();
                alert.show();

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String Fnumber = dataSnapshot.child(marker_ghost.getSnippet().toString()).child("nope").getValue().toString();

                                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", Fnumber, null)));
                                //Toast.makeText(getActivity().getApplication(),dataSnapshot.child(marker_ghost.getSnippet().toString()).child("nope").getValue().toString(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }
                });


            }
        });
    }

    public void Fambil() {
        Fref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mMap != null) {
                    mMap.clear();
                }
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String nama = (String) child.child("nama").getValue();
                    String nope = (String) child.child("nope").getValue();
                    String komoditi = (String) child.child("komoditi").getValue();
                    String key = (String) child.getKey();

                    Double latt = (Double) child.child("lat").getValue();
                    Double lonn = (Double) child.child("lon").getValue();
                    LatLng FlKomoditi = new LatLng(latt, lonn);
                    //Toast.makeText(getActivity().getApplication(),""+FlKomoditi ,Toast.LENGTH_LONG).show();

                    mMap.addMarker(new MarkerOptions().position(FlKomoditi).title("\rPetani      : " + nama + "\n\r" + "Komoditi : " + komoditi + "\n\r" +
                            "No Telp    : " + nope).snippet(key));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(FlKomoditi, 17));

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
