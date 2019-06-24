package com.example.lenovo.soccerinfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class LeagueActivity extends AppCompatActivity {

    String TAG = "LeagueActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);
    }

    public void onLeague(View btn) {

        int key = 1;
        SharedPreferences sharedPreferences = getSharedPreferences("info", Activity.MODE_PRIVATE);
        key = sharedPreferences.getInt("league_key", 1);
        //获取用户输入内容

        String ID = String.valueOf(btn.getId());
        Log.i(TAG, "onLeague: btn:" + ID);
        Log.i(TAG, "onLeague: " + R.id.Spain);
        if (btn.getId() == R.id.England) {
            key = 1;
        } else if (btn.getId() == R.id.Spain) {
            key = 2;
        } else if (btn.getId() == R.id.Italy) {
            key = 3;
        } else if (btn.getId() == R.id.Gemany) {
            key = 4;
        } else if (btn.getId() == R.id.France) {
            key = 5;
        }
        key = 2;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("league_key", key);
        editor.putString("league_key2", "" + key);
        editor.apply();

        Intent intent = getIntent();
//        setResult(3,intent);
//        intent.putExtra("leagueKey",""+key);
//        finish();
        Intent info = new Intent(this, InfoFragment.class);
        startActivity(info);
    }

}
