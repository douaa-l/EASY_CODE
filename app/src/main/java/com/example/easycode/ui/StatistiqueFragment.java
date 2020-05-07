package com.example.easycode.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easycode.Adapters.PageAdapter;
import com.example.easycode.Adapters.statAdapter;
import com.example.easycode.R;
import com.example.easycode.stat.graphOne;
import com.example.easycode.stat.graphThree;
import com.example.easycode.stat.graphTwo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class StatistiqueFragment extends Fragment {
String id_user;
    private PagerAdapter pagerAdapter;
    private ViewPager viewpager;
    private List<Fragment> list=new ArrayList<>();

BarChart barChart;
ArrayList<BarEntry>dataValues=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id_user=getParentFragment().getArguments().getString("id_user");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistique, container, false);
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInsranceState ) {
        this.ConfigureViewPager();
    }
    private void ConfigureViewPager(){

        viewpager = (ViewPager)getView().findViewById(R.id.stat_pager);

        list.add(new graphOne());
        list.add(new graphTwo());
        list.add(new graphThree());
        statAdapter statAdapter = new statAdapter(getChildFragmentManager(), list);
        viewpager.setAdapter(statAdapter);

    }

    }
