package com.example.easycode.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easycode.HTMLActivity;
import com.example.easycode.R;

public class facultatifActivity extends Fragment {
    Button accident,entretien,preparation,secours;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_facultatif,container,false);
    }
    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInsranceState ) {
        accident=getView().findViewById(R.id.accident);
        secours=getView().findViewById(R.id.secour);
        entretien=getView().findViewById(R.id.entretien);
        preparation=getView().findViewById(R.id.prepar);

        accident.setOnClickListener(goCours);
        secours.setOnClickListener(goCours);
        entretien.setOnClickListener(goCours);
        preparation.setOnClickListener(goCours);
    }
    public View.OnClickListener goCours=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String titre_cours="";
            switch (v.getId()){
                case R.id.accident:
                    titre_cours="accident";
                    break;
                case R.id.secour:
                    titre_cours="secour";
                    break;
                case R.id.entretien:
                    titre_cours="entretien";
                    break;
                case R.id.prepar:
                    titre_cours="prepar";
                    break;

            }
            Intent intent=new Intent(getActivity(), HTMLActivity.class);
            intent.putExtra("titre_cours",titre_cours);
            intent.putExtra("facultatif","");
            startActivity(intent);

        }
    };
    }
