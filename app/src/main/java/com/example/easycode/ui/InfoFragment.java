package com.example.easycode.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easycode.Dialogs.DeleteDialog;
import com.example.easycode.Dialogs.MdpDialog;
import com.example.easycode.InscriptionActivity;
import com.example.easycode.Outils.Outils;
import com.example.easycode.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class InfoFragment extends Fragment {
    Button modif,annuler;
    TextView username,modifMDP;
    TextView sexe=null;
    TextView date=null;
    TextView email=null;
    ImageView image=null;
    String id_user;
    String password;
    String newsexe;
    String newdate;
    String newusername;
    String newemail;
    TextView delete;



    EditText etusername=null;
    RadioGroup etsexe;
    RadioButton fille ,garcon;

    TextView etdate=null;
    EditText etemail=null;

    DatePickerDialog.OnDateSetListener mDateSetListner;

    private static String URL_REGIST=Outils.getPath()+"users/getInfo.php";
    private static String URL_MODIF=Outils.getPath()+"users/modif_info.php";
    private static String URL_MDP=Outils.getPath()+"users/modif_password.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      id_user=getParentFragment().getArguments().getString("id_user");
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

     @Override
    public void onViewCreated(View view,@Nullable Bundle savedInsranceState ) {


        modif=getActivity().findViewById(R.id.modifier);
        modif.setOnClickListener(clickBouton);
        modifMDP=getActivity().findViewById(R.id.modifMDP);
        modifMDP.setOnClickListener(modifierMDP);
        annuler=getActivity().findViewById(R.id.annuler);
        annuler.setOnClickListener(annulerModif);
        username=getActivity().findViewById(R.id.tvUserName);
        sexe=getActivity().findViewById(R.id.tvSexe);
        date=getActivity().findViewById(R.id.tvDate);
        email=getActivity().findViewById(R.id.tvEmail);
        image=getActivity().findViewById(R.id.tvImage);
        etusername=getActivity().findViewById(R.id.etUserName);
        etdate=getActivity().findViewById(R.id.etDate);
        etdate.setOnClickListener(modifierDate);
        etemail=getActivity().findViewById(R.id.etEmail);
        etsexe=getActivity().findViewById(R.id.etSexe);
        fille=getActivity().findViewById(R.id.fille);
        garcon=getActivity().findViewById(R.id.garçon);
        delete=getActivity().findViewById(R.id.supprimerCompte);
        delete.setOnClickListener(supprimerCompte);


        mDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String dateOfBirth= dayOfMonth+ "/" +month+ "/" +year;
                etdate.setText(dateOfBirth);


                }
            };

getInformation();



    }
    public View.OnClickListener clickBouton=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button b=(Button)v;
            String contenu=(String)b.getText();
            if(contenu.equals("modifier les informations")){

                username.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                sexe.setVisibility(View.GONE);
                date.setVisibility(View.GONE);

                if(sexe.getText().equals("fille")){
                    fille.setChecked(true);
                    garcon.setChecked(false);
                }else {
                    garcon.setChecked(true);
                    fille.setChecked(false);
                }
                etdate.setText(date.getText());
                etemail.setText(email.getText());
                etusername.setText(username.getText());

                etdate.setVisibility(View.VISIBLE);
                etsexe.setVisibility(View.VISIBLE);
                etemail.setVisibility(View.VISIBLE);
                etusername.setVisibility(View.VISIBLE);
                b.setText("VALIDER");
                annuler.setVisibility(View.VISIBLE);



            }else{

                modifierInfo();
            }




        }
    };

    private void modifierInfo(){

newsexe="";
  newdate=Outils.convertDate((String)etdate.getText());
  newusername= String.valueOf(etusername.getText());
   newemail= String.valueOf(etemail.getText());



            if(fille.isChecked()){
                image.setImageDrawable(getResources().getDrawable(R.drawable.girl));
                newsexe="fille";

            }else {
                image.setImageDrawable(getResources().getDrawable(R.drawable.boy));
                newsexe="garçon";
            }

        System.out.println("---------------"+newdate+newemail+newsexe+newusername);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MODIF, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("______________"+response);


                  try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean erreur=jsonObject.getBoolean("error");
                        String message=jsonObject.getString("message");
                        if(erreur){
                            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                        }else{
                            modif.setText("modifier les informations");
                            annuler.setVisibility(View.GONE);
                            Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                            etdate.setVisibility(View.GONE);
                            etsexe.setVisibility(View.GONE);
                            etemail.setVisibility(View.GONE);
                            etusername.setVisibility(View.GONE);

                            username.setVisibility(View.VISIBLE);
                            email.setVisibility(View.VISIBLE);
                            sexe.setVisibility(View.VISIBLE);
                            date.setVisibility(View.VISIBLE);

                            getInformation();

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
                    Map<String, String> params = new HashMap<>();
                    params.put("id_user", id_user);
                    params.put("username", newusername);
                    params.put("password", password);
                    params.put("sexe", newsexe);
                    params.put("email", newemail);
                    params.put("date", newdate);


                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);





    };

    private View.OnClickListener modifierDate=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Outils.hideKeyboard(getActivity());
            Calendar cal=Calendar.getInstance();
            int year= cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dialog = new DatePickerDialog(getActivity(),android.R.style.Theme_DeviceDefault_Light_Dialog,mDateSetListner,year,month,day);

            dialog.show();
        }

    };

    private void getInformation(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(response);

                    username.setText(jsonObject.getString("username"));
                    date.setText(Outils.convertDatedebdd(jsonObject.getString("date")));
                    email.setText(jsonObject.getString("email"));
                    password=jsonObject.getString("password");
                    String s=jsonObject.getString("sexe");
                    sexe.setText(s);
                    if(s.equals("fille")){
                        image.setImageDrawable(getResources().getDrawable(R.drawable.girl));
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
                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public View.OnClickListener annulerModif=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            modif.setText("modifier les informations");
            annuler.setVisibility(View.GONE);
            etdate.setVisibility(View.GONE);
            etsexe.setVisibility(View.GONE);
            etemail.setVisibility(View.GONE);
            etusername.setVisibility(View.GONE);

            username.setVisibility(View.VISIBLE);
            email.setVisibility(View.VISIBLE);
            sexe.setVisibility(View.VISIBLE);
            date.setVisibility(View.VISIBLE);
        }
    };


    public View.OnClickListener modifierMDP=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle=new Bundle();
            bundle.putString("id_user",id_user);
            MdpDialog dialog=new MdpDialog();
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(),"MDP");

        }
    };

    public View.OnClickListener supprimerCompte=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Bundle bundle=new Bundle();
            bundle.putString("id_user",id_user);
            DeleteDialog dd=new DeleteDialog();
            dd.setArguments(bundle);
            dd.show(getFragmentManager(),"DELETE");
        }
    };

}


