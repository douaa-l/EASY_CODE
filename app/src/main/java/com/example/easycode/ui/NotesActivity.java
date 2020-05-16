package com.example.easycode.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easycode.Outils.Outils;
import com.example.easycode.Outils.unTest;
import com.example.easycode.Outils.uneNote;
import com.example.easycode.R;
import com.example.easycode.TestActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NotesActivity extends Fragment {
    Button ajouter;
    LinearLayout listesv;
    Dialog epicDialog;
    String note="";
    String id_user;
    int num_note=1;
    EditText noteText;
    ArrayList<uneNote> rows=new ArrayList<uneNote>();
    private static String URL_ADD= Outils.getPath()+"users/ajouter_note.php";
    private static String URL_AFFICHE=Outils.getPath()+"users/afficher_notes.php";
    private static String URL_DELETE=Outils.getPath()+"users/supprimer_note.php";


    @Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        id_user=getArguments().getString("id_user");
    return inflater.inflate(R.layout.fragment_notifications,container,false);

}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
     ajouter=view.findViewById(R.id.ajouter);
     listesv=view.findViewById(R.id.listesv);
     epicDialog=new Dialog(getActivity());
     ajouter.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             ajouternote();
         }
     });
     actualise();
    }

    public void ajouternote(){
        epicDialog.setContentView(R.layout.newnote);

        noteText = epicDialog.findViewById(R.id.note);
        ImageView close = epicDialog.findViewById(R.id.closeNote);
        Button add=epicDialog.findViewById(R.id.continuernote);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
        add.setOnClickListener(addNote);
        Random ran=new Random();
        int numero=ran.nextInt(3)+1;
        int imgid = getResources().getIdentifier("postit"+numero,"drawable","com.example.easycode");
        noteText.setBackgroundResource(imgid);

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();


    }
    public View.OnClickListener addNote=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            note= String.valueOf(noteText.getText());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    System.out.println(response);
                    try {
                        System.out.println("num note _____________________"+num_note);
                        JSONObject jsonObject = new JSONObject(response);

                        boolean error=jsonObject.getBoolean("error");
                        String message=jsonObject.getString("message");
                        if(!error){
                            epicDialog.dismiss();
                            actualise();
                        }
                        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();





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
                    params.put("note",note );
                    params.put("num_note", String.valueOf(num_note));


                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);

        }
    };

    public void actualise(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_AFFICHE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                  /*  for(int i=0;i<(rows.size());i++){
                        rows.remove(i);
                    }
                   */
                  rows.clear();
                  listesv.removeAllViewsInLayout();
                    for(int i=0;i<(jsonArray.length());i++){
                        rows.add(new uneNote(jsonArray.getJSONObject(i)));

                    }
                    num_note=rows.size()+1;
                    for (int a=0 ;a<rows.size();a++){
                        int i=a+1;
                        Button b=new Button(getActivity());
                        b.setText("Note "+i);
                        b.setTextSize(18);
                        b.setTextColor(getResources().getColor(R.color.orange));
                        b.setBackgroundColor(getResources().getColor(R.color.blanc));
                        b.setPadding(10,40,10,40);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, 5, 0, 5);
                        b.setLayoutParams(params);
                        b.setOnClickListener(affichernote);
                        b.setTag(a);
                        listesv.addView(b);
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


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public View.OnClickListener affichernote=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position=(int)v.getTag();
epicDialog.setContentView(R.layout.lanote);
Button supprimer=epicDialog.findViewById(R.id.supprimer);
supprimer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        supprimer (rows.get(position).getId_note());
    }
});
            TextView postit=epicDialog.findViewById(R.id.postit);
            ImageView close=epicDialog.findViewById(R.id.closepostit);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    epicDialog.dismiss();
                }
            });
            Random ran=new Random();
            int numero=ran.nextInt(3)+1;
            int imgid = getResources().getIdentifier("postit"+numero,"drawable","com.example.easycode");
            postit.setBackgroundResource(imgid);
            postit.setText(rows.get(position).getNote());
            epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            epicDialog.show();
        }
    };
    public int getposition( int id_note){
        int position=0;
        boolean stop=false;
        while (position<rows.size() && !stop){
            if(rows.get(position).getId_note()==id_note){
                stop=true;
            }
        }
       return position;
    }
    public void supprimer(final int id_note){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    boolean error=jsonObject.getBoolean("error");
                    String message=jsonObject.getString("message");
                    if(!error){
                        epicDialog.dismiss();
                        actualise();
                    }
                    Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();


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
                params.put("id_note", String.valueOf(id_note));


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
