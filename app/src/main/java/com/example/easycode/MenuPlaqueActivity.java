package com.example.easycode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easycode.Outils.Outils;
import com.example.easycode.Outils.Plaque;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuPlaqueActivity extends AppCompatActivity {

    Button testButton=null;
    LinearLayout ll=null;
    LinearLayout ll1=null;
    LinearLayout ll2=null;
    LinearLayout ll3=null;
    LinearLayout ll4=null;
    LinearLayout ll5=null;
    LinearLayout ll6=null;
    LinearLayout ll7=null;
    LinearLayout ll8=null;
    LinearLayout ll9=null;
    LinearLayout ll10,ll11,ll12,ll13,ll14,ll15,ll16,ll17,ll18,ll19,ll20,ll21,ll22,ll23,ll24,ll25,ll26;
    String titre_cours="";
    String theme="";
    String niveau="";
    String title="";
    int id_niveau_choisi=0;
    int id_theme_choisi=0;
    int num_niveau=0;
    String id_cours;
    String id_user="";
    TextView titrePlaques=null;
    ArrayList<Plaque> rows=new ArrayList<Plaque>();
    ArrayList<String>titres=new ArrayList<>();
    ArrayList<String>images=new ArrayList<>();
    ArrayList<String>descriptions=new ArrayList<>();


    private static String URL_REGIST= Outils.getPath()+"projet/includes/get_plaques.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_plaque);
        Intent intent=getIntent();
        if(intent!=null){
            if (intent.hasExtra("titre_cours")){
                titre_cours= (intent.getStringExtra("titre_cours"));}

                if (intent.hasExtra("title")){
                    title= (intent.getStringExtra("title"));}
            if (intent.hasExtra("id_cours")){
                id_cours= (intent.getStringExtra("id_cours"));}

                if (intent.hasExtra("id_theme_choisi")){
                    id_theme_choisi = intent.getIntExtra("id_theme_choisi",0);
                }
                if (intent.hasExtra("theme")){
                    theme = intent.getStringExtra("theme");
                }
                if (intent.hasExtra("id_niveau_choisi")){
                    id_niveau_choisi = intent.getIntExtra("id_niveau_choisi",0);
                }
                if (intent.hasExtra("niveau")){
                    niveau = intent.getStringExtra("niveau");

                }
                if (intent.hasExtra("num_niveau")){
                    num_niveau = intent.getIntExtra("num_niveau",0);}
                if (intent.hasExtra("id_user")){
                    id_user = intent.getStringExtra("id_user");
                }
             titrePlaques=findViewById(R.id.titrePlaques);
              titrePlaques.setText(title);

            }
        testButton=findViewById(R.id.testButton);
        testButton.setOnClickListener(goToTest);
        ll= findViewById(R.id.linear);
        ll1= findViewById(R.id.linear1);
        ll2= findViewById(R.id.linear2);
        ll3=findViewById(R.id.linear3);
        ll4= findViewById(R.id.linear4);
        ll5= findViewById(R.id.linear5);
        ll6= findViewById(R.id.linear6);
        ll7= findViewById(R.id.linear7);
        ll8= findViewById(R.id.linear8);
        ll9= findViewById(R.id.linear9);
        ll10= findViewById(R.id.linear10);
        ll11= findViewById(R.id.linear11);
        ll12= findViewById(R.id.linear12);
        ll13= findViewById(R.id.linear13);
        ll14= findViewById(R.id.linear14);
        ll15= findViewById(R.id.linear15);
        ll16= findViewById(R.id.linear16);
        ll17= findViewById(R.id.linear17);
        ll18= findViewById(R.id.linear18);
        ll19= findViewById(R.id.linear19);
        ll20= findViewById(R.id.linear20);
        ll21= findViewById(R.id.linear21);
        ll22= findViewById(R.id.linear22);
        ll23= findViewById(R.id.linear23);
        ll24= findViewById(R.id.linear24);
        ll25= findViewById(R.id.linear25);
        ll26= findViewById(R.id.linear26);

        ll.setVisibility(View.GONE);
        ll1.setVisibility(View.GONE);
        ll2.setVisibility(View.GONE);
        ll3.setVisibility(View.GONE);
        ll4.setVisibility(View.GONE);
        ll5.setVisibility(View.GONE);
        ll6.setVisibility(View.GONE);
        ll7.setVisibility(View.GONE);
        ll8.setVisibility(View.GONE);
        ll9.setVisibility(View.GONE);
        ll10.setVisibility(View.GONE);
        ll11.setVisibility(View.GONE);
        ll12.setVisibility(View.GONE);
        ll14.setVisibility(View.GONE);
        ll15.setVisibility(View.GONE);
        ll16.setVisibility(View.GONE);
        ll17.setVisibility(View.GONE);
        ll18.setVisibility(View.GONE);
        ll19.setVisibility(View.GONE);
        ll20.setVisibility(View.GONE);
        ll21.setVisibility(View.GONE);
        ll22.setVisibility(View.GONE);
        ll23.setVisibility(View.GONE);
        ll24.setVisibility(View.GONE);
        ll25.setVisibility(View.GONE);
        ll26.setVisibility(View.GONE);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray array = new JSONArray(response);

                    for(int i=0;i<(array.length());i++){
                        rows.add(new Plaque(array.getJSONObject(i)));
                        titres.add(rows.get(i).getNom_plaque());
                        images.add(rows.get(i).getPlaque());
                        descriptions.add(rows.get(i).getDescription());
                        String nom_plaque=rows.get(i).getPlaque();
                        ImageView image =new ImageView(getApplicationContext());

                        image.setPadding(20,10,20,10);
                        int imgid=getResources().getIdentifier(nom_plaque,"drawable","com.example.easycode");
                        image.setImageDrawable(getResources().getDrawable(imgid));
                        image.setTag(i);
                        image.setOnClickListener(clickPlaque);
                         if(i<3){ll.addView(image);ll.setVisibility(View.VISIBLE);}
                       else if(i<6){ll1.addView(image);ll1.setVisibility(View.VISIBLE);}
                        else if(i<9){ll2.addView(image);ll2.setVisibility(View.VISIBLE);}
                        else if(i<12){ll3.addView(image);ll3.setVisibility(View.VISIBLE);}
                        else if(i<15){ll4.addView(image);ll4.setVisibility(View.VISIBLE);}
                        else if(i<18){ll5.addView(image);ll5.setVisibility(View.VISIBLE);}
                        else if(i<21){ll6.addView(image);ll6.setVisibility(View.VISIBLE);}
                        else if(i<24){ll7.addView(image);ll7.setVisibility(View.VISIBLE);}
                        else if(i<27){ll8.addView(image);ll8.setVisibility(View.VISIBLE);}
                        else if(i<30){ll9.addView(image);ll9.setVisibility(View.VISIBLE);}
                        else if(i<33){ll10.addView(image);ll10.setVisibility(View.VISIBLE);}
                         else if(i<36){ll11.addView(image);ll11.setVisibility(View.VISIBLE);}
                         else if(i<39){ll12.addView(image);ll12.setVisibility(View.VISIBLE);}
                         else if(i<42){ll13.addView(image);ll13.setVisibility(View.VISIBLE);}
                         else if(i<45){ll14.addView(image);ll14.setVisibility(View.VISIBLE);}
                         else if(i<48){ll15.addView(image);ll15.setVisibility(View.VISIBLE);}
                         else if(i<51){ll16.addView(image);ll16.setVisibility(View.VISIBLE);}
                         else if(i<54){ll17.addView(image);ll17.setVisibility(View.VISIBLE);}
                         else if(i<57){ll18.addView(image);ll18.setVisibility(View.VISIBLE);}
                         else if(i<60){ll19.addView(image);ll19.setVisibility(View.VISIBLE);}
                         else if(i<63){ll20.addView(image);ll20.setVisibility(View.VISIBLE);}
                         else if(i<66){ll21.addView(image);ll21.setVisibility(View.VISIBLE);}
                         else if(i<69){ll22.addView(image);ll22.setVisibility(View.VISIBLE);}
                         else if(i<72){ll23.addView(image);ll23.setVisibility(View.VISIBLE);}
                         else if(i<75){ll24.addView(image);ll24.setVisibility(View.VISIBLE);}
                         else if(i<78){ll25.addView(image);ll25.setVisibility(View.VISIBLE);}
                         else if(i<81){ll26.addView(image);ll26.setVisibility(View.VISIBLE);}





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
                Map<String,String> params=new HashMap<>();
                params.put("titre_cours", titre_cours);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(MenuPlaqueActivity.this);
        requestQueue.add(stringRequest);








    }
    private View.OnClickListener clickPlaque =new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Plaque p=(Plaque)rows.get((int)v.getTag());
            String description=p.getDescription();
            String nom_plaque=p.getNom_plaque();
            String image=p.getPlaque()+"g";
            Intent intent=new Intent(MenuPlaqueActivity.this,SuivanteActivity.class);
            intent.putExtra("description",description);
            intent.putExtra("titre",nom_plaque);
            intent.putExtra("image",image);
           intent.putExtra("position",(int)v.getTag());
            intent.putStringArrayListExtra("titres",titres);
            intent.putStringArrayListExtra("images",images);
            intent.putStringArrayListExtra("descriptions",descriptions);

            startActivity(intent);
        }
    };


    private View.OnClickListener goToTest=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MenuPlaqueActivity.this,TestActivity.class);
            intent.putExtra("id_user",id_user);
            intent.putExtra("num_niveau",num_niveau);
            intent.putExtra("titre_cours",titre_cours);
            intent.putExtra("titre_theme",theme);
            intent.putExtra("id_cours",id_cours);
            startActivity(intent);
        }
    };


}
