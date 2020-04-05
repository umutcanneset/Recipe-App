package com.mobile.recipearchive;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class RecipeGet extends Activity {
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
        setContentView(R.layout.recipe_get);
        Button goBack=(Button) findViewById(R.id.goBackButton2);
        name = (EditText) findViewById(R.id.name2);
        ingredients = (EditText) findViewById(R.id.ingredient2);
        instructions = (EditText) findViewById(R.id.instructions2);
        category = (Spinner) findViewById(R.id.categorySpinner2);
        fieldList = new String[] {"name"};
        showList = new int[] {R.id.mealName};
        categoryAdapter = new SimpleCursorAdapter(this, R.layout.meal_cell, null, fieldList, showList, 0);
        category.setAdapter(categoryAdapter);
        new GetMealTask(context, categoryAdapter).execute((Object[]) null);

        name.setEnabled(false);
        ingredients.setEnabled(false);
        instructions.setEnabled(false);
        category.setEnabled(false);
        name.setTextColor(Color.BLUE);
        ingredients.setTextColor(Color.BLUE);
        instructions.setTextColor(Color.BLUE);

        goBack.setOnClickListener(goBackClick);
        Bundle extras = getIntent().getExtras();
        rowID = extras.getLong("row_id");
        new RecipeGet.GetRecipeTask().execute(rowID);
    }
    private class GetRecipeTask extends AsyncTask<Long, Object, Cursor> {

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
    public View.OnClickListener goBackClick = new View.OnClickListener() {
        public void onClick(View v){
            finish();
        }
    };
}
