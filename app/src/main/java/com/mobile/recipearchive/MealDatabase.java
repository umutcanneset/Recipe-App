package com.mobile.recipearchive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MealDatabase {
	
   private SQLiteDatabase database;
   private DatabaseOpenHelper databaseHelper;

   public MealDatabase(Context context){
	   databaseHelper = new DatabaseOpenHelper(context, "Recipe", null, 1);
   } 

   public void open() throws SQLException{
	   database = databaseHelper.getWritableDatabase();
   }

   public void close(){
      if (database != null)
         database.close();
   }

   public void addRecipe(String name, String category, String ingredients, String instructions){
      ContentValues newRecipe = new ContentValues();
      newRecipe.put("name", name);
      newRecipe.put("category", category);
      newRecipe.put("ingredients", ingredients);
      newRecipe.put("instructions", instructions);
      open();
      database.insert("recipe", null, newRecipe);
      close();
   }

   public void addMeal(String name){
	   ContentValues newMeal = new ContentValues();
	   newMeal.put("name", name);
	   open();
	   database.insert("meal", null, newMeal);
	   close();
   }



   public void updateRecipe(long id, String name, String category, String ingredients, String instructions){
      ContentValues newRecipe = new ContentValues();
      newRecipe.put("name", name);
       newRecipe.put("category", category);
       newRecipe.put("ingredients", ingredients);
       newRecipe.put("instructions", instructions);
      open();
      database.update("recipe", newRecipe, "_id=" + id, null);
      close();
   }
   
   public void deleteRecipe(long id){
	      open();
	      database.delete("recipe", "_id=" + id, null);
	      close();
   }

   public Cursor getAllRecipes(){
      return database.query("recipe", new String[] {"_id", "name","category"}, null, null, null, null, "category");
   }

   public Cursor getAllMeals(){
	   return database.query("meal", new String[] {"_id", "name"}, null, null, null, null, "name");
   }

   
   public Cursor getRecipe(long id){
      return database.query("recipe", null, "_id=" + id, null, null, null, null);
   }

   public Cursor getMeal(long id){
	  return database.query("meal", null, "_id=" + id, null, null, null, null);
   }


   
   private class DatabaseOpenHelper extends SQLiteOpenHelper{

	  public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version){
		  super(context, name, factory, version);
      }

      public void onCreate(SQLiteDatabase db){
          String sqlFilm = "CREATE TABLE recipe" +
                  "(_id integer primary key autoincrement," +
                  "name TEXT, category TEXT, ingredients TEXT, instructions TEXT);";
          db.execSQL(sqlFilm);
          String sqlGenre = "CREATE TABLE meal" +
                  "(_id integer primary key autoincrement," +
                  "name TEXT);";
          db.execSQL(sqlGenre);

      }

      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
      }
   }
}
