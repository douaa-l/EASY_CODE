package com.example.easycode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.example.easycode.Outils.uneLecon;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.widget.LinearLayout.LayoutParams;

public class LeconActivity extends AppCompatActivity {

    ArrayList<uneLecon> rows=new ArrayList<uneLecon>();
    LinearLayout ll=null;
    TextView titre=null;
    int id_theme_choisi=7;
    int id_niveau_choisi=7;
    String theme="";
    String niveau="";
    String id_user="";
    int num_niveau=0;
    private static String URL_REGIST= Outils.getPath()+"users/etat_lecon.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecon);
        Intent intent= getIntent();
        if(intent!=null){

            if (intent.hasExtra("theme")){
                theme = intent.getStringExtra("theme");
            }
            if (intent.hasExtra("niveau")){
                niveau = intent.getStringExtra("niveau");
            }

            if (intent.hasExtra("id_theme_choisi")){
                id_theme_choisi = intent.getIntExtra("id_theme_choisi",7);
            }
            if (intent.hasExtra("id_niveau_choisi")){
                id_niveau_choisi = intent.getIntExtra("id_niveau_choisi",7);
            }
            if (intent.hasExtra("num_niveau")){
                num_niveau = intent.getIntExtra("num_niveau",0);}
            if (intent.hasExtra("id_user")){
                id_user = intent.getStringExtra("id_user");
            }
titre=findViewById(R.id.titre);
            titre.setText(theme);
            Log.d("******id theme****", String.valueOf(id_theme_choisi));

        }
        ll=findViewById(R.id.leconsContainer);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray array = new JSONArray(response);
                    for(int i=0;i<(array.length());i++){
                        rows.add(new uneLecon(array.getJSONObject(i)));
                        Button cours =new Button(LeconActivity.this);
                        cours.setText(rows.get(i).getTitre());
                        cours.setTextSize(18);
                        cours.setTextColor(getResources().getColor(R.color.blanc));
                        cours.setPadding(10,40,10,40);

                        LayoutParams params = new LayoutParams(
                                LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 30, 0, 30);
                        cours.setLayoutParams(params);

                       if(rows.get(i).getEtat().equals("blocked")){
                            cours.setBackgroundColor(getResources().getColor(R.color.gristransparent));
                        cours.setTag(i);
                        }else{cours.setBackgroundColor(getResources().getColor(R.color.bleutransparent));
                            cours.setTag(i);
                        }
                        cours.setOnClickListener(clickLecon);
                        ll.addView(cours);

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
                params.put("id_theme", String.valueOf(id_theme_choisi));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(LeconActivity.this);
        requestQueue.add(stringRequest);


    }
    public View.OnClickListener clickLecon=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int rowEmplacement=(int)v.getTag();
            if (rows.get(rowEmplacement).getEtat().equals("blocked")){
                Toast.makeText(LeconActivity.this,"la lecon est bloquée",Toast.LENGTH_LONG).show();
            }else{
                if(theme.equals("signalisation")){

                Intent intent=new Intent(LeconActivity.this,MenuPlaqueActivity.class);
             intent.putExtra("titre_cours",rows.get(rowEmplacement).getCodeCours());
             intent.putExtra("title",rows.get(rowEmplacement).getTitre());
                    intent.putExtra("theme",theme);
                    intent.putExtra("id_theme_choisi",id_theme_choisi);
                    intent.putExtra("niveau",niveau);
                    intent.putExtra("id_niveau_choisi",id_niveau_choisi);
                    intent.putExtra("id_user",id_user);
                    intent.putExtra("num_niveau",num_niveau);
                    intent.putExtra("id_cours",rows.get(rowEmplacement).getId_cours());
                startActivity(intent);
                    LeconActivity.this.finish();
                }else{
                    Intent intent=new Intent(LeconActivity.this,HTMLActivity.class);
                    intent.putExtra("titre_cours",rows.get(rowEmplacement).getCodeCours());
                    intent.putExtra("title",rows.get(rowEmplacement).getTitre());
                    intent.putExtra("titre_theme",theme);
                    intent.putExtra("niveau",niveau);
                    intent.putExtra("id_niveau_choisi",id_niveau_choisi);
                    intent.putExtra("id_theme_choisi",id_theme_choisi);
                    intent.putExtra("id_user",id_user);
                    intent.putExtra("num_niveau",num_niveau);
                    intent.putExtra("id_cours",rows.get(rowEmplacement).getId_cours());

                    startActivity(intent);
                    LeconActivity.this.finish();

                }
            }


        }
    };

}
