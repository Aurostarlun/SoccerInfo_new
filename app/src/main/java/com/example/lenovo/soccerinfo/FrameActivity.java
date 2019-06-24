package com.example.lenovo.soccerinfo;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FrameActivity extends FragmentActivity {

    private Fragment mFragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtNews, rbtGames, rbtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        mFragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.frame_news);
        mFragments[1] = fragmentManager.findFragmentById(R.id.frame_games);
        mFragments[2] = fragmentManager.findFragmentById(R.id.frame_info);
        fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();

        rbtNews = findViewById(R.id.radioNews);
        rbtGames = findViewById(R.id.radioGames);
        rbtInfo = findViewById(R.id.radioInfo);
        rbtNews.setBackgroundResource(R.drawable.shape2);

        radioGroup = findViewById(R.id.bottomGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("radioGroup", "checkId=" + checkedId);
                fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
                rbtNews.setBackgroundResource(R.drawable.shape1);
                rbtGames.setBackgroundResource(R.drawable.shape1);
                rbtInfo.setBackgroundResource(R.drawable.shape1);

                switch (checkedId) {
                    case R.id.radioNews:
                        fragmentTransaction.show(mFragments[0]).commit();
                        rbtNews.setBackgroundResource(R.drawable.shape2);
                        break;
                    case R.id.radioGames:
                        fragmentTransaction.show(mFragments[1]).commit();
                        rbtGames.setBackgroundResource(R.drawable.shape2);
                        break;
                    case R.id.radioInfo:
                        fragmentTransaction.show(mFragments[2]).commit();
                        rbtInfo.setBackgroundResource(R.drawable.shape2);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void openWeb(View btn) {
        Log.i("open", "openWeb: ");
        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.jisutiyu.com/"));
        startActivity(web);
    }

    public void openPerson(View btn) {
        Log.i("open", "openPerson: ");
        Intent person = new Intent(this, PersonActivity.class);
        startActivity(person);
    }

    public void openLeague(View btn) {
        Log.i("open", "openLeague: ");
        Intent league = new Intent(this, LeagueActivity.class);
        startActivity(league);
    }
}
