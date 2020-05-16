package com.example.easycode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easycode.Dialogs.NegativeDialog;
import com.example.easycode.Outils.Outils;
import com.example.easycode.Outils.Plaque;
import com.example.easycode.Outils.unTest;
import com.example.easycode.Outils.uneLecon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestActivity extends AppCompatActivity {
int num_niveau=0;
String id_user="";
String titre_cours="";
String titre_theme="";
int nbQuestions=3;
int nb=nbQuestions*2;
int total=0;
LinearLayout ll=null;
TextView question,correction;
TextView numquestion=null;
ImageView imagequestion=null;
Button nextQuestion=null;
int num=1;
boolean pause=false;
String id_cours;
int last=0;
Dialog epicDialog=null;
Button positive,negative,themesuivant;
ImageView closePositif,closeNegatif,closeThemesuivant;
    VideoView videoView=null;
    int stopPosition;

    ArrayList<unTest> rows=new ArrayList<unTest>();
    private static String URL_REGIST=Outils.getPath()+"projet/includes/get_qst_cours.php";
    private static String URL_VERIF=Outils.getPath()+"projet/includes/evaluate_cours.php";
    private static String URL_LAST=Outils.getPath()+"projet/includes/evaluate_theme.php";
    private static String URL_UPDATE=Outils.getPath()+"projet/includes/update_eval_theme.php";
    private static String URL_NIVEAU=Outils.getPath()+"projet/includes/update_test_niveau.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent=getIntent();
        if(intent!=null){

            if (intent.hasExtra("num_niveau")){
                num_niveau = intent.getIntExtra("num_niveau",0);}
            if (intent.hasExtra("id_cours")){
                id_cours= (intent.getStringExtra("id_cours"));}
            if (intent.hasExtra("id_user")){
                id_user = intent.getStringExtra("id_user");
            }
            if (intent.hasExtra("titre_cours")){
                titre_cours = intent.getStringExtra("titre_cours");
                if(titre_cours.equals("Sanctions")){
                  last=1 ;
                }
            }
            if (intent.hasExtra("titre_theme")){
                titre_theme = intent.getStringExtra("titre_theme");
            }

            System.out.println("&&&&&&&&&&&&&&&&&&&&&&"+num_niveau+" "+id_user+" "+titre_cours+" "+titre_theme.length()+"/"+nbQuestions+"&&&&&&");


        }
        epicDialog=new Dialog(this);

        correction=findViewById(R.id.correction);
        nextQuestion=findViewById(R.id.nextQuestion);
        nextQuestion.setOnClickListener(suivant);
        ll=findViewById(R.id.llchoix);
        imagequestion=findViewById(R.id.imagequestion);
        numquestion=findViewById(R.id.numquestion);
        question=findViewById(R.id.question);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response+"//////////"+titre_cours+titre_theme+id_user+num_niveau+nbQuestions);

          try {

                    JSONArray array = new JSONArray(response);

                  for(int i=0;i<(array.length());i++){
                      rows.add(new unTest(array.getJSONObject(i)));

                  }

                    if(!rows.get(0).getImage().equals("")){
                        if(titre_theme.equals("signalisation")){
                            int imgid=getResources().getIdentifier(rows.get(0).getImage()+"g","drawable","com.example.easycode");
                            imagequestion.setImageDrawable(getResources().getDrawable(imgid));}  else{
                            int imgid=getResources().getIdentifier(rows.get(0).getImage(),"drawable","com.example.easycode");
                            imagequestion.setImageDrawable(getResources().getDrawable(imgid));

                        }
                  }
                  numquestion.setText("Question 1");
                  question.setText(rows.get(0).getQuestion());
                  String []table=rows.get(0).aleaChoix();
                  int max=2;
                  if(rows.get(0).getType_quiz().equals("multi"))max=3;
                  for (int i=0;i<max;i++){
                      System.out.println("&&&&&&&&&&&&&&&&&"+table[i]);
                      Button b=new Button(TestActivity.this);
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
                      b.setTag(0);
                      ll.addView(b);

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
                params.put("titre_cours", titre_cours);
                params.put("titre_theme", titre_theme);
                params.put("num_niveau", String.valueOf(num_niveau));
                params.put("nbQstParType", String.valueOf(nbQuestions));
                params.put("id_user", id_user);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(TestActivity.this);
        requestQueue.add(stringRequest);
    }

    public View.OnClickListener verifierReponse=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            for (int a=0;a<ll.getChildCount();a++){

Button b=(Button)v;
                Button button=(Button)ll.getChildAt(a);
                System.out.println("le nombre de boutons est de "+ll.getChildCount()+"/"+(button.getText()));

                if(button.getText()!=b.getText()){

                    button.setVisibility(View.INVISIBLE);}

            }
            Button b=(Button)v;
            b.setClickable(false);
            int i=(int)v.getTag();
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

    public View.OnClickListener suivant=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            correction.setVisibility(View.GONE);
nb--;
if(nb!=0){int imgid;
            ll.removeAllViewsInLayout();
    if(!rows.get(num).getImage().equals("")){
if(titre_theme.equals("signalisation")){

    imgid = getResources().getIdentifier(rows.get(num).getImage()+"g","drawable","com.example.easycode");
}
else{ imgid = getResources().getIdentifier(rows.get(num).getImage(),"drawable","com.example.easycode");}
        System.out.println(rows.get(num).getImage()+"  "+imgid);
            imagequestion.setImageDrawable(getResources().getDrawable(imgid));
    }
            question.setText(rows.get(num).getQuestion());
            String []table=rows.get(num).aleaChoix();
            int max=2;
            if(rows.get(num).getType_quiz().equals("multi"))max=3;
            for (int i=0;i<max;i++){
                System.out.println("&&&&&&&&&&&&&&&&&"+table[i]);
                Button b=new Button(TestActivity.this);
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
            num++;
            numquestion.setText("Question "+num);

        }else{
    System.out.println("------------"+id_cours+total+last+"-----------");

   StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_VERIF, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            System.out.println(response);
     try {
               JSONObject jsonObject=new JSONObject(response);


            String etat=jsonObject.getString("etat_cours_prochain");


                if(etat.equals("blocked")){showNegative();}

                else{
                    Boolean dernier =jsonObject.getBoolean("dernier");
                    if(!dernier){
                        showPositive();
                    }else{
                        dernierCoursDuTheme();
                    }
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

            params.put("id_cours", id_cours);
            params.put("moyenne", String.valueOf(total));
         //  params.put("last", String.valueOf(last));

            return params;
        }

    };

    RequestQueue requestQueue = Volley.newRequestQueue(TestActivity.this);
    requestQueue.add(stringRequest);



}

        }
    };

    public void showPositive(){
        epicDialog.setContentView(R.layout.positive);
        positive=(Button)epicDialog.findViewById(R.id.continuerpositive);
        TextView text=epicDialog.findViewById(R.id.phrasepositive);
        Resources res = getResources();
        String chaine = res.getString(R.string.positivephrase,total);
        text.setText(chaine);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                finish();
            }
        });
        closePositif=(ImageView)epicDialog.findViewById(R.id.closePositive);
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
    public void showNegative(){
        NegativeDialog negative=new NegativeDialog();
        Bundle bundle=new Bundle(0);
        bundle.putInt("moyenne",total);
        bundle.putInt("nbQst",6);
        negative.setArguments(bundle);
        negative.show(getSupportFragmentManager(),"Negative");
    }

    public void showThemeSuivant(){
        epicDialog.setContentView(R.layout.themesuivant);
        themesuivant=(Button)epicDialog.findViewById(R.id.continuertheme);
        closeThemesuivant=(ImageView)epicDialog.findViewById(R.id.closeTheme);
        TextView text=epicDialog.findViewById(R.id.phrasetheme);
        Resources res = getResources();
        String chaine = res.getString(R.string.themephrase,total);
        text.setText(chaine);
        closeThemesuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                finish();
            }
        });
        themesuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                finish();

            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    public void showVideo(){
        epicDialog.setContentView(R.layout.video);
        final TextView textView=epicDialog.findViewById(R.id.textSensibilisation);
       videoView=epicDialog.findViewById(R.id.sensibilisation);
        Random ran=new Random();
        int numero=ran.nextInt(2)+1;
        Log.d("numero de la video", String.valueOf(numero));
        Uri uri= Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sensibilisation3);
        switch (numero){
            case 1:
                uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sensibilisation1);
                break;
            case 2:
                uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sensibilisation2);
                break;

        }

        videoView.setVideoURI(uri);
        videoView.setVisibility(View.GONE);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pause){pause=false;
                    Log.d("on doit continuer", "onClick: resume");
                    videoView.seekTo(stopPosition);
                    videoView.start();
                }else
                {videoView.pause();pause=true;
                stopPosition = videoView.getCurrentPosition();
                    Log.d("c'est une pause", "onClick: pause");}
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                updateTheme();
                if (!titre_theme.equals("divers")){showThemeSuivant();}
                updateTestNiveau();
                videoView=null;
            }
        });

         final Button video=(Button)epicDialog.findViewById(R.id.continuervideo);
         video.setVisibility(View.VISIBLE);
        ImageView closeVideo=(ImageView)epicDialog.findViewById(R.id.closeVideo);
        closeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                videoView=null;
              finish();
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                videoView.setVisibility(View.VISIBLE);
                videoView.start();
                video.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);

            }
        });
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }


    public void showTestNiveau(){
        epicDialog.setContentView(R.layout.unblocktestniveau);
       Button test=(Button)epicDialog.findViewById(R.id.continuertestniveau);
        ImageView closetestniveau=(ImageView)epicDialog.findViewById(R.id.closetestniveau);
        closetestniveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                finish();
            }

        });
test.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        epicDialog.dismiss();
        finish();

    }
});
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }


    public void dernierCoursDuTheme(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LAST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    String evalProchainTheme =jsonObject.getString("etat_theme_prochain");

                    if(evalProchainTheme.equals("unblocked")){
                        

                        if (!titre_theme.equals("divers")){showThemeSuivant();}

                            updateTestNiveau();

                    }
                    else{showVideo();}


                } catch (JSONException e) {



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
                params.put("id_user",id_user );
                params.put("num_niveau", String.valueOf(num_niveau));
                params.put("titre_theme",titre_theme);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(TestActivity.this);
        requestQueue.add(stringRequest);
    }


    void updateTheme(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response+"''''''''''''''''''''''''''");



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
                params.put("titre_theme",titre_theme);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(TestActivity.this);
        requestQueue.add(stringRequest);

showThemeSuivant();
    }
    public void updateTestNiveau(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NIVEAU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);


                    String etat_test =jsonObject.getString("etat_test");

                    if(etat_test.equals("unblocked")){

                        showTestNiveau();

                    }



                } catch (JSONException e) {



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
                params.put("id_user",id_user );
                params.put("num_niveau", String.valueOf(num_niveau));


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(TestActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
if(videoView!=null){
        if((videoView.isPlaying())){
            videoView.pause();
            pause=true;
            stopPosition = videoView.getCurrentPosition();
            Log.d("c'est une pause", "onClick: pause");
        }
    }}

    @Override
    protected void onResume() {
        super.onResume();
        if(pause){
            Log.d("on doit continuer", "onClick: resume");
            videoView.seekTo(stopPosition);
            videoView.start();
            pause=false;
        }
    }
}
