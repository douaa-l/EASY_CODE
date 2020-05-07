package com.example.easycode.Dialogs;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class MdpDialog extends DialogFragment {
    EditText oldMDP;
    EditText newMDP;
    EditText confirmMDP;
    ImageView close;
    Button valider;
    String id_user;

    private static String URL_MDP="http://192.168.1.10/users/modif_password.php";


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        oldMDP = view.findViewById(R.id.oldMDP);
        newMDP = view.findViewById(R.id.newMDP);
        confirmMDP = view.findViewById(R.id.confirmnewMDP);
        close = view.findViewById(R.id.closeMDP);
        valider = (Button)view.findViewById(R.id.validerMDP);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_MDP, new Response.Listener<String>() {
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
                        params.put("oldpassword", String.valueOf(oldMDP.getText()));
                        params.put("newpassword", String.valueOf(newMDP.getText()));
                        params.put("newpasswordconf", String.valueOf(confirmMDP.getText()));


                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);






            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        id_user=getArguments().getString("id_user");
getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return inflater.inflate(R.layout.motdepassemodif, container, false);
    }
}
