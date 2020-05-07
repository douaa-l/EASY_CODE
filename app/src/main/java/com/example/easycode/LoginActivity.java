package com.example.easycode;

import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easycode.Outils.Outils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.view.KeyEvent.KEYCODE_DEL;

public class LoginActivity extends AppCompatActivity {

    Button cnct=null;
    ImageView img=null;
    RelativeLayout r=null;
    Button login=null;
    Button signup=null;
    EditText un=null;
    EditText mdp=null;
    ProgressBar pb=null;
    private static String URL_REGIST=Outils.getPath()+"users/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        pb=(ProgressBar)findViewById(R.id.progressBarLogin);
        cnct=(Button)findViewById(R.id.connecter);
        img=(ImageView)findViewById(R.id.voiturelogin);
        r=(RelativeLayout)findViewById(R.id.fondlogin);
        login=(Button)findViewById(R.id.login);
        signup=(Button)findViewById(R.id.inscription);
        un=(EditText)findViewById(R.id.nomUser);
        mdp=(EditText)findViewById(R.id.MDP);


        final MediaPlayer demarrage =MediaPlayer.create(this,R.raw.sondemarrage);

        img.setVisibility(View.INVISIBLE);

       initialisation();

        un.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Ne réagir qu'à l'appui sur une touche (et pas au relâchement)
                if(event.getAction() == 0){
                    // S'il s'agit d'un appui sur la touche « entrée »
                    if(keyCode == 66)
                    {
                        Outils.hideKeyboard(LoginActivity.this);

                    }

                }

                return false;
            }

        });
        mdp.setOnKeyListener(new View.OnKeyListener() {
                                 @Override
                                 public boolean onKey(View v, int keyCode, KeyEvent event) {
                                     // Ne réagir qu'à l'appui sur une touche (et pas au relâchement)
                                     if(event.getAction() == 0)
                                         // S'il s'agit d'un appui sur la touche « entrée »
                                         if(keyCode == 66)
                                         {
                                             Outils.hideKeyboard(LoginActivity.this);

                                         }
                                     return false;
                                 }
                             }

        );

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r.setVisibility(View.VISIBLE);
                ObjectAnimator animatorX =ObjectAnimator.ofFloat(r,"x",-600f,10f);
                animatorX.setDuration(1000);
                animatorX.start();

                ObjectAnimator animatorY =ObjectAnimator.ofFloat(signup,"y",400f);
                animatorY.setDuration(1000);
                animatorY.start();

                ObjectAnimator animatorZ =ObjectAnimator.ofFloat(login,"y",45f);
                animatorZ.setDuration(1000);
                animatorZ.start();


            }
        });
        cnct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cnct.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);

                Outils.hideKeyboard(LoginActivity.this);

                final String username = un.getText().toString();
                final String password = mdp.getText().toString();



                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Boolean erreur =jsonObject.getBoolean("error");
                            String msgErreur=jsonObject.getString("message");
                            final String id_user=jsonObject.getString("id_user");



                            if (!erreur){

                                img.setVisibility(View.VISIBLE);
                                ObjectAnimator animatorX =ObjectAnimator.ofFloat(img,"x",-480f,480f);
                                animatorX.setDuration(2500);
                                animatorX.start();
                                demarrage.start();

                                cnct.setVisibility(View.VISIBLE);
                                pb.setVisibility(View.GONE);
                                demarrage.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        Intent intent=new Intent(LoginActivity.this,NiveauActivity.class);
                                        intent.putExtra("id_user",id_user);
                                        startActivity(intent);
                                    }
                                });


                            }else{Toast.makeText(LoginActivity.this, msgErreur, Toast.LENGTH_LONG).show();

                                pb.setVisibility(View.GONE);
                                cnct.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, getString(R.string.loginechoué)+e.toString(), Toast.LENGTH_LONG).show();
                            cnct.setVisibility(View.VISIBLE);
                            pb.setVisibility(View.GONE);
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, getString(R.string.loginechoué)+error.toString(), Toast.LENGTH_LONG).show();
                                cnct.setVisibility(View.VISIBLE);

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<>();
                        params.put("username", username);
                        params.put("password",password);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);
            }
        });




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent InscriptionActivity = new Intent(LoginActivity.this, com.example.easycode.InscriptionActivity.class);
                startActivity(InscriptionActivity);


            }
        });



            }

            void initialisation(){
        un.getText().clear();
        mdp.getText().clear();
                r.setVisibility(View.INVISIBLE);
                ObjectAnimator animatorX =ObjectAnimator.ofFloat(login,"y",250f);
                animatorX.setDuration(1000);
                animatorX.start();

                ObjectAnimator animatorY =ObjectAnimator.ofFloat(signup,"y",370f);
                animatorY.setDuration(1000);
                animatorY.start();
            }

    @Override
    protected void onResume() {
        super.onResume();
        initialisation();
    }
}


