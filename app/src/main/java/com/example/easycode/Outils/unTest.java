package com.example.easycode.Outils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class unTest {
    String question="";
    String image ="";
    String reponseJuste="";
    String type_quiz="";
    String choix1="";
    String choix2="";




    public  unTest(JSONObject object){
        try {
            this.question= object.getString("contenu");
            this.image = object.getString("image_quiz");
            this.type_quiz = object.getString("type_quiz");
            this.reponseJuste = object.getString("reponse");
            if((this.type_quiz).equals("multi")){
            this.choix1=object.getString("choix_1");
            this.choix2=object.getString("choix_2");
            }else{
                if((this.reponseJuste).equals("vrai")){
                    this.choix1="faux";

                }else this.choix1="vrai";
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getQuestion() {
        return question;
    }

    public String getImage() {
        return image;
    }

    public String getReponseJuste() {
        return reponseJuste;
    }

    public String getType_quiz() {
        return type_quiz;
    }

    public String[] aleaChoix(){
        String[] table=new String[3];
            int max=2;
        if((this.type_quiz).equals("multi")){
            table[0]=choix2;
            table[1]=choix1;
            table[2]=reponseJuste;}else {table[0]=choix1;table[1]=reponseJuste;}



        Random rgen = new Random();  // Random number generator
if(this.type_quiz.equals("multi"))max=3;
        for (int i=0; i<max; i++) {
            int randomPosition = rgen.nextInt(max);
            String temp = table[i];
            table[i] = table[randomPosition];
            table[randomPosition] = temp;
        }

       return table;
    }
}
