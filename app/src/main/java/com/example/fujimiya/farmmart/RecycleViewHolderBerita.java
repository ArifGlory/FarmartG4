package com.example.fujimiya.farmmart;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleViewHolderBerita extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtNamaOutlet,txtIsiBerita;
    public ImageView gmbrList;


    public RecycleViewHolderBerita(View itemView) {
        super(itemView);

        txtNamaOutlet = (TextView) itemView.findViewById(R.id.txtJudul);
        txtIsiBerita = (TextView) itemView.findViewById(R.id.txtIsi);





    }

    @Override
    public void onClick(View v) {



    }
}
