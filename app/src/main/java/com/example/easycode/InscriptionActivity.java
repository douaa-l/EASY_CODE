package com.example.easycode;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.easycode.R.raw.messageenvoye;
import static com.example.easycode.R.raw.messagerecu;

public class InscriptionActivity extends AppCompatActivity {


    Button inscrire =null;
    Button valider =null;

   TextView un=null;
    TextView mdp=null;
    TextView email=null;
    TextView date=null;
    TextView sexe=null;


    EditText pseudo=null;
    EditText motdepasse=null;
    EditText emailText=null;
    TextView dateTexte=null;
    RadioGroup sexeReponse=null;

    ImageView carinoUn=null;
    ImageView carinoDeux=null;
    ImageView carinoTrois=null;
    ImageView carinoQuatre=null;
    ImageView carinoCinq=null;
    ImageView carinoSix=null;

    TextView texteUn=null;
    TextView texteDeux=null;
    TextView texteTrois=null;
    TextView texteQuatre=null;
    TextView texteCinq=null;
    TextView texteSix=null;



    //liste qui contient les prochains elements a etre affichés
    ArrayList<View> tv= new ArrayList<View>();

    DatePickerDialog.OnDateSetListener mDateSetListner;


    private static String URL_REGIST=Outils.getPath()+"users/inscription.php";
    private static String URL_VERFIF=Outils.getPath()+"users/verifierpseudoinscription.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        init();
        saisieInfo(pseudo,un,tv);
        saisieInfo(motdepasse,mdp,tv);
        saisieInfo(emailText,email,tv);

        inscrire(inscrire);


        dateTexte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Outils.hideKeyboard(InscriptionActivity.this);
                Calendar cal=Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(InscriptionActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog,mDateSetListner,year,month,day);

                dialog.show();
            }
        });

        mDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String dateOfBirth= dayOfMonth+ "/" +month+ "/" +year;
                dateTexte.setText(dateOfBirth);
                date.setText(dateTexte.getText());
                dateTexte.setVisibility(View.GONE);
                date.setVisibility(View.VISIBLE);

                final MediaPlayer messageenvoye =MediaPlayer.create(InscriptionActivity.this, R.raw.messageenvoye);
                Animation affichage = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.bullediscu);

                date.startAnimation(affichage);
                messageenvoye.start();


                for(View v :tv){
                    Outils.inverserVisibility(v);


                }
        }};

//l'edit texte du pseudo
        un.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //transformer le nuage de la parole de carinoen bulle de discu

                Drawable image= (Drawable)getResources().getDrawable(R.drawable.carinoparole);
                texteUn.setBackground(image);

            }

            @Override
            public void afterTextChanged(Editable s) {

                final MediaPlayer messagerecu =MediaPlayer.create(InscriptionActivity.this, R.raw.messagerecu);

                //modifier le texte de textedeux avec l'info entrée dans l'edit texte du pseudo
                Resources res = getResources();
                String chaine = res.getString(R.string.inscription_2, un.getText());
                texteDeux.setText(chaine);

                //afficher la 2eme parole de carino
                Animation affichage = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.bullediscucarino);
                texteDeux.startAnimation(affichage);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        messagerecu.setLooping(false);
                        messagerecu.setVolume(100, 100);
                        messagerecu.start();
                    }
                }, 600);

                //afficher l'edit text pour entrer le mot de passe
                Animation affichageEditText = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.afficheredittext);
                motdepasse.startAnimation(affichageEditText);

                //enlever le premier carino
                carinoUn.setVisibility(View.GONE);



            }
        });

//l'edit text du mot de passe
        mdp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //transformer le nuage de la parole de carinoen bulle de discu
                Drawable image= (Drawable)getResources().getDrawable(R.drawable.carinoparole);
                texteDeux.setBackground(image);

            }

            @Override
            public void afterTextChanged(Editable s) {


                final MediaPlayer messagerecu =MediaPlayer.create(InscriptionActivity.this, R.raw.messagerecu);

                //enlever le texte2 l'edit texte du mot de passe et le 2eme carino du tableau de vues à passer à la fct saisieinfo
                tv.remove(texteDeux);
                tv.remove(motdepasse);
                tv.remove(carinoDeux);

                //ajouter le 3eme texte le bouton s'inscrire et le 3eme carino
                tv.add(texteQuatre);
                tv.add(emailText);
                tv.add(carinoQuatre);

                //effacer le 2eme carino
                carinoDeux.setVisibility(View.GONE);

                //afficher la 3eme bulle de discu
                Animation affichage = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.bullediscucarino);
                texteQuatre.startAnimation(affichage);

                Animation affichageEditText = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.afficheredittext);
                emailText.startAnimation(affichageEditText);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        messagerecu.setLooping(false);
                        messagerecu.setVolume(100, 100);
                        messagerecu.start();
                    }
                }, 600);
            }
        });
 //l'edit texte de l'e-mail
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //transformer le nuage de la parole de carinoen bulle de discu

                Drawable image= (Drawable)getResources().getDrawable(R.drawable.carinoparole);
                texteQuatre.setBackground(image);

            }

            @Override
            public void afterTextChanged(Editable s) {

                final MediaPlayer messagerecu =MediaPlayer.create(InscriptionActivity.this, R.raw.messagerecu);



                //afficher la 2eme parole de carino
                Animation affichage = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.bullediscucarino);
                texteCinq.startAnimation(affichage);

                //enlever le texte2 l'edit texte du mot de passe et le 2eme carino du tableau de vues à passer à la fct saisieinfo
                tv.remove(texteQuatre);
                tv.remove(emailText);
                tv.remove(carinoQuatre);

                //ajouter le 3eme texte le bouton s'inscrire et le 3eme carino
                tv.add(texteCinq);
                tv.add(dateTexte);
                tv.add(carinoCinq);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        messagerecu.setLooping(false);
                        messagerecu.setVolume(100, 100);
                        messagerecu.start();
                    }
                }, 600);

                //afficher l'edit text pour entrer la date de naissance
                Animation affichageEditText = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.afficheredittext);
                dateTexte.startAnimation(affichageEditText);

                //enlever le premier carino
                carinoQuatre.setVisibility(View.GONE);



            }
        });





        //l'edit texte de la date
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //transformer le nuage de la parole de carinoen bulle de discu

                Drawable image= (Drawable)getResources().getDrawable(R.drawable.carinoparole);
                texteCinq.setBackground(image);

            }

            @Override
            public void afterTextChanged(Editable s) {

                final MediaPlayer messagerecu =MediaPlayer.create(InscriptionActivity.this, R.raw.messagerecu);



                //afficher la 2eme parole de carino
                Animation affichage = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.bullediscucarino);
                texteSix.startAnimation(affichage);

                //enlever le texte2 l'edit texte du mot de passe et le 2eme carino du tableau de vues à passer à la fct saisieinfo
                tv.remove(texteCinq);
                tv.remove(dateTexte);
                tv.remove(carinoCinq);

                //ajouter le 3eme texte le bouton s'inscrire et le 3eme carino
                tv.add(texteSix);
                tv.add(sexeReponse);
                tv.add(valider);
                tv.add(carinoSix);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        messagerecu.setLooping(false);
                        messagerecu.setVolume(100, 100);
                        messagerecu.start();
                    }
                }, 600);

                //afficher l'edit text pour entrer la date de naissance
                Animation affichageEditText = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.afficheredittext);
                sexeReponse.startAnimation(affichageEditText);
                valider.startAnimation(affichageEditText);

                //enlever le premier carino
                carinoCinq.setVisibility(View.GONE);



            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {

                tv.remove(texteSix);
                tv.remove(sexeReponse);
                tv.remove(valider);
                tv.remove(carinoSix);

                //afficher la 2eme parole de carino
                Animation affichageCarino = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.bullediscucarino);
                texteTrois.startAnimation(affichageCarino);

                Drawable image= (Drawable)getResources().getDrawable(R.drawable.carinoparole);
                texteSix.setBackground(image);

                texteSix.setVisibility(View.VISIBLE);
                texteTrois.setVisibility(View.VISIBLE);
                carinoTrois.setVisibility(View.VISIBLE);
                inscrire.setVisibility(View.VISIBLE);

                Animation affichageEditText = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.afficheredittext);
                inscrire.startAnimation(affichageEditText);

                if(sexeReponse.getCheckedRadioButtonId()==R.id.fille){sexe.setText("fille");}else sexe.setText("garçon");

                sexeReponse.setVisibility(View.GONE);
                valider.setVisibility(View.GONE);
                sexe.setVisibility(View.VISIBLE);

                final MediaPlayer messageenvoye =MediaPlayer.create(InscriptionActivity.this, R.raw.messageenvoye);
                Animation affichage = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.bullediscu);

                sexe.startAnimation(affichage);
                messageenvoye.start();

                for(View view:tv){
                    Outils.inverserVisibility(view);


                }

                final MediaPlayer messagerecu =MediaPlayer.create(InscriptionActivity.this, R.raw.messagerecu);



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        messagerecu.setLooping(false);
                        messagerecu.setVolume(100, 100);
                        messagerecu.start();
                    }
                }, 600);



                //enlever le premier carino
                carinoSix.setVisibility(View.GONE);

            }
        });
    }



    //initialisation
    public void init(){
        texteUn=(TextView)findViewById(R.id.textView);
        texteDeux=(TextView)findViewById(R.id.textView2);
        texteTrois=(TextView)findViewById(R.id.textView3);
        texteQuatre=(TextView)findViewById(R.id.textView4);
        texteCinq=(TextView)findViewById(R.id.textView5);
        texteSix=(TextView)findViewById(R.id.textView6);


        inscrire=(Button)findViewById(R.id.inscription);
        valider=(Button)findViewById(R.id.validerSexe);

        un=(TextView)findViewById(R.id.username);
        mdp=(TextView)findViewById(R.id.motdepasse);
        email=(TextView)findViewById(R.id.email);
        date=(TextView)findViewById(R.id.date);
        sexe=(TextView)findViewById(R.id.sexe);


        pseudo=(EditText)findViewById(R.id.editTextNom);
        motdepasse=(EditText) findViewById(R.id.editTextMdp);
        emailText=(EditText)findViewById(R.id.editTextEmail);
        dateTexte=(TextView) findViewById(R.id.editTextDate);
        sexeReponse=(RadioGroup)findViewById(R.id.editSexe);

        carinoUn=(ImageView)findViewById(R.id.carino1);
        carinoDeux=(ImageView)findViewById(R.id.carino2);
        carinoTrois=(ImageView)findViewById(R.id.carino3);
        carinoQuatre=(ImageView)findViewById(R.id.carino4);
        carinoCinq=(ImageView)findViewById(R.id.carino5);
        carinoSix=(ImageView)findViewById(R.id.carino6);

            texteUn.setVisibility(View.VISIBLE);
            final MediaPlayer messagerecu = MediaPlayer.create(InscriptionActivity.this, R.raw.messagerecu);

            //afficher le premier texte
            Animation affichage = AnimationUtils.loadAnimation(InscriptionActivity.this, R.anim.bullediscucarino);
            texteUn.startAnimation(affichage);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    messagerecu.setLooping(false);
                    messagerecu.setVolume(100, 100);
                    messagerecu.start();
                }
            }, 600);


            //afficher l'edit texte du pseudo
            Animation affichageEditText = AnimationUtils.loadAnimation(InscriptionActivity.this, R.anim.afficheredittext);
            pseudo.startAnimation(affichageEditText);

        texteDeux.post(new Runnable() {
            @Override
            public void run() {
                texteDeux.setVisibility(View.INVISIBLE);
            }
        });
        texteTrois.post(new Runnable() {
            @Override
            public void run() {
                texteTrois.setVisibility(View.INVISIBLE);
            }
        });
        texteQuatre.post(new Runnable() {
            @Override
            public void run() {
                texteQuatre.setVisibility(View.INVISIBLE);
            }
        });
        texteCinq.post(new Runnable() {
            @Override
            public void run() {
                texteCinq.setVisibility(View.INVISIBLE);
            }
        });
        texteSix.post(new Runnable() {
            @Override
            public void run() {
                texteSix.setVisibility(View.INVISIBLE);
            }
        });

        carinoDeux.post(new Runnable() {
            @Override
            public void run() {
                carinoDeux.setVisibility(View.INVISIBLE);
            }
        });
        carinoTrois.post(new Runnable() {
            @Override
            public void run() {
                carinoTrois.setVisibility(View.INVISIBLE);
            }
        });
        carinoQuatre.post(new Runnable() {
            @Override
            public void run() {
                carinoQuatre.setVisibility(View.INVISIBLE);
            }
        });
        carinoCinq.post(new Runnable() {
            @Override
            public void run() {
                carinoCinq.setVisibility(View.INVISIBLE);
            }
        });
        carinoSix.post(new Runnable() {
            @Override
            public void run() {
                carinoSix.setVisibility(View.INVISIBLE);
            }
        });

        motdepasse.post(new Runnable() {
            @Override
            public void run() {
                motdepasse.setVisibility(View.INVISIBLE);
            }
        });
        emailText.post(new Runnable() {
            @Override
            public void run() {
                emailText.setVisibility(View.INVISIBLE);
            }
        });
        dateTexte.post(new Runnable() {
            @Override
            public void run() {
                dateTexte.setVisibility(View.INVISIBLE);
            }
        });

        sexeReponse.post(new Runnable() {
            @Override
            public void run() {
                sexeReponse.setVisibility(View.INVISIBLE);
            }
        });

        un.post(new Runnable() {
            @Override
            public void run() {
                un.setVisibility(View.INVISIBLE);
            }
        });
        mdp.post(new Runnable() {
            @Override
            public void run() {
                mdp.setVisibility(View.INVISIBLE);
            }
        });
        email.post(new Runnable() {
            @Override
            public void run() {
                email.setVisibility(View.INVISIBLE);
            }
        });
        date.post(new Runnable() {
            @Override
            public void run() {
                date.setVisibility(View.INVISIBLE);
            }
        });
        sexe.post(new Runnable() {
            @Override
            public void run() {
                sexe.setVisibility(View.INVISIBLE);
            }
        });
        valider.post(new Runnable() {
            @Override
            public void run() {
                valider.setVisibility(View.INVISIBLE);
            }
        });



        inscrire.post(new Runnable() {
            @Override
            public void run() {
                inscrire.setVisibility(View.INVISIBLE);
            }
        });



    //ajouter au debut au tableau le texte 2 le 2eme carino et l'edit texte du mot de passe
        tv.add(texteDeux);
        tv.add(carinoDeux);
        tv.add(motdepasse);
    }

    public void saisieInfo(final EditText et, final TextView tv,final ArrayList <View>tableDeVues){
        et.setVisibility(View.VISIBLE);
        tv.setVisibility(View.INVISIBLE);

        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // Ne réagir qu'à l'appui sur une touche (et pas au relâchement)
                if(event.getAction() == 0)
                    // S'il s'agit d'un appui sur la touche « entrée »
                    if(keyCode == 66)
                    {   Boolean invalide=false;
                        String erreur="";

                            if(et==pseudo) {


                            final String username= String.valueOf(et.getText());


                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_VERFIF, new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean erreur =jsonObject.getBoolean("error");
                                        String msg=jsonObject.getString(("message"));

                                        if(erreur){
                                            Toast.makeText(InscriptionActivity.this,msg,Toast.LENGTH_LONG).show();
                                        } else{
                                            tv.setText(et.getText());
                                            et.setVisibility(View.GONE);
                                            tv.setVisibility(View.VISIBLE);

                                            final MediaPlayer messageenvoye =MediaPlayer.create(InscriptionActivity.this, R.raw.messageenvoye);
                                            Animation affichage = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.bullediscu);

                                            tv.startAnimation(affichage);
                                            messageenvoye.start();

                                            for(View view:tableDeVues){
                                                Outils.inverserVisibility(view);


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
                                    })
                            {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String>params=new HashMap<>();
                                    params.put("username", username);

                                    return params;
                                }

                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(InscriptionActivity.this);
                            requestQueue.add(stringRequest);




                        }else {
                        if(et==motdepasse){
                            invalide=Outils.verifierTaille(et);
                            erreur=getString(R.string.erreurinscriptiontaille);

                        }
                        if(et==emailText){
                            invalide=Outils.verifiermail(et);
                            erreur="votre adresse e-mail est invalide";
                        }

                        //on cache le clavier ,on met le texte entré dans la bulle de discu et on cache l'edit texte
                        Outils.hideKeyboard(InscriptionActivity.this);

                        if (invalide==false){
                       tv.setText(et.getText());
                       et.setVisibility(View.GONE);
                       tv.setVisibility(View.VISIBLE);

                       final MediaPlayer messageenvoye =MediaPlayer.create(InscriptionActivity.this, R.raw.messageenvoye);
                        Animation affichage = AnimationUtils.loadAnimation(InscriptionActivity.this,R.anim.bullediscu);

                        tv.startAnimation(affichage);
                        messageenvoye.start();

                       for(View view:tableDeVues){
                           Outils.inverserVisibility(view);


                       }}else {Toast.makeText(InscriptionActivity.this,erreur,Toast.LENGTH_LONG).show();}

                    }}
                return false;
            }
        });
    }




    //Inscription
    public void inscrire (final Button inscrire){

        final ProgressBar pb=(ProgressBar)findViewById(R.id.progressBarInscription);
        pb.setVisibility(View.GONE);


        inscrire.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                inscrire.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);


                final String username = un.getText().toString();
                final String password = mdp.getText().toString();
                final String mail = email.getText().toString();
                final String datenaissance= date.getText().toString();
                final String gender=sexe.getText().toString();



                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            Boolean erreur =jsonObject.getBoolean("error");
                            String msgErreur=jsonObject.getString("message");
                            String id_user=jsonObject.getString("id_user");



                            if (!erreur){
                                Toast.makeText(InscriptionActivity.this, R.string.inscriptionreussie, Toast.LENGTH_LONG).show();
                                pb.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(InscriptionActivity.this, DebutantExpertActivity.class);
                                intent.putExtra("id_user",id_user);
                                startActivity(intent);
                                finish();

                            }else{
                                pb.setVisibility(View.INVISIBLE);
                                Toast.makeText(InscriptionActivity.this, getString(R.string.erreurinscription)+msgErreur, Toast.LENGTH_LONG).show();}


                        } catch (JSONException e) {
                            e.printStackTrace();
                            pb.setVisibility(View.INVISIBLE);
                            Toast.makeText(InscriptionActivity.this, getString(R.string.inscriptionechouee)+e.toString(), Toast.LENGTH_LONG).show();


                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(InscriptionActivity.this, getString(R.string.inscriptionechouee)+error.toString(), Toast.LENGTH_LONG).show();



                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<>();
                        params.put("username", username);
                        params.put("password",password);
                        params.put("email",mail);
                        params.put("date", Outils.convertDate(datenaissance));
                        params.put("sexe",gender);
                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(InscriptionActivity.this);
                requestQueue.add(stringRequest);
            }
        });




            }


    }



