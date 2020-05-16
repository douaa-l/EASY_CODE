package com.example.easycode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import com.example.easycode.Dialogs.NegativeDialog;
import com.example.easycode.Outils.Outils;
import com.example.easycode.Outils.unTest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestBlancActivity extends AppCompatActivity {
    String id_user;
    int nbQuestions=1;
    ArrayList<unTest> rows=new ArrayList<>();
    int nb=(nbQuestions*2)*20;
    int nb2=nb;
    int total=0;
    LinearLayout ll=null;
    TextView question,correction;
    TextView numquestion=null;
    ImageView imagequestion=null;
    Button nextQuestion=null;
    Dialog epicDialog=null;
    int numero=1;
    private static String URL_QST= Outils.getPath()+"projet/includes/test_primaire.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_blanc);

        Intent intent=getIntent();
        if(intent!=null){
            if(intent.hasExtra("id_user")){
              id_user=intent.getStringExtra("id_user") ;
            }
            epicDialog=new Dialog(this);
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

            RequestQueue requestQueue = Volley.newRequestQueue(TestBlancActivity.this);
            requestQueue.add(stringRequest);
        }

        public void showNegative(){
            NegativeDialog negative=new NegativeDialog();
            Bundle bundle=new Bundle(0);
            bundle.putInt("moyenne",total);
            bundle.putInt("nbQst",nb2);
            negative.setArguments(bundle);
            negative.show(getSupportFragmentManager(),"Negative");
        }






        public View.OnClickListener suivant=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nb--;
                correction.setVisibility(View.GONE);
                if(nb!=0){
                    afficheQst(numero);
                    System.out.println("on passe a la question s"+numero);
                    numero++;

                }else {evaluer();}
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
                Button b=new Button(TestBlancActivity.this);
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
                    total++;
                }else {
                    b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    correction.setVisibility(View.VISIBLE);
                    Resources res = getResources();
                    String chaine = res.getString(R.string.correction,rows.get(i).getReponseJuste());
                    correction.setText(chaine);

                }


            }
        };
        public void evaluer(){
            Log.d("evaluation", "evaluer: "+total);
            if(total<30){
                showNegative();
            }else {
                showReussite();
            }
        }

        public void showReussite(){

                epicDialog.setContentView(R.layout.testblancresult);
                Button testblanc=(Button)epicDialog.findViewById(R.id.continuertestblanc);
                TextView text=epicDialog.findViewById(R.id.phrasetestblanc);
                Resources res = getResources();
                String chaine = res.getString(R.string.testblanc,total,nb2);
                text.setText(chaine);

                testblanc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        epicDialog.dismiss();
                        finish();
                    }
                });
               ImageView closePositif=(ImageView)epicDialog.findViewById(R.id.closetestblanc);
                closePositif.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        epicDialog.dismiss();
                        finish();
                    }

                });

                epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                epicDialog.show();


        }

}
