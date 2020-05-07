package com.example.easycode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class HTMLActivity extends AppCompatActivity {
WebView webView=null;
String titre_cours="";
int num_niveau;
String id_cours,id_user,titre_theme;
    Button bouton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        bouton = findViewById(R.id.buttonhtml);
        final Intent intent= getIntent();
        if(intent!=null){


            if (intent.hasExtra("num_niveau")){
                num_niveau = intent.getIntExtra("num_niveau",0);}
            if (intent.hasExtra("id_cours")){
                id_cours= (intent.getStringExtra("id_cours"));}
            if (intent.hasExtra("id_user")){
                id_user = intent.getStringExtra("id_user");
            }

            if (intent.hasExtra("titre_theme")){
                titre_theme = intent.getStringExtra("titre_theme");
            }

            if (intent.hasExtra("titre_cours")){
                titre_cours = intent.getStringExtra("titre_cours");
            }
            if (intent.hasExtra("facultatif")){
               bouton.setVisibility(View.GONE);
            }

        } bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(HTMLActivity.this,TestActivity.class);
                intent1.putExtra("titre_cours",titre_cours);
                intent1.putExtra("num_niveau",num_niveau);
                intent1.putExtra("id_cours",id_cours);
                intent1.putExtra("id_user",id_user);
                intent1.putExtra("titre_theme",titre_theme);
                startActivity(intent1);
            }
        });
        String code=titre_cours;

        switch (titre_cours){
            case"Categorie B":
                code="CategorieB";
                break;

        }

            webView=(WebView)findViewById(R.id.webView);
            System.out.println(titre_cours+"------------"+code);


        webView.loadUrl("file:///android_asset/"+code+".html");
    }


}
