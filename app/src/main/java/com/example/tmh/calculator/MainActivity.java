package com.example.tmh.calculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements CalFragment.IMyCallBack {
    private String mData;
    public final static String PREF_KEY = "data";
    private CalFragment mCalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalFragment = new CalFragment();
        setFragment(mCalFragment);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_cal, fragment);
        fragmentTransaction.commit();
    }

    private void savePre(String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    @Override
    public void setData(String data) {
        mData = data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                savePre(PREF_KEY, null);
                mCalFragment = new CalFragment();
                setFragment(mCalFragment);
                break;
            case R.id.menu_save:
                savePre(PREF_KEY, mData);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
