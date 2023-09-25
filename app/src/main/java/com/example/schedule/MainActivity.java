package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public TextView DayOfWeek;
    private Button button_Open_FullSchedule, button_mon, button_tue, button_wed, button_thu, button_fri, button_sat;
    private TextView lesson1, room1, time1;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        getSupportActionBar().setTitle("Расписание");

        //Присваивание объектов к переменным
        DayOfWeek = findViewById(R.id.DayOfWeek);
        button_mon = findViewById(R.id.Mon);

        lesson1 = findViewById(R.id.lesson1);
        room1 = findViewById(R.id.room1);
        time1 = findViewById(R.id.time1);

        button_tue = findViewById(R.id.Tue);
        button_wed = findViewById(R.id.Wed);
        button_thu = findViewById(R.id.Thu);
        button_fri = findViewById(R.id.Fri);
        button_sat = findViewById(R.id.Sat);
        button_Open_FullSchedule = findViewById(R.id.button_Open_FullSchedule);

        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        }   catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        }   catch (SQLException mSQLException) {
            throw mSQLException;
        }

        // Вычисление кратности недели
        int DayWeek;
        Date start = null;
        try {
            start = new SimpleDateFormat("dd/MM/yyyy").parse("01/09/2022"); //получим дату 1-го сентября 2019
        } catch (ParseException exc) {
            exc.printStackTrace();
        }
        long delay = System.currentTimeMillis() - start.getTime(); //получим разницу (в мс) между сегодня и 1-ым сентября
        long week = 1000 * 60 * 60 * 24 * 7; //кол-во миллисекунд в одной неделе
        delay %= week * 2; //найдем остаток от деления разницы на две недели
        if (delay <= week) DayWeek = 1; //если разница меньше либо равна одной неделе, то это первая неделя
        else DayWeek = 2; //иначе вторая
        if(DayWeek == 1) DayOfWeek.setText("Первая неделя");
        else DayOfWeek.setText("Вторая неделя");


        //Действие кнопоп дней недели
        button_mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lesson = "", room = "", time = "";
                Cursor cursor = mDb.rawQuery("SELECT * FROM raspisanie", null);
                cursor.moveToFirst();
                /*
                while (!cursor.isAfterLast()) {
                    product += cursor.getString(3) + " | ";
                    cursor.moveToNext();
                }
                */
                lesson = cursor.getString(3);
                room = cursor.getString(4);
                time = cursor.getString(5);
                cursor.close();
                lesson1.setText(lesson);
                room1.setText(room);
                time1.setText(time);
            }
        });

        button_tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lesson = "", room = "", time = "";
                Cursor cursor = mDb.rawQuery("SELECT * FROM raspisanie", null);
                cursor.moveToFirst();
                cursor.moveToPosition(1);

                lesson = cursor.getString(3);
                room = cursor.getString(4);
                time = cursor.getString(5);
                cursor.close();
                lesson1.setText(lesson);
                room1.setText(room);
                time1.setText(time);
            }
        });


        //Новый класс для действия кнопки
        button_Open_FullSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(button_Open_FullSchedule);
            }
        });
    }

    //Метод для смены Activity
    public void startNewActivity(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

}
