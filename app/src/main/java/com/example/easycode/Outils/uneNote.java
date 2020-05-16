package com.example.easycode.Outils;

import org.json.JSONException;
import org.json.JSONObject;

public class uneNote {
    int num_note=0;
    String note;
    int id_note;


    public uneNote(JSONObject object){
        try {
            this.note= object.getString("note");
            this.num_note = object.getInt("num_note");
            this.id_note = object.getInt("id_note");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getNum_note() {
        return num_note;
    }
    public int getId_note() {
        return id_note;
    }

    public String getNote() {
        return note;
    }
}
