package com.example.easycode.stat;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.example.easycode.Outils.Plaque;
import com.example.easycode.Outils.uneLecon;
import com.example.easycode.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class graphOne extends Fragment {

    String id_user;
    BarChart barChart;
    ArrayList<BarEntry> dataValues=new ArrayList<>();
    ArrayList<String> xLabels = new ArrayList<>();
    ArrayList<uneLecon> rows =new ArrayList<uneLecon>();
    private static String URL_STAT= Outils.getPath()+"users/stat.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        id_user=getParentFragment().getParentFragment().getArguments().getString("id_user");
        return inflater.inflate(R.layout.graphone, container, false);
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInsranceState ) {


        barChart=view.findViewById(R.id.mp_chart1);

        getStat();

    }


    public void getStat(){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL_STAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("-_-_-_-_-_-_"+response);
              try {
                  JSONArray  array = new JSONArray(response);
                  rows.clear();
                  dataValues.clear();
                  xLabels.clear();
                    for(int i=0;i<(array.length());i++){
                        rows.add(new uneLecon(array.getJSONObject(i)));

                    }
                    for(int i=0;i<(rows.size());i++){
                       String cours=rows.get(i).getTitre();
                       int eval=rows.get(i).getEvaluation();
                       dataValues.add(new BarEntry(i,eval));
                       xLabels.add(cours);
                    }
                    BarDataSet barDataSet=new BarDataSet(dataValues,"graph");
                    BarData barData=new BarData();
                    barData.addDataSet(barDataSet);
                    barChart.setData(barData);
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    barChart.animateY(2500);

                    XAxis xAxis=barChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));
                    xAxis.setLabelCount(xLabels.size());
                  xAxis.setLabelRotationAngle(90);
                  xAxis.setDrawAxisLine(false);
                  xAxis.setDrawGridLines(false);
                  xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                  xAxis.setGranularity(1);
                  YAxis yAxis=barChart.getAxisLeft();
                  yAxis.setAxisMaximum(6);
                  Description description=new Description();
                  description.setText("evaluation des cours du niveau 1");
                  description.setTextColor(getResources().getColor(R.color.vert));
                  description.setTextSize(16);
                  barChart.setDescription(description);



                  barChart.invalidate();


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);
                params.put("num_niv", String.valueOf(1));



                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }



    }

