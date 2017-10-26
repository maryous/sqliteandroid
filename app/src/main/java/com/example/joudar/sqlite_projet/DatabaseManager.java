package com.example.joudar.sqlite_projet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by joudar on 26/10/17.
 */
//on cree notre class qui extends de SQLiteo
public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Game.db";
    private static final int DATABASE_VERSION = 2;//normalement etait version 1

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creation de notre table
        String strSql = "create table T_score ("
                + "   idscore integer primary key autoincrement,"
                + " name text not null,"
                + "score integer not null,"
                + "when_ integer not null"
                + ")";
        db.execSQL(strSql);
        Log.i("DATABASE", "onCreate invoked");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strSql = "drop table T_score";
        db.execSQL(strSql);
        this.onCreate(db);
        Log.i("DATABASE", "onUpgrade invoked");


    }

    public void insertScore(String name, int score) {
        name = name.replace(" '", " ''");
        String strSql = " insert into T_score(name, score, when_) values (' "
                + name + "' , " + score + " ," + new Date().getTime() + ")";

        this.getWritableDatabase().execSQL(strSql);
        Log.i("DATABASE", strSql);
    }

    public List<ScoreData> readTop10() {
        List<ScoreData> scores = new ArrayList<>();

        // 1ere technique : sql

//        String strSql = "select * from T_score order by  score desc limit 10";
//        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        /////////////////////////////////////
        //2 eme technique "plus objet

        Cursor cursor = this.getReadableDatabase().query("T_score", new String[]{"idScore", "name","score",
                "when_"},null, null,null,null,"score desc","10");
        ////////////////////////////////////////


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ScoreData score = new ScoreData(cursor.getInt(0), cursor.getString(1),
                    cursor.getInt(2), new Date(cursor.getInt(3)));
            scores.add(score);
            cursor.moveToNext();
        }
        cursor.close();


        return scores;

    }
}
