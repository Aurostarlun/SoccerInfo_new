package com.example.lenovo.soccerinfo;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lenovo on 2019/6/13.
 */

public class NewsFragment extends Fragment implements Runnable, AdapterView.OnItemClickListener {

    final String TAG = "NewsFragment";
    Handler handler;
    ArrayAdapter adapter;
    List<String> items = new ArrayList<String>();
    List<String> titleList = new ArrayList<String>();
    float times = 0.0f;
    //获取SP里保存的数据


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frame_news, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView tv2 = getView().findViewById(R.id.newsTextView2);

//        for (int i=0;i<10;i++){
//            data.add("item"+i);
//        }

        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    List<String> list = (List<String>) msg.obj;
                    Log.i(TAG, "handleMessage: getMessage msg = " + list);
                    ListView listView = getView().findViewById(R.id.itemList);
                    adapter = new ArrayAdapter<String>(NewsFragment.this.getContext(), android.R.layout.simple_expandable_list_item_1, list);
                    listView.setAdapter(adapter);
                    listView.setEmptyView(tv2);
                    listView.setOnItemClickListener(NewsFragment.this);


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


        //获取网络数据

//        Document doc = null;
//        try {
//            doc = Jsoup.connect("https://soccer.hupu.com/").get();
//            //doc = Jsoup.parse(html);
//            Elements divs = doc.getElementsByTag("div");
//            Element div = divs.get(38);
//            Elements divs2 = div.getAllElements();
//
//            for (int i=8;i<104;i=i+5){
//                Element div2 = divs2.get(i);
//                Elements as = div2.getElementsByTag("a");
//                Element a = as.get(0);
//                String title = a.text();
//                String url = a.attr("href");
//                Log.i(TAG, "run: "+ title);
//                Log.i(TAG, "run: "+url);
//
//                titleList.add(title);
//                items.add(url);
//            }

//            int i = 1;
//            for(Element ul:divs2){
//                Log.i(TAG,"run:ul["+i+"]="+ul);
//                i++;
//            }

//         Log.i(TAG,"run:"+div);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Document document = null;
        try {
            document = Jsoup.connect("https://voice.hupu.com/soccer").get();
            Elements hs = document.getElementsByTag("h4");
            for (int i = 0; i < 25; i++) {
                Element h4 = hs.get(i);
                Elements as = h4.getElementsByTag("a");
                Element a = as.get(0);
                String title = a.text();
                String url = null;
                String text = null;
                url = a.attr("href");
                Log.i(TAG, "run: " + title);
                Log.i(TAG, "run: " + url);

                try {
                    Document con = null;
                    con = Jsoup.connect(url).get();
                    Elements ps = con.getElementsByTag("p");
                    text = ps.text();
//                    Log.i(TAG, "run: con"+text);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                titleList.add(title);
                items.add(text);
                Log.i(TAG, "run: as" + as);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


//        URL url = null;
//        try {
//            url = new URL("http://www.usd-cny.com/icbc.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//
//            String html = inputStream2String(in);
//            Log.i(TAG, "run: html=" + html);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Message msg = handler.obtainMessage(1);
        //msg.what = 1;
        msg.obj = titleList;
        handler.sendMessage(msg);
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick:parent=" + parent);
        Log.i(TAG, "onItemClick:view=" + view);
        Log.i(TAG, "onItemClick:position=" + position);
        Log.i(TAG, "onItemClick:id=" + id);


        //保存更新的浏览量
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("info", Activity.MODE_PRIVATE);
        times = sharedPreferences.getFloat("times_key", 0.0f);
        times++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("times_key", times);
        editor.apply();
        Log.i(TAG, "onItemClick: times:" + times);


        Log.i(TAG, "onItemClick: itemsto:" + items.get(position));
        Intent News = new Intent(this.getContext(), NewsActivity.class);
        News.putExtra("text", items.get(position));
        News.putExtra("title", titleList.get(position));
        startActivity(News);

    }

}