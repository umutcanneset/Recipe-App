package com.mobile.recipearchive;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.CursorAdapter;

public class GetMealTask extends AsyncTask<Object, Object, Cursor>{
	
	private MealDatabase database;
	private CursorAdapter categoryAdapter;
	
	public GetMealTask(Context context, CursorAdapter categoryAdapter){
		database = new MealDatabase(context);
		this.categoryAdapter = categoryAdapter;
	}

    @Override
    protected Cursor doInBackground(Object... params){
 	   database.open();
 	   return database.getAllMeals();
    }

    @Override
    protected void onPostExecute(Cursor result){
 	   categoryAdapter.changeCursor(result);
 	   database.close();
    } 

}
