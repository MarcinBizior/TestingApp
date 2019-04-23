package com.example.kanum.testingapp;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import java.util.Stack;

public class MainActivity extends FragmentActivity {

    public static final String JSON_FRAGMENT = "jsonFragment";

    private JsonFragment mJsonFragment;

    private Stack<String> mFragmentStack = new Stack<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mJsonFragment = new JsonFragment();
                addNewFragment(mJsonFragment, JSON_FRAGMENT, false);
            } else {
                mJsonFragment = new JsonFragment();
                addNewFragment(mJsonFragment, JSON_FRAGMENT, false);
            }
        } else {
            mJsonFragment = (JsonFragment) getSupportFragmentManager().findFragmentByTag(JSON_FRAGMENT);
        }

    }


    public void addNewFragment(Fragment fragment, String name, boolean addTobackStack){
        if(addTobackStack) mFragmentStack.push(name);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.conteiner0, fragment, name);
        if(addTobackStack) fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commit();
    }

}
