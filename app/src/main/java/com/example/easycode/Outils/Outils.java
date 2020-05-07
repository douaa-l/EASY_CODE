package com.example.easycode.Outils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easycode.InscriptionActivity;
import com.example.easycode.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Outils {

    public static String convertDate(String unedate){
        String format="YYYY/MM/dd";
        String[] date = unedate.split("/");
        String jour=date[0];
        String mois=date[1];
        String annee=date[2];

        return annee+"/"+mois+"/"+jour;
    }
    public static String convertDatedebdd(String unedate){


        String[] date = unedate.split("-");
        String jour=date[2];
        String mois=date[1];
        String annee=date[0];

        return jour+"/"+mois+"/"+annee;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void inverserVisibility(View view){
        if (view.getVisibility()==View.VISIBLE){
            view.setVisibility(View.INVISIBLE);
        }else view.setVisibility(View.VISIBLE);
    }




    public static boolean verifierTaille(EditText mdp){
        String MDP= String.valueOf(mdp.getText());

        if(MDP.length()<5 || (MDP.length()>15) ) return true;

        return false;
    }
    public static boolean verifiermail (EditText mail){
        String MAIL= String.valueOf(mail.getText());

        if(MAIL.contains("@gmail.com")|| MAIL.contains("@yahoo.com") || MAIL.contains("@outlook.com")|| MAIL.contains("@hotmail.com")) return false;

        return true;
    }
    public static String getPath(){
        return "http://192.168.1.6/";
    }

}
