package com.example.fujimiya.farmmart;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fujimiya on 12/25/16.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<ItemController> {

    Context context;
    List<String> versionModels;

    //Text
    public static List<String> FNamaPetani = new ArrayList<String>();
    public static List<String> FAlamat = new ArrayList<String>();
    public static List<String> FNope = new ArrayList<String>();
    public static List<String> FKomoditi = new ArrayList<String>();
    public static List<String> FLat = new ArrayList<String>();
    public static List<String> FLon = new ArrayList<String>();

    Firebase Fref;

    //Set List Items
    public void setCardList(final Context context) {

        notifyDataSetChanged();
        Firebase.setAndroidContext(this.context);

        Fref = new Firebase("https://farmart-8d3e5.firebaseio.com/petani");
        Fref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FNamaPetani.clear();
                FAlamat.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String nama = (String) child.child("nama").getValue();
                    FNamaPetani.add(nama);
                    String alamat = (String) child.child("alamat").getValue();
                    FAlamat.add(alamat);

                }
                notifyDataSetChanged();
                //Toast.makeText(context.getApplicationContext(), "Data berhasil diambil", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    public MyRecyclerAdapter(Context context) {
        this.context = context;
        setCardList(context);
    }

    public MyRecyclerAdapter(List<String> versionModels) {
        this.versionModels = versionModels;

    }

    @Override
    public ItemController onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycle, viewGroup, false);
        ItemController viewHolder = new ItemController(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemController versionViewHolder, int i) {

        versionViewHolder.title.setText(FNamaPetani.get(i));
        versionViewHolder.subTitle.setText(FAlamat.get(i));
    }

    @Override
    public int getItemCount() {

        return FNamaPetani == null ? 0 : FNamaPetani.size();
    }
}