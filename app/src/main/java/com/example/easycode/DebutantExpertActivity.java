package com.example.easycode;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easycode.Outils.Outils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DebutantExpertActivity extends AppCompatActivity {

    Button debutant=null;
    Button expert=null;
    ProgressBar pb;
   String id_user="";
    String choix="";


    private static String URL_REGIST=Outils.getPath()+"users/create_profil.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debutant_expert);
        Intent intent= getIntent();
        if(intent!=null){

            if (intent.hasExtra("id_user")){
                id_user= (intent.getStringExtra("id_user"));
            }



        }

        debutant=(Button) findViewById(R.id.buttonDebutant);
        expert=(Button) findViewById(R.id.buttonExpert);
        pb=(ProgressBar)findViewById(R.id.progressBarDE);

        debutant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debutant.setClickable(false);
                choix="debutant";
                pb.setVisibility(View.VISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DebutantExpertActivity.this, R.string.inscriptionreussie, Toast.LENGTH_LONG).show();
                        pb.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(DebutantExpertActivity.this, TutoActivity.class);
                        intent.putExtra("id_user",id_user);
                        startActivity(intent);
                            finish();
                        System.out.println(response);
                        
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(DebutantExpertActivity.this, getString(R.string.inscriptionechouee)+error.toString(), Toast.LENGTH_LONG).show();



                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<>();
                        params.put("id_user", id_user);
                        params.put("choix",choix);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(DebutantExpertActivity.this);
                requestQueue.add(stringRequest);
            }
        });
        
       expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expert.setClickable(false);
                pb.setVisibility(View.VISIBLE);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            Boolean erreur =jsonObject.getBoolean("error");
                            String msgErreur=jsonObject.getString("message");



                            if (!erreur){
                                Toast.makeText(DebutantExpertActivity.this, R.string.inscriptionreussie, Toast.LENGTH_LONG).show();
                                pb.setVisibility(View.INVISIBLE);

                                Intent intent = new Intent(DebutantExpertActivity.this, TestPrimaireActivity.class);
                                intent.putExtra("id_user",id_user);
                                startActivity(intent);
                                finish();

                            }else{
                                pb.setVisibility(View.INVISIBLE);
                                Toast.makeText(DebutantExpertActivity.this, getString(R.string.erreurinscription)+msgErreur, Toast.LENGTH_LONG).show();}


                        } catch (JSONException e) {
                            e.printStackTrace();
                            pb.setVisibility(View.INVISIBLE);
                            Toast.makeText(DebutantExpertActivity.this, getString(R.string.inscriptionechouee)+e.toString(), Toast.LENGTH_LONG).show();


                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(DebutantExpertActivity.this, getString(R.string.inscriptionechouee)+error.toString(), Toast.LENGTH_LONG).show();



                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<>();
                        params.put("id_user", id_user);
                        params.put("choix",choix);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(DebutantExpertActivity.this);
                requestQueue.add(stringRequest);
            }
        });


    }

}
