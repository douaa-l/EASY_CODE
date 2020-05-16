package com.example.easycode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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
import com.example.easycode.Outils.unTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestPrimaireActivity extends AppCompatActivity {
    private static String URL_QST= Outils.getPath()+"projet/includes/test_primaire.php";
    private static String URL_SIGNALISATION= Outils.getPath()+"projet/includes/evaluate_TP_signalisation.php";
    private static String URL_CIRCULATION= Outils.getPath()+"projet/includes/evaluate_TP_circulation.php";
    private static String URL_DIVERS= Outils.getPath()+"projet/includes/evaluate_TP_divers.php";
    private static String URL_NIVEAU=Outils.getPath()+"projet/includes/update_test_niveau.php";

    ArrayList<unTest> rows=new ArrayList<>();
    String id_user;
    int nbQuestions=1;
    int noteCirculation=0;
    int noteSignalisation=0;
    int noteDivers=0;
    int nbCirculation=10;
    int nbSignalisation=12;
    int nbDivers=18;
    int nb=40;
    int nb2=40;
    int numero=1;
    LinearLayout ll=null;
    TextView question,correction;
    TextView numquestion=null;
    ImageView imagequestion=null;
    Button nextQuestion=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_primaire);
        Intent intent=getIntent();
        if(intent!=null){
            if(intent.hasExtra("id_user")){
                id_user=intent.getStringExtra("id_user") ;
            }

            correction=findViewById(R.id.correction);
            nextQuestion=findViewById(R.id.nextQuestion);
            nextQuestion.setOnClickListener(suivant);
            ll=findViewById(R.id.llchoix);
            imagequestion=findViewById(R.id.imagequestion);
            numquestion=findViewById(R.id.numquestion);
            question=findViewById(R.id.question);


            getQuestions();



        }}
    public void getQuestions(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_QST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response+"---------//"+id_user+"//"+nbQuestions);
                try {

                    JSONArray array = new JSONArray(response);

                    for(int i=0;i<(array.length());i++){
                        rows.add(new unTest(array.getJSONObject(i)));
                        System.out.print(rows.get(i).getImage()+"//");


                    }
                 afficheQst(0);



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
                params.put("id_user", String.valueOf(id_user));
                params.put("nbQstParType", String.valueOf(nbQuestions));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(TestPrimaireActivity.this);
        requestQueue.add(stringRequest);
    }
    public View.OnClickListener suivant=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(nbSignalisation!=0){
               nbSignalisation--;
            }else{if(nbCirculation!=0){
                nbCirculation--;
            }else{if(nbDivers!=0){
                nbDivers--;
            }
            }
            }
            nb--;
            correction.setVisibility(View.GONE);
            if(nb!=0){
                afficheQst(numero);
                System.out.println("on passe a la question s"+numero);
                numero++;

            }else {evaluer(URL_SIGNALISATION,noteSignalisation);
                    evaluer(URL_CIRCULATION,noteCirculation);
                    evaluer(URL_DIVERS,noteDivers);
                    updateTestNiveau(1);
                    updateTestNiveau(2);

                    Intent intent=new Intent (TestPrimaireActivity.this,TutoActivity.class);
                intent.putExtra("id_user",id_user);
                startActivity(intent);
                finish();
            }
        }};

    public void afficheQst(int num){
        int imgid;
        imagequestion.setVisibility(View.GONE);
        if(!rows.get(num).getImage().equals("")){


            imgid = getResources().getIdentifier(rows.get(num).getImage()+"g","drawable","com.example.easycode");
            if(imgid==0) { imgid = getResources().getIdentifier(rows.get(num).getImage(),"drawable","com.example.easycode");
            }

            if(imgid!=0){
                imagequestion.setImageDrawable(getResources().getDrawable(imgid));
                imagequestion.setVisibility(View.VISIBLE);}
        }
        ll.removeAllViewsInLayout();
        int a=num+1;
        numquestion.setText("Question "+a);
        question.setText(rows.get(num).getQuestion());
        String []table=rows.get(num).aleaChoix();
        int max=2;
        if(rows.get(num).getType_quiz().equals("multi"))max=3;
        for (int i=0;i<max;i++){
            System.out.println("&&&&&&&&&&&&&&&&&"+table[i]);
            Button b=new Button(TestPrimaireActivity.this);
            b.setText(table[i]);
            b.setTextSize(18);
            b.setTextColor(getResources().getColor(R.color.blanc));
            b.setPadding(10,40,10,40);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 10, 0, 10);
            b.setLayoutParams(params);
            b.setOnClickListener(verifierReponse);
            b.setTag(num);
            ll.addView(b);

        }



    }
    public View.OnClickListener verifierReponse=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Button b=(Button)v;
            b.setClickable(false);
            int i=(int)v.getTag();


            for (int a=0;a<ll.getChildCount();a++){


                Button button=(Button)ll.getChildAt(a);
                System.out.println("le nombre de boutons est de "+ll.getChildCount()+"/"+(button.getText()));

                if(button.getText()!=b.getText()){

                    button.setVisibility(View.INVISIBLE);}

            }


            if((b.getText()).equals(rows.get(i).getReponseJuste())){
                b.setBackgroundColor(getResources().getColor(R.color.vert));
                if(nbSignalisation!=0){
                    noteSignalisation++;
                }else{if(nbCirculation!=0){
                    noteCirculation++;
                }else{if(nbDivers!=0){
                    noteDivers++;
                }
                }
                }
            }else {
                b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                correction.setVisibility(View.VISIBLE);
                Resources res = getResources();
                String chaine = res.getString(R.string.correction,rows.get(i).getReponseJuste());
                correction.setText(chaine);

            }


        }
    };
    public void evaluer(String url, final int note){
        Log.d("circulation", "evaluer: "+noteCirculation);
        Log.d("signalisation", "evaluer: "+noteSignalisation);
        Log.d("divers", "evaluer: "+noteDivers);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("c'est fait---------//"+id_user);

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
                params.put("id_user", String.valueOf(id_user));
                params.put("moyenne", String.valueOf(note));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(TestPrimaireActivity.this);
        requestQueue.add(stringRequest);



        }
    public void updateTestNiveau(final int num_niveau){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NIVEAU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response);

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
                params.put("id_user",id_user );
                params.put("num_niveau", String.valueOf(num_niveau));


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(TestPrimaireActivity.this);
        requestQueue.add(stringRequest);
    }
    }



