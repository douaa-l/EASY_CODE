package com.example.easycode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easycode.Outils.Outils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class LevelActivity extends AppCompatActivity {
    ImageView themeUn=null;
    ImageView themeDeux=null;
    ImageView themeTrois=null;
    Button boutontest=null;

    String etatUn="";
    String etatDeux="";
    String etatTrois="";
    String etattest="";
LinearLayout ll;
    String niveau="";
    TextView titre=null;
    int id_niveau_choisi=0;
    int id_theme_choisi=0;
    int id_theme1=0;
    int num_niveau=0;
    String id_user="";
    boolean testfinal=false;

    private static String URL_REGIST= Outils.getPath()+"users/etat_theme.php";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Intent intent= getIntent();
        if(intent!=null){
            titre=(TextView)findViewById(R.id.leveltitle);
            if (intent.hasExtra("niveau")){
                niveau = intent.getStringExtra("niveau");
            }
            if (intent.hasExtra("id_user")){
                id_user = intent.getStringExtra("id_user");
            }
            titre.setText(niveau);
            if (intent.hasExtra("id_niveau_choisi")){
                id_niveau_choisi = intent.getIntExtra("id_niveau_choisi",0);

                System.out.println("********************"+String.valueOf(id_niveau_choisi)+"***********************************");
            }
            if (intent.hasExtra("num_niveau")){
                num_niveau = intent.getIntExtra("num_niveau",0);}

            if (intent.hasExtra("testfinal")){
                testfinal = intent.getBooleanExtra("testfinal",false);}

            themeUn=(ImageView)findViewById(R.id.theme1);
            themeDeux=(ImageView)findViewById(R.id.theme2);
            themeTrois=(ImageView)findViewById(R.id.theme3);
            boutontest=findViewById(R.id.boutontest);
            ll=findViewById(R.id.layout);

            themeUn.setOnClickListener(themeListener);
            themeDeux.setOnClickListener(themeListener);
            themeTrois.setOnClickListener(themeListener);
            boutontest.setOnClickListener(testNiveau);

            if(num_niveau==3){
                Button tfinal =findViewById(R.id.tfinal);
                tfinal.setVisibility(View.VISIBLE);
                tfinal.setOnClickListener(testblanc);
                if(testfinal){ tfinal.setBackgroundColor(getResources().getColor(R.color.violetclair));
                }else{tfinal.setBackgroundColor(getResources().getColor(R.color.gristransparent));}


            }



        }

etats();


    }

    public void etats(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);


                    etatUn = jsonObject.getString("theme1");
                    etatDeux = jsonObject.getString("theme2");
                    etatTrois = jsonObject.getString("theme3");
                    id_theme1= jsonObject.getInt("id_theme1");
                    etattest=jsonObject.getString("test");


                    System.out.println("*****************************"+etatUn+" "+etatDeux+" "+etatTrois+" "+id_theme1+" "+etattest);
                    boutontest.setTag(etattest);
                    if(etattest.equals("blocked")){
                        boutontest.setBackgroundColor(getResources().getColor(R.color.gristransparent));

                    }else {
                        boutontest.setBackground(getResources().getDrawable(R.drawable.buttonshape));
                    }

                    if(etatUn.equals("blocked")){
                        themeUn.setBackground(getResources().getDrawable(R.drawable.theme1blocked));

                    }
                    if(etatUn.equals("unblocked")){
                        themeUn.setBackground(getResources().getDrawable(R.drawable.theme1etoiles0));

                    }
                    if(etatUn.equals("threestars")){
                        themeUn.setBackground(getResources().getDrawable(R.drawable.theme1etoiles3));

                    }

                    if(etatDeux.equals("blocked")){
                        themeDeux.setBackground(getResources().getDrawable(R.drawable.theme2blocked));
                    }
                    if(etatDeux.equals("unblocked")){
                        themeDeux.setBackground(getResources().getDrawable(R.drawable.theme2etoiles0));
                    }
                    if(etatDeux.equals("threestars")){
                        themeDeux.setBackground(getResources().getDrawable(R.drawable.theme2etoiles3));
                    }

                    if(etatTrois.equals("blocked")){
                        themeTrois.setBackground(getResources().getDrawable(R.drawable.theme3blocked));
                    }
                    if(etatTrois.equals("unblocked")){
                        themeTrois.setBackground(getResources().getDrawable(R.drawable.theme3etoiles0));
                    }
                    if(etatTrois.equals("threestars")){
                        themeTrois.setBackground(getResources().getDrawable(R.drawable.theme3etoiles3));
                    }




                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {




                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params=new HashMap<>();
                params.put("id_niveau", String.valueOf(id_niveau_choisi));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(LevelActivity.this);
        requestQueue.add(stringRequest);
    }



    private View.OnClickListener themeListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String theme="";
            String blocked="";

            switch (v.getId()){
                case R.id.theme1:
                    theme="signalisation";
                    blocked=etatUn;
                    id_theme_choisi=id_theme1;
                    break;
                case R.id.theme2:
                    theme="circulation";
                    blocked=etatDeux;
                    id_theme_choisi=id_theme1+1;
                    break;
                case R.id.theme3:
                    theme="divers";
                    blocked=etatTrois;
                    id_theme_choisi=id_theme1+2;

                    break;
            }
            System.out.println("*************************************"+id_theme_choisi);
            if(!blocked.equals("blocked")){
                Intent intent=new Intent(LevelActivity.this, LeconActivity.class);
                intent.putExtra("theme",theme);
                intent.putExtra("niveau",niveau);
                intent.putExtra("id_theme_choisi",id_theme_choisi);
                intent.putExtra("id_niveau_choisi",id_niveau_choisi);
               intent.putExtra("id_user",id_user);
               intent.putExtra("num_niveau",num_niveau);

                startActivity(intent);}
            else {
                Toast.makeText(LevelActivity.this, "Le théme est bloqué", Toast.LENGTH_LONG).show();
            }

        }
    };


public View.OnClickListener testNiveau=new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String tag= (String) v.getTag();
        if(tag.equals("blocked")){
            Toast.makeText(LevelActivity.this,"le test est bloqué",Toast.LENGTH_LONG).show();
        }else{
            Intent intent=new Intent(LevelActivity.this,TestNiveauActivity.class);
            intent.putExtra("id_user",id_user);
            intent.putExtra("num_niveau",num_niveau);
            startActivity(intent);

        }

    }
};

    public View.OnClickListener testblanc=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(testfinal){

                Intent intent=new Intent(LevelActivity.this,TestBlancActivity.class);
                intent.putExtra("id_user",id_user);
                startActivity(intent);

            }else{
                Toast.makeText(LevelActivity.this,"le test blanc est débloqué, vous devez réussir tous vos tests de niveau ",Toast.LENGTH_LONG).show();
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        etats();
    }
}
