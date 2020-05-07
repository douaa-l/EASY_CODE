package com.example.easycode.Dialogs;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.easycode.R;

import org.w3c.dom.Text;

public class NegativeDialog extends DialogFragment {
    Button negative;
    ImageView closeNegatif;
    Button continuer;
    int moyenne;
    TextView text;
    int total;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            moyenne=getArguments().getInt("moyenne");
            total=getArguments().getInt("nbQst");
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return inflater.inflate(R.layout.negative, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        text=(TextView)view.findViewById(R.id.phrasenegative);
        negative = (Button) view.findViewById(R.id.continuernegative);
        continuer=view.findViewById(R.id.continuernegative);
        Resources res = getResources();
        String chaine = res.getString(R.string.negativephrase,moyenne,total);
        text.setText(chaine);

        closeNegatif = (ImageView) view.findViewById(R.id.closeNegative);
        closeNegatif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                getActivity().finish();

            }
        });
        continuer.setOnClickListener(new  View.OnClickListener() {
     @Override
      public void onClick(View v) {
         getDialog().dismiss();
         getActivity().finish();
                                                 }
                                             });

    }
}
