package com.example.todomobile;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.apache.commons.beanutils.converters.SqlDateConverter;

public class Database extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ToDoList";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "todo";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_END_DATE = "end_date";
    private static final String COLUMN_CONTEXT = "context";
    private static final String COLUMN_PRIORITY = "priority" ;
    private static final String COLUMN_URL = "url";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_START_DATE + " TEXT, " +
                        COLUMN_END_DATE + " TEXT, " +
                        COLUMN_CONTEXT + " TEXT, " +
                        COLUMN_PRIORITY + " TEXT, " +
                        COLUMN_URL + " TEXT) ;";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Méthode d'ajour d'une tâche
    void addTask(String title, String desc, String startDate, String endDate, String con, String priority, String url){
        SQLiteDatabase db =  this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //Ajout des différentes données
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESCRIPTION, desc);
        cv.put(COLUMN_START_DATE, startDate);
        cv.put(COLUMN_END_DATE, endDate);
        cv.put(COLUMN_CONTEXT, con);
        cv.put(COLUMN_PRIORITY, priority);
        cv.put(COLUMN_URL, url);

        //Insertion dans la table
        long  result = db.insert(TABLE_NAME, null, cv);

        //Vérification de l'état de la requête
        if(result == -1){
            Toast.makeText(context, "Erreur lors de l'insertion !", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Tâche ajoutée avec succès !", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
}