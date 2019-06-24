package com.example.lenovo.soccerinfo;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2019/6/13.
 */

public class InfoFragment extends Fragment implements Runnable {

    final String TAG = "InfoFragment";
    Handler handler;
    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_info, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<String> titleList = new ArrayList<String>();
        titleList.add("名词");
        titleList.add("球队");
        titleList.add("场次");
        titleList.add("胜场");
        titleList.add("平局");
        titleList.add("败场");
        titleList.add("积分");
        GridView titleView = getView().findViewById(R.id.titleGrid);
        adapter = new ArrayAdapter<String>(InfoFragment.this.getContext(), android.R.layout.simple_expandable_list_item_1, titleList);
        titleView.setAdapter(adapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 3) {
                    List<String> list = (List<String>) msg.obj;
                    Log.i(TAG, "handleMessage: getMessage msg = " + list);
                    GridView listView = getView().findViewById(R.id.infoGrid);
                    adapter = new ArrayAdapter<String>(InfoFragment.this.getContext(), android.R.layout.simple_expandable_list_item_1, list);
                    listView.setAdapter(adapter);
                }
            }
        };
    }

    @Override
    public void run() {
        Log.i(TAG, "run: run...");
        List<String> gridList = new ArrayList<String>();

//        String url[] = {};
//        url[0] ="https://soccer.hupu.com/table/England.html";
//        url[1] ="https://soccer.hupu.com/table/Spain.html";
//        url[2] ="https://soccer.hupu.com/table/Italy.html";
//        url[3] ="https://soccer.hupu.com/table/Germany.html";
//        url[4] ="https://soccer.hupu.com/table/France.html";

        int league = 1;
        String url = null;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("info", Activity.MODE_PRIVATE);
        league = sharedPreferences.getInt("league_key", 1);
        String league2 = sharedPreferences.getString("league_key2", "");
        switch (league) {
            case 1:
                url = "https://soccer.hupu.com/table/England.html";
                break;
            case 2:
                url = "https://soccer.hupu.com/table/Spain.html";
                break;
            case 3:
                url = "https://soccer.hupu.com/table/Italy.html";
                break;
            case 4:
                url = "https://soccer.hupu.com/table/Germany.html";
                break;
            case 5:
                url = "https://soccer.hupu.com/table/France.html";
                break;
        }
        Log.i(TAG, "run: league" + league);
        Log.i(TAG, "run: league2" + league2);
        Log.i(TAG, "run: url:" + url);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            Elements tables = doc.getElementsByTag("table");
            Element table = tables.get(0);
            Elements trs = table.getElementsByTag("tr");

            for (int j = 1; j <= 20; j++) {
                Element tr = trs.get(j);
                Elements tds = tr.getElementsByTag("td");

                String rank = null, team = null, games = null, win = null, equal = null, lose = null, points = null;

                for (int i = 0; i <= 14; i++) {
                    Element td = tds.get(i);
                    if (i == 0) {
                        rank = td.text();
                    } else if (i == 2) {
                        team = td.text();
                    } else if (i == 3) {
                        games = td.text();
                    } else if (i == 4) {
                        win = td.text();
                    } else if (i == 5) {
                        equal = td.text();
                    } else if (i == 6) {
                        lose = td.text();
                    } else if (i == 14) {
                        points = td.text();
                    }
                }
                String text = rank + team + games + win + equal + lose + points;
                Log.i(TAG, "run: text" + text);
                gridList.add(rank);
                gridList.add(team);
                gridList.add(games);
                gridList.add(win);
                gridList.add(equal);
                gridList.add(lose);
                gridList.add(points);
            }
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");
            gridList.add("");


        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(3);
        msg.obj = gridList;
        handler.sendMessage(msg);

    }
}
