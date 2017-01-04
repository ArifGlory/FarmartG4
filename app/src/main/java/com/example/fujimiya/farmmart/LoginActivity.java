package com.example.fujimiya.farmmart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    DialogInterface.OnClickListener listener;
    Firebase ref;
    EditText FUser_txt,FPassword_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        FUser_txt = (EditText) findViewById(R.id.FUser);
        FPassword_txt = (EditText) findViewById(R.id.FPassword);
    }

    public void login(View view) {
       // Toast.makeText(getApplicationContext(),"Anda berhasil login "+FPassword_txt.getText(),Toast.LENGTH_LONG).show();

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        ref = new Firebase("https://farmart-8d3e5.firebaseio.com/login");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){

                    String nama = (String) child.child("nama").getValue();
                    String password = (String) child.child("password").getValue();



                    if (child.child("nama").getValue().toString().equals(FUser_txt.getText().toString()) && child.child("password").getValue().toString().equals(FPassword_txt.getText().toString()) ){
                        //Toast.makeText(getApplicationContext(),"Anda berhasil login "+nama,Toast.LENGTH_LONG).show();
                        finish();
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Username dan password salah, silahkan coba kembali",Toast.LENGTH_LONG).show();
                    }

                    //Toast.makeText(getApplicationContext(),"Anda berhasil login "+password,Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //Toast.makeText(getApplicationContext(),"Ditekan",Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Apakan anda tetap ingin menutup aplikasi?");
            builder.setCancelable(false);

            listener = new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == DialogInterface.BUTTON_POSITIVE){
                        finishAffinity();
                    }

                    if(which == DialogInterface.BUTTON_NEGATIVE){
                        dialog.cancel();
                    }
                }
            };
            builder.setPositiveButton("Ya",listener);
            builder.setNegativeButton("Tidak", listener);
            builder.show();

        }
        return super.onKeyDown(keyCode, event);
    }
}
