package com.example.joudar.sqlite_projet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView scoreView;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreView=(TextView)findViewById(R.id.scoreView);
        databaseManager= new DatabaseManager( this );
        databaseManager.insertScore( "hasnae" , 1000);
        databaseManager.insertScore("yousra",1200);
        databaseManager.insertScore("marwa",1250);
        databaseManager.insertScore("ahmed113",1750);
        List<ScoreData > scores = databaseManager.readTop10();
        for( ScoreData score : scores) {

            scoreView.append(score.toString() + "\n\n");


        }



        databaseManager.close();


    }
}
