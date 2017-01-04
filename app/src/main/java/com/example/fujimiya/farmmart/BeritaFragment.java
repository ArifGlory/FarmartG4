package com.example.fujimiya.farmmart;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeritaFragment extends Fragment {


    public BeritaFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    FloatingActionButton Gfloatbtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_berita, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        Gfloatbtn = (FloatingActionButton) view.findViewById(R.id.fab);


        RecycleAdapterBerita adapterBerita = new RecycleAdapterBerita(view.getContext());
        recyclerView.setAdapter(adapterBerita);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));



        Gfloatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Membuat Berita baru",Toast.LENGTH_SHORT).show();
                BeritaTambah beritaTambah = new BeritaTambah();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmen_maps,beritaTambah);
                fragmentTransaction.commit();
            }
        });




        return view;
    }

}