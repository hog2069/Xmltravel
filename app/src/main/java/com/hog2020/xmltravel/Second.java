package com.hog2020.xmltravel;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Second extends AppCompatActivity {
    ArrayList<String> travel=new ArrayList<String>();
    ArrayAdapter adapter;

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        listView= findViewById(R.id.listview);
        adapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,travel);
        listView.setAdapter(adapter);

        Thread thread =new Thread(){
            @Override
            public void run() {

                String address="https://openapi.gg.go.kr/TourismPension?KEY=ced2ae7e743e49458267b67132bc930c&Type=xml&pIndex=1&pSize=100&SIGUN_CD=41820";

                try {
                    URL url =new URL(address);
                    InputStream is =url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
                    XmlPullParser xpp=factory.newPullParser();

                    xpp.setInput(isr);

                    int eventType =xpp.getEventType();

                    StringBuffer buffer = null;

                    while(eventType!=XmlPullParser.END_DOCUMENT){

                        switch (eventType){

                            case XmlPullParser.START_DOCUMENT:
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {}
                                });
                                break;
                            case XmlPullParser.START_TAG:
                                String tagname = xpp.getName();

                                if(tagname.equals("row")){
                                    buffer=new StringBuffer();

                                }else if(tagname.equals("SIGUN_NM")){
                                    buffer.append("시(군): ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                }else if(tagname.equals("BIZPLC_NM")){
                                    buffer.append("펜션 이름: ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                }else if(tagname.equals("BSN_STATE_NM")){
                                    buffer.append("영업 여부: ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                }else if(tagname.equals("REFINE_WGS84_LAT")) {
                                    buffer.append("펜션 위도: ");
                                    xpp.next();
                                    buffer.append(xpp.getText() + "\n");
                                }else if(tagname.equals("REFINE_WGS84_LOGT")){
                                    buffer.append("펜션 경도: ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                }else if(tagname.equals("LOCPLC_FACLT_TELNO")){
                                    buffer.append("펜션 전화번호: ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                }else if(tagname.equals("REFINE_ROADNM_ADDR")){
                                    buffer.append("펜션 도로명주소: ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                }else if(tagname.equals("REFINE_LOTNO_ADDR")){
                                    buffer.append("펜션 지번주소: ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                }else if(tagname.equals("ROOM_CNT")){
                                    buffer.append("펜션 객실수: ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                }else if(tagname.equals("FACLT_AR")){
                                    buffer.append("펜션 객실면적: ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                }
                                break;

                            case XmlPullParser.TEXT:
                                break;
                            case XmlPullParser.END_TAG:
                                String tagname2 =xpp.getName();
                                if(tagname2.equals("row")){

                                    travel.add(buffer.toString());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                                break;
                        }
                        eventType=xpp.next();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }
}
