package com.example.easycode.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.easycode.Adapters.PageAdapter;
import com.example.easycode.R;

import java.util.ArrayList;
import java.util.List;

public class ProfilActivity extends Fragment {

private PagerAdapter pagerAdapter;
   private ViewPager viewpager;
   private List<Fragment>list=new ArrayList<>();
private String id_user;
Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // id_user=getArguments().getString("id_user");
        return inflater.inflate(R.layout.fragment_dashboard,container,false);
    }
    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInsranceState ) {

this.ConfigureViewPager();
    }
    private void ConfigureViewPager(){
       // bundle.putSerializable("id_user","1");

        viewpager = (ViewPager)getView().findViewById(R.id.view_pager);
        InfoFragment infoFragment=new InfoFragment();
       // infoFragment.setArguments(bundle);
        list.add(infoFragment);
        list.add(new StatistiqueFragment());
        pagerAdapter=new PageAdapter(getChildFragmentManager(),list);
        viewpager.setAdapter(pagerAdapter);

    }

    }
