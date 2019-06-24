package com.example.lenovo.soccerinfo;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2019/6/13.
 */

public class GamesFragment extends Fragment implements Runnable {

    final String TAG = "NewsFragment";
    Handler handler;
    ListView list;
    private List<HashMap<String, String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_games, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView tv = getView().findViewById(R.id.gamesTextView);
//        list.setAdapter(listItemAdapter);

        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 2) {
                    listItems = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(getActivity(), listItems,//ListItems数据源
                            R.layout.list_item,//ListItem的XML布局实现
                            new String[]{"League", "Teams"},
                            new int[]{R.id.League, R.id.Teams}
                    );
                    Log.i(TAG, "handleMessage: " + listItems);
                    list = getView().findViewById(R.id.GamesList);
                    list.setAdapter(listItemAdapter);
                }
            }
        };

    }


    @Override
    public void run() {
        Log.i(TAG, "run: run()......");
//        for (int i = 1; i < 3; i++) {
//            Log.i(TAG, "run: i=" + i);
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        List<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.uhchina.com/zuqiusaicheng/6yue21ri.htm").get();
            Elements tbs = doc.getElementsByTag("table");
            Element tb = tbs.get(1);
            Elements trs = tb.getElementsByTag("tr");

            for (int i = 2; i <= 50; i++) {
                Element tr = trs.get(i);
                Elements tds = tr.getElementsByTag("td");
                String league = null, teamA = null, teamB = null;
                String time = null;
                for (int j = 0; j <= 4; j = j + 1) {
                    Element td = tds.get(j);
                    Elements as;
                    Element a;
                    if (j == 0) {
                        league = td.text();
//                        Log.i(TAG, "run: td"+td);
//                        as = td.getElementsByTag("a");
//                        a = as.get(0);
//                        url = a.attr("href");
                    } else if (j == 1) {
                        time = td.text();
                    } else if (j == 2) {
                        teamA = td.text();
                    } else if (j == 4) {
                        teamB = td.text();
                    }
                }
                HashMap<String, String> map = new HashMap<String, String>();
                String info = league + "    " + time;
                String teams = teamA + "VS" + teamB;
                map.put("League", info);
                map.put("Teams", teams);
                retList.add(map);
                Log.i(TAG, "run: league" + league + time);
                Log.i(TAG, "run: teamA" + teamA);
                Log.i(TAG, "run: teamB" + teamB);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        Message msg = handler.obtainMessage(2);
        //msg.what = 1;
        msg.obj = retList;
        handler.sendMessage(msg);
    }

}
