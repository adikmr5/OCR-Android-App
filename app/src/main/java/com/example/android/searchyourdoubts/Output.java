package com.example.android.searchyourdoubts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

public class Output extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    static String text;

    public static String getData(){
        return text;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        toolbar=findViewById(R.id.Toolbar);
        tabLayout=findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewPager);
        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        Intent intent = getIntent();
        text = intent.getExtras().getString("Detect");
//        Log.i("output:",""+text);
//        Toast.makeText(this,"hello "+text,Toast.LENGTH_SHORT).show();


    }
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Google(),"Google");
        viewPagerAdapter.addFragment(new Youtube(),"YouTube");
        viewPagerAdapter.addFragment(new Yahoo(),"Yahoo");
        viewPagerAdapter.addFragment(new Quora(),"Quora");
        viewPager.setAdapter(viewPagerAdapter);

    }
}