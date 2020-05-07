package com.example.easycode.Dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easycode.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeleteDialog extends DialogFragment {
    private static String URL_DELETE="http://192.168.1.6/users/delete_user.php";

    ImageView close;
    Button supprimer;
    String id_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        id_user=getArguments().getString("id_user");
        return inflater.inflate(R.layout.delete, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {




        close = view.findViewById(R.id.closeSupprimer);
        supprimer =view.findViewById(R.id.supprimer);


        supprimer.setOnClickListener(new View.OnClickListener() {
           @Override
         public void onClick(View v) {

               StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {

                       System.out.println(response);
                       try {
                           JSONObject jsonObject = new JSONObject(response);
                           Boolean erreur=jsonObject.getBoolean("error");
                           String message=jsonObject.getString("message");
                           Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

                           if(!erreur){
                               getDialog().dismiss();
                               getActivity().finish();
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
                                     }
        );
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });}




}
