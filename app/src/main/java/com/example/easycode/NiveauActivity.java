package com.example.easycode;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easycode.ui.HomeActivity;
import com.example.easycode.ui.NotesActivity;
import com.example.easycode.ui.ProfilActivity;
import com.example.easycode.ui.facultatifActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class NiveauActivity extends AppCompatActivity {
    Button cours=null;
    Button profil=null;
    Button notes=null;
    Button facultatif=null;
    String id_user="";
    Dialog epicDialog;

Bundle bundle=new Bundle();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau);
        Intent intent= getIntent();
        if(intent!=null){


            if (intent.hasExtra("id_user")){
                id_user = intent.getStringExtra("id_user");
            }


            bundle.putString("id_user",id_user);




        }
        epicDialog=new Dialog(this);
        cours=(Button)findViewById(R.id.cours);
         profil=(Button)findViewById(R.id.profil);
        notes=(Button)findViewById(R.id.notes);
        facultatif=findViewById(R.id.facultatif);



        cours.setOnClickListener(navListener);
        profil.setOnClickListener(navListener);
        notes.setOnClickListener(navListener);
        facultatif.setOnClickListener(navListener);

        HomeActivity fragment=new HomeActivity();
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,fragment).commit();


    }
    private View.OnClickListener navListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment selectedFragment=null;
            switch (v.getId()){
                case R.id.notes:
                    selectedFragment= new NotesActivity();
                    selectedFragment.setArguments(bundle);
                    cours.setTextColor(getResources().getColor(R.color.gristransparent));
                    profil.setTextColor(getResources().getColor(R.color.gristransparent));
                    notes.setTextColor(getResources().getColor(R.color.jaune));
                    facultatif.setTextColor(getResources().getColor(R.color.gristransparent));
                    break;
                case R.id.profil:
                    selectedFragment=new ProfilActivity();
                    selectedFragment.setArguments(bundle);
                    cours.setTextColor(getResources().getColor(R.color.gristransparent));
                    profil.setTextColor(getResources().getColor(R.color.jaune));
                    notes.setTextColor(getResources().getColor(R.color.gristransparent));
                    facultatif.setTextColor(getResources().getColor(R.color.gristransparent));

                    break;
                case R.id.cours:
                    selectedFragment=new HomeActivity();
                    selectedFragment.setArguments(bundle);
                    cours.setTextColor(getResources().getColor(R.color.jaune));
                    profil.setTextColor(getResources().getColor(R.color.gristransparent));
                    notes.setTextColor(getResources().getColor(R.color.gristransparent));
                    facultatif.setTextColor(getResources().getColor(R.color.gristransparent));
                    break;
                case R.id.facultatif:
                    selectedFragment=new facultatifActivity();
                    selectedFragment.setArguments(bundle);
                    facultatif.setTextColor(getResources().getColor(R.color.jaune));
                    profil.setTextColor(getResources().getColor(R.color.gristransparent));
                    notes.setTextColor(getResources().getColor(R.color.gristransparent));
                    cours.setTextColor(getResources().getColor(R.color.gristransparent));
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,selectedFragment).commit();

        }
    };

    @Override
    public void onBackPressed() {
deconnexion();


    }
    public void deconnexion(){
        epicDialog.setContentView(R.layout.deconnexion);
        Button deconnecter=(Button)epicDialog.findViewById(R.id.deconnexion);
        Button annuler=(Button)epicDialog.findViewById(R.id.annulerdeconnexion) ;
        ImageView close=(ImageView)epicDialog.findViewById(R.id.closedeconnexion);


        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();

            }

        });
        deconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();

    }
}
