package com.ridgelineapps.resdicegame;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HighScores extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores);
        
        ((Button)findViewById(R.id.high_scores_ok)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        
        ((TextView)findViewById(R.id.textView1_1)).setText(formatScore(prefs.getString("hs_score_1", "0")));
        ((TextView)findViewById(R.id.textView1_2)).setText(formatDate(prefs.getString("hs_date_1", "0")));

        ((TextView)findViewById(R.id.textView2_1)).setText(formatScore(prefs.getString("hs_score_2", "0")));
        ((TextView)findViewById(R.id.textView2_2)).setText(formatDate(prefs.getString("hs_date_2", "0")));

        ((TextView)findViewById(R.id.textView3_1)).setText(formatScore(prefs.getString("hs_score_3", "0")));
        ((TextView)findViewById(R.id.textView3_2)).setText(formatDate(prefs.getString("hs_date_3", "0")));

        ((TextView)findViewById(R.id.textView4_1)).setText(formatScore(prefs.getString("hs_score_4", "0")));
        ((TextView)findViewById(R.id.textView4_2)).setText(formatDate(prefs.getString("hs_date_4", "0")));

        ((TextView)findViewById(R.id.textView5_1)).setText(formatScore(prefs.getString("hs_score_5", "0")));
        ((TextView)findViewById(R.id.textView5_2)).setText(formatDate(prefs.getString("hs_date_5", "0")));
    }
    
    public static String formatScore(String scoreString) {
        if(scoreString == null || scoreString.equals("") || scoreString.equals("0")) {
            return "-";
        }
        
        return scoreString;
    }
    
    public static String formatDate(String dateLong) {
        if(dateLong == null || dateLong.equals("") || dateLong.equals("0")) {
            return "-";
        }
        
        long date = 0;
        try {
            date = Long.parseLong(dateLong);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        if(date == 0) {
            return "-";
        }
        
        Date d = new Date(date);
        return d.toString();
    }
    
    public static void score(Activity activity, int score) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
     
        ArrayList<ScoreAndDate> scores = new ArrayList<ScoreAndDate>();

        boolean added = false;
        boolean newHigh = false;
        boolean newTop5 = false;
        
        for(int i=1; i <= 5; i++) {
            int hs = 0;
            try {
                hs = Integer.parseInt(prefs.getString("hs_score_" + i, "0"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            long date = 0;
            try {
                date = Long.parseLong(prefs.getString("hs_date_" + i, "0"));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            
            if(score >= hs) {
                if(hs > 0) {
                    newTop5 = true;
                    if(scores.size() == 0) {
                        newHigh = true;
                    }
                }
                added = true;
                ScoreAndDate sad = new ScoreAndDate();
                sad.score = score;
                sad.date = System.currentTimeMillis();
                scores.add(sad);
            }
            
            ScoreAndDate sad = new ScoreAndDate();
            sad.score = hs;
            sad.date = date;
            scores.add(sad);
        }
        
        if(added) {
            Editor editor = prefs.edit();
            for(int i=1; i <= 5; i++) {
                ScoreAndDate sad = scores.get(i);
                editor.putString("hs_score_" + i, "" + sad.score);
                editor.putString("hs_date_" + i, "" + sad.date);
            }
            editor.commit();
        }
        

        if(newHigh) {
            Toast.makeText(activity, "New High Score !!!", Toast.LENGTH_SHORT).show();
        }
        else if(newTop5) {
            Toast.makeText(activity, "New Top 5 Score" + score, Toast.LENGTH_SHORT).show();            
        }
    }
    
    public static class ScoreAndDate
    {
        public int score;
        public long date;
    }
}
