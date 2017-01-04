package com.example.fujimiya.farmmart;

/**
 * Created by fujimiya on 12/25/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.security.PublicKey;


public class ItemController extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Variable
    CardView cardItemLayout;
    ImageView icon; // Picture
    TextView title;
    TextView subTitle;
    Intent i;
    DialogInterface.OnClickListener listener;
    Firebase FUref;

    public ItemController(View itemView) {
        super(itemView);

        //Set id
        cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);

        //Tambahan untuk id Picture

        kunci = "";
        //id Text
        title = (TextView) itemView.findViewById(R.id.listitem_name);
        subTitle = (TextView) itemView.findViewById(R.id.listitem_subname);

        //onClick
        itemView.setOnClickListener(this);

    }


    public String kunci;
    public View view;
    public AlertDialog alg;
    public int urut;

    public String Snama, Skomoditi, Snope, Salamat;

    @Override
    public void onClick(View v) {
        view = v;
        //tampilkan toas ketika click
        urut = Integer.parseInt(String.format("%d", getAdapterPosition()));


        LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
        View vie = inflater.inflate(R.layout.dialog_data, null);
        ImageButton ubah = (ImageButton) vie.findViewById(R.id.user_edit);
        ImageButton hapus = (ImageButton) vie.findViewById(R.id.user_hapus);
        Firebase.setAndroidContext(v.getContext());

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
        alertDialogBuilder.setView(vie);
        AlertDialog alert = alertDialogBuilder.create();
        alg = alert;
        alert.show();


        FUref = new Firebase("https://farmart-8d3e5.firebaseio.com/petani");
        FUref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                kunci = "";

                for (DataSnapshot child : dataSnapshot.getChildren()) {


                    if (child.child("nama").getValue().toString().equals(MyRecyclerAdapter.FNamaPetani.get(urut))) {
                        String key = (String) child.getKey();
                        Snama = child.child("nama").getValue().toString();
                        Skomoditi = child.child("komoditi").getValue().toString();
                        Snope = child.child("nope").getValue().toString();
                        Salamat = child.child("alamat").getValue().toString();
                        kunci = key;
                        urut = 0;
                        //Toast.makeText(view.getContext(), FUref.child(key).getKey().toString(), Toast.LENGTH_LONG).show();
                    }

                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view2) {

                alg.cancel();
                UbahFragment ubahFragment = new UbahFragment();
                Bundle bundle = new Bundle();
                bundle.putString("kunci", kunci);
                bundle.putString("nama", Snama);
                bundle.putString("komoditi", Skomoditi);
                bundle.putString("nope", Snope);
                bundle.putString("alamat", Salamat);
                ubahFragment.setArguments(bundle);

                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmen_maps, ubahFragment)
                        .commit();


            }
        });
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view1) {
                alg.cancel();
                HapusFragment hapusFragment = new HapusFragment();
                Bundle bundle = new Bundle();
                bundle.putString("kunci", kunci);
                bundle.putString("nama", Snama);
                bundle.putString("komoditi", Skomoditi);
                bundle.putString("nope", Snope);
                bundle.putString("alamat", Salamat);
                hapusFragment.setArguments(bundle);
                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmen_maps, hapusFragment)
                        .commit();

            }
        });


    }

}