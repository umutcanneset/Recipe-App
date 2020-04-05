package com.mobile.recipearchive;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class RecipeChangeDelete extends Activity {
	private long rowID;
	private EditText name;
	private EditText ingredients;
	private EditText instructions;
	private Spinner category;
	private Context context;
	private CursorAdapter categoryAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	String[] fieldList;
    	int[] showList;
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		setContentView(R.layout.recipe_change_delete);
		Button saveChanges = (Button) findViewById(R.id.saveRecipeButton);
		Button delete = (Button) findViewById(R.id.deleteRecipeButton);
		Button goBack=(Button) findViewById(R.id.goBackButton);
		name = (EditText) findViewById(R.id.name);
		ingredients = (EditText) findViewById(R.id.ingredient);
		instructions = (EditText) findViewById(R.id.instructions);
		category = (Spinner) findViewById(R.id.categorySpinner);
        fieldList = new String[] {"name"};
        showList = new int[] {R.id.mealName};
        categoryAdapter = new SimpleCursorAdapter(this, R.layout.meal_cell, null, fieldList, showList, 0);
        category.setAdapter(categoryAdapter);
        new GetMealTask(context, categoryAdapter).execute((Object[]) null);
		saveChanges.setOnClickListener(saveChangesClick);
		delete.setOnClickListener(deleteClick);
		goBack.setOnClickListener(goBackClick);
		Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");
        new GetRecipeTask().execute(rowID);
	}
	
	private class GetRecipeTask extends AsyncTask<Long, Object, Cursor>{
		
		MealDatabase database = new MealDatabase(context);

		@Override
	     protected Cursor doInBackground(Long... params){
	        database.open();
	        return database.getRecipe(params[0]);
	     }
		
	      @Override
	      protected void onPostExecute(Cursor result){
	         super.onPostExecute(result);
	         result.moveToFirst(); 
	         int namePos = result.getColumnIndex("name");
	         int categoryPos = result.getColumnIndex("category");
	         int ingredientPos = result.getColumnIndex("ingredients");
	         int instructionPos = result.getColumnIndex("instructions");
	         name.setText(result.getString(namePos));

	         ingredients.setText("" + result.getString(ingredientPos));
	         instructions.setText("" + result.getString(instructionPos));


			  for (int i = 0; i < categoryAdapter.getCount(); i++){
				  Cursor cursor = (Cursor) categoryAdapter.getItem(i);
				  String categoryName = cursor.getString(cursor.getColumnIndex("name"));
				  String resultCategoryName = result.getString(categoryPos);
				  if (categoryName.equals(resultCategoryName)){
					  category.setSelection(i);
					  break;
				  }
			  }
	         result.close(); 
	         database.close();
	      }
	}
	
    AsyncTask<Object, Object, Object> changeRecipeTask = new AsyncTask<Object, Object, Object>(){
    	@Override
        protected Object doInBackground(Object... params){
    		MealDatabase database = new MealDatabase(context);
	        Cursor categoryCursor = (Cursor) categoryAdapter.getItem(category.getSelectedItemPosition());
	        String categoryName = categoryCursor.getString(categoryCursor.getColumnIndex("name"));

    		database.updateRecipe(rowID, name.getText().toString(), categoryName,ingredients.getText().toString(),
    				instructions.getText().toString());
    		return null;
        } 

        @Override
        protected void onPostExecute(Object result){

			Toast.makeText(getApplicationContext(),"Recipe Changed",Toast.LENGTH_SHORT).show();
			finish();
        } 
    };
    
    AsyncTask<Long, Object, Object> deleteRecipeTask = new AsyncTask<Long, Object, Object>(){
        @Override
        protected Object doInBackground(Long... params){
			MealDatabase database = new MealDatabase(context);
			database.deleteRecipe(params[0]);
			return null;
        }

        @Override
        protected void onPostExecute(Object result){

			Toast.makeText(getApplicationContext(),"Recipe Deleted!",Toast.LENGTH_SHORT).show();
			finish();
        } 
   }; 
	
	public OnClickListener saveChangesClick = new OnClickListener() {
		public void onClick(View v){
			changeRecipeTask.execute((Object[]) null);


		}
	};
	
	public OnClickListener deleteClick = new OnClickListener() {
		public void onClick(View v){
			deleteRecipeTask.execute(new Long[] { rowID });


		}
	};

	public OnClickListener goBackClick = new OnClickListener() {
		public void onClick(View v){
			finish();
		}
	};
	
}

