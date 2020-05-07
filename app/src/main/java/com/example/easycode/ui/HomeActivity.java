package com.example.easycode.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.easycode.DebutantExpertActivity;
import com.example.easycode.InscriptionActivity;
import com.example.easycode.LevelActivity;
import com.example.easycode.NiveauActivity;
import com.example.easycode.Outils.Outils;
import com.example.easycode.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends Fragment {
    ImageView niveauUn=null;
    ImageView niveauDeux=null;
ImageView niveauTrois=null;
String etatUn="";
String etatDeux="";
String etatTrois="";
int id_niveau_choisi=0;
    int id_niveau1=0;
    int num_niveau=0;
    int a=0;
    boolean testfinal=false;

    String id_user="";
    private static String URL_REGIST=Outils.getPath()+"users/connexion.php";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       id_user=getArguments().getString("id_user");


        return inflater.inflate(R.layout.fragment_home,container,false);

    }

 @Override
    public void onViewCreated(View view,@Nullable Bundle savedInsranceState ) {

     niveauUn = (ImageView) getView().findViewById(R.id.niveau1);
     niveauDeux = (ImageView) getView().findViewById(R.id.niveau2);
     niveauTrois = (ImageView) getView().findViewById(R.id.niveau3);

     niveauUn.setOnClickListener(levelListener);
     niveauDeux.setOnClickListener(levelListener);
     niveauTrois.setOnClickListener(levelListener);

etats();



 }
public void etats(){
    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            System.out.println(response);
            try {
                JSONObject jsonObject = new JSONObject(response);


                etatUn = jsonObject.getString("niveau1");
                etatDeux = jsonObject.getString("niveau2");
                etatTrois = jsonObject.getString("niveau3");
                id_niveau1=jsonObject.getInt("id_niveau_1");

                System.out.println(etatUn+"*******************************************"+jsonObject.getString("message"));



                if (etatUn.equals("blocked")) {
                    niveauUn.setImageDrawable(getResources().getDrawable(R.drawable.niveau1blocked));
                    niveauUn.setMaxHeight(188);
                    niveauUn.setMaxWidth(120);

                }
                if (etatUn.equals("unblocked")) {
                    niveauUn.setImageDrawable(getResources().getDrawable(R.drawable.niveau1etoiles0));
                    niveauUn.setMaxHeight(188);
                    niveauUn.setMaxWidth(120);

                }
                if (etatUn.equals("onestar")) {
                    niveauUn.setImageDrawable(getResources().getDrawable(R.drawable.niveau1etoiles1));
                    niveauUn.setMaxHeight(188);
                    niveauUn.setMaxWidth(120);

                }
                if (etatUn.equals("twostars")) {
                    niveauUn.setImageDrawable(getResources().getDrawable(R.drawable.niveau1etoiles2));
                    niveauUn.setMaxHeight(188);
                    niveauUn.setMaxWidth(120);
                    a++;
                }
                if (etatUn.equals("threestars")) {
                    niveauUn.setImageDrawable(getResources().getDrawable(R.drawable.niveau1etoiles3));
                    niveauUn.setMaxHeight(188);
                    niveauUn.setMaxWidth(120);
                    a++;
                }
                if (etatDeux.equals("blocked")) {
                    niveauDeux.setImageDrawable(getResources().getDrawable(R.drawable.niveau2blocked));
                    niveauDeux.setMaxHeight(188);
                    niveauDeux.setMaxWidth(120);
                }

                if (etatDeux.equals("unblocked")) {
                    niveauDeux.setImageDrawable(getResources().getDrawable(R.drawable.niveau2etoiles0));
                    niveauDeux.setMaxHeight(188);
                    niveauDeux.setMaxWidth(120);
                }

                if (etatDeux.equals("onestar")) {
                    niveauDeux.setImageDrawable(getResources().getDrawable(R.drawable.niveau2etoiles1));
                    niveauDeux.setMaxHeight(188);
                    niveauDeux.setMaxWidth(120);
                }

                if (etatDeux.equals("twostars")) {
                    niveauDeux.setImageDrawable(getResources().getDrawable(R.drawable.niveau2etoiles2));
                    niveauDeux.setMaxHeight(188);
                    niveauDeux.setMaxWidth(120);
                    a++;
                }

                if (etatDeux.equals("threestars")) {
                    niveauDeux.setImageDrawable(getResources().getDrawable(R.drawable.niveau2etoiles3));
                    niveauDeux.setMaxHeight(188);
                    niveauDeux.setMaxWidth(120);
                    a++;
                }

                if (etatTrois.equals("blocked")) {
                    niveauTrois.setImageDrawable(getResources().getDrawable(R.drawable.niveau3blocked));
                    niveauTrois.setMaxHeight(188);
                    niveauTrois.setMaxWidth(120);
                }
                if (etatTrois.equals("unblocked")) {
                    niveauTrois.setImageDrawable(getResources().getDrawable(R.drawable.niveau3etoiles0));
                    niveauTrois.setMaxHeight(188);
                    niveauTrois.setMaxWidth(120);
                }
                if (etatTrois.equals("onestar")) {
                    niveauTrois.setImageDrawable(getResources().getDrawable(R.drawable.niveau3etoiles1));
                    niveauTrois.setMaxHeight(188);
                    niveauTrois.setMaxWidth(120);
                }
                if (etatTrois.equals("twostars")) {
                    niveauTrois.setImageDrawable(getResources().getDrawable(R.drawable.niveau3etoiles2));
                    niveauTrois.setMaxHeight(188);
                    niveauTrois.setMaxWidth(120);
                    a++;
                }
                if (etatTrois.equals("threestars")) {
                    niveauTrois.setImageDrawable(getResources().getDrawable(R.drawable.niveau3etoiles3));
                    niveauTrois.setMaxHeight(188);
                    niveauTrois.setMaxWidth(120);
                    a++;
                }

                if(a==3){testfinal=true;}

            } catch (JSONException e) {
                e.printStackTrace();

                Toast.makeText(getActivity(), getString(R.string.inscriptionechouee) + e.toString(), Toast.LENGTH_LONG).show();


            }
        }
    },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), getString(R.string.inscriptionechouee) + error.toString(), Toast.LENGTH_LONG).show();


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
    private View.OnClickListener levelListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String niveau="";
            String blocked="";

            switch (v.getId()){
                case R.id.niveau1:
                    niveau="NIVEAU 1";
                    blocked=etatUn;
                    id_niveau_choisi=id_niveau1;
                    num_niveau=1;
                    break;
                case R.id.niveau2:
                    niveau="NIVEAU 2";
                    blocked=etatDeux;
                    num_niveau=2;
                    id_niveau_choisi=id_niveau1+1;
                    break;
                case R.id.niveau3:
                    niveau="NIVEAU 3";
                    blocked=etatTrois;
                    num_niveau=3;
                    id_niveau_choisi=id_niveau1+2;

                    break;
            }
if(!blocked.equals("blocked")){
            Intent intent=new Intent(getActivity(), LevelActivity.class);
            intent.putExtra("niveau",niveau);
   intent.putExtra("id_user",id_user);
    intent.putExtra("num_niveau",num_niveau);
   intent.putExtra("id_niveau_choisi",id_niveau_choisi);
   intent.putExtra("testfinal",testfinal);


            startActivity(intent);}
else {
    Toast.makeText(getActivity(), "Le niveau est bloqué", Toast.LENGTH_LONG).show();
}

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        etats();
    }
}
