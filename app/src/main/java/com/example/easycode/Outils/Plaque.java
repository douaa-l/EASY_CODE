package com.example.easycode.Outils;

import org.json.JSONException;
import org.json.JSONObject;

public class Plaque {
    int id_niveau=0;
    int num_niveau=0;
    int eval_niv=0;
    String nom_plaque="";
    String description="";
    String plaque="";

    public Plaque(JSONObject object){
        try {
            this.nom_plaque= object.getString("nom_plaque");
            this.description = object.getString("discription");
            this.plaque = object.getString("plaque");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public String getNom_plaque(){return this.nom_plaque;}
    public String getDescription(){return this.description;}

    public String getPlaque(){return this.plaque;}

}
