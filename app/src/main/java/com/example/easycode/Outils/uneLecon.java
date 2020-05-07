package com.example.easycode.Outils;

import org.json.JSONException;
import org.json.JSONObject;

public class uneLecon {
    String titre="";
    String codeCours="";
    int etat=0;
    String id_cours;
    int evaluation;




    public uneLecon(JSONObject object){
        try {
            this.codeCours= object.getString("titre_cours");
            this.etat = object.getInt("etat_lecon");
            this.evaluation = object.getInt("eval_lecon");
            this.id_cours=object.getString("id_cours");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public String getId_cours() {
        return id_cours;
    }
    public int getEvaluation(){return evaluation;}
    public String getTitre(){
        String code="";
        switch (this.codeCours){
            case "danger":
                code="Dangers";
                break;
            case "intersection":
                code="Relatifs aux intersections";
                break;
            case "interdiction":
                code="Interdictions/fin d’interdictions";
                break;
            case "obligation":
                code="Obligations/Fin d’obligations";
                break;
            case "indication":
                code="Indications et balises";
                break;
            case "temporaire":
                code="Plaques temporaires";
                break;
            default:
                code=this.codeCours;
                break;
        }
        return code;}

    public String getEtat(){
        if (this.etat==0 )return "blocked";
        else return "unblocked";
    }

    public String getCodeCours(){
        return  this.codeCours;

    }


}
