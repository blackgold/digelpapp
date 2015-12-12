package com.digelp.digelp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;


public class MainActivity extends ActionBarActivity {

    private ImageButton mButtonQ1YO;
    private ImageButton mButtonQ1NO;
    private ImageButton mButtonQ2YO;
    private ImageButton mButtonQ2NO;
    private ImageButton mButtonQ3YO;
    private ImageButton mButtonQ3NO;
    private Button mSubmit;
    private String Q1="1";
    private String Q2="1";
    private String Q3="1";
    public static Context mContext; // commit suicide please

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        mButtonQ1YO = (ImageButton)findViewById(R.id.buttonFoodUp);
        mButtonQ1NO = (ImageButton)findViewById(R.id.buttonFoodDown);
        mButtonQ2YO = (ImageButton)findViewById(R.id.buttonServiceUp);
        mButtonQ2NO = (ImageButton)findViewById(R.id.buttonServiceDown);
        mButtonQ3YO = (ImageButton)findViewById(R.id.buttonAmbianceUp);
        mButtonQ3NO = (ImageButton)findViewById(R.id.buttonAmbianceDown);
        mSubmit = (Button)findViewById(R.id.buttonSubmit);

        mButtonQ1YO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonQ1YO.setFocusable(true);
                mButtonQ1NO.setElevation(0);
                Q1="1";
                Log.e("MAIN", "click ");
            }
        });

        mButtonQ1NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonQ1NO.setElevation(1);
                mButtonQ1YO.setElevation(0);
                Q1="0";
                Log.e("MAIN", "click ");
            }
        });

        mButtonQ2YO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonQ2YO.setElevation(1);
                mButtonQ2NO.setElevation(0);
                Q2="1";
                Log.e("MAIN", "click ");
            }
        });

        mButtonQ2NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonQ2NO.setElevation(1);
                mButtonQ2YO.setElevation(0);
                Q2="0";
                Log.e("MAIN", "click ");
            }
        });

        mButtonQ3YO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonQ3YO.setElevation(1);
                mButtonQ3NO.setElevation(0);
                Q3="1";
                Log.e("MAIN", "click ");
            }
        });

        mButtonQ3NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonQ3NO.setElevation(1);
                mButtonQ3YO.setElevation(0);
                Q3="0";
                Log.e("MAIN", "click ");
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MAIN", "submit ");
                SurveyResult sr = new SurveyResult();
                sr.add("Food",Q1 );
                sr.add("Service",Q2);
                sr.add("Ambiance",Q3);
                Q1=Q2=Q3="1";
                long unixTime = System.currentTimeMillis() / 1000L;
                sr.add("Timestamp",Long.toString(unixTime));
                String id = getString(R.string.business_id);
                sr.add("BusinessId",id);
                String url = getString(R.string.server_survey_url);
                PostToServer pts = new PostToServer(url,sr.getJsonSTring());
                pts.post();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
