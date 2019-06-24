package com.example.lenovo.soccerinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    String TAG = "NewsActivity";
//    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        String title = getIntent().getStringExtra("title");
        final TextView NewsTitle = findViewById(R.id.newsTitle);
        NewsTitle.setText(title);
        String text = null;
        text = getIntent().getStringExtra("text");
        Log.i(TAG, "onCreate: text:" + text);
        final TextView te = findViewById(R.id.text);
        te.setText(text);
        te.setMovementMethod(ScrollingMovementMethod.getInstance());

//        Document doc = null;
//        try{
//            doc = Jsoup.connect(url2).get();
//            Log.i(TAG, "onCreate: doc"+doc);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        handler = new Handler(){
//          @Override
//            public void handleMessage(Message msg){
//              if (msg.what==2){
//                  String text2 = (String)msg.obj;
//                  te.setText(text2);
//              }
//              super.handleMessage(msg);
//          }
//        };
    }

//    @Override
//    public void run() {
//        Log.i(TAG, "run: start");
////        Document doc = null;
////        try{
////            doc = Jsoup.connect(url).get();
////            Elements divs = doc.select("quote-content");
////            Log.i(TAG, "onCreate: div:"+divs);
////
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        String text = "Hello";
//
//        Message msg = handler.obtainMessage(1);
//        //msg.what = 1;
//        msg.obj = text;
//        handler.sendMessage(msg);
//
//    }
}
