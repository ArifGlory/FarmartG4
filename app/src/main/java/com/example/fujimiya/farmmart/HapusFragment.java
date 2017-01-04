package com.example.fujimiya.farmmart;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HapusFragment extends Fragment {
    EditText Fnama, Fkomoditi, Fnope, Falamat;
    Button Fhapus;
    Firebase Fref;
    DialogInterface.OnClickListener listener;
    public View view1;
    public String terima;



    public HapusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hapus, container, false);
        Firebase.setAndroidContext(this.getContext());
        terima = getArguments().getString("kunci");
        Fnama = (EditText) view.findViewById(R.id.input_nama);
        Fkomoditi = (EditText) view.findViewById(R.id.input_komoditi);
        Fnope = (EditText) view.findViewById(R.id.input_nope);
        Falamat = (EditText) view.findViewById(R.id.input_alamat);
        Fhapus = (Button) view.findViewById(R.id.hapus);


        Fnama.setText(getArguments().getString("nama"));
        Fkomoditi.setText(getArguments().getString("komoditi"));
        Fnope.setText(getArguments().getString("nope"));
        Falamat.setText(getArguments().getString("alamat"));

       Fhapus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Fref = new Firebase("https://farmart-8d3e5.firebaseio.com/petani");
               Fref.child(terima).setValue(null);


               LihatDataFragment lihatDataFragment = new LihatDataFragment();
               android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
               fragmentTransaction.replace(R.id.fragmen_maps, lihatDataFragment);
               fragmentTransaction.commit();
               //Toast.makeText(view.getContext(),terima, Toast.LENGTH_LONG).show();
           }
       });

        return view;
    }

}
