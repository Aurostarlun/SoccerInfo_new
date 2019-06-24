package com.example.lenovo.soccerinfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PersonActivity extends AppCompatActivity {

    final String TAG = "PersonActivity";
    float getTimes = 0.0f;
    String name = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        SharedPreferences sharedPreferences = getSharedPreferences("info", Activity.MODE_PRIVATE);
        getTimes = sharedPreferences.getFloat("times_key", 0.0f);
        Log.i(TAG, "onCreate: getTimes" + getTimes);
        String getTimesto = "" + getTimes + "  " + "条";
        TextView timesShow = findViewById(R.id.personTimesShow);
        timesShow.setText(getTimesto);

        name = sharedPreferences.getString("name_key", null);
        Log.i(TAG, "onCreate: name:" + name);

        EditText nameText;
        nameText = findViewById(R.id.nameEdit);
        nameText.setText(name);

    }

    public void save(View btn) {
        SharedPreferences sharedPreferences = getSharedPreferences("info", Activity.MODE_PRIVATE);

        Log.i("cfg", "save");
        EditText nameText;
        nameText = findViewById(R.id.nameEdit);
        String setName = String.valueOf(nameText.getText());
        //获取新的值
        Log.i(TAG, "save:获取到新的值");

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name_key", setName);
        editor.apply();
        //返回到调用页面
        Intent intent = getIntent();
        setResult(2, intent);
        finish();
    }
}
