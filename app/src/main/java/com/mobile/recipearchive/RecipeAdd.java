package com.mobile.recipearchive;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class RecipeAdd extends Fragment {
	private EditText name;
	private EditText ingredients;
	private EditText instructions;
	private Spinner category;
	private Context context;
	private CursorAdapter categoryAdapter;
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	String[] fieldList;
    	int[] showList;
        View RecipeAddView = inflater.inflate(R.layout.recipe_add, container, false);
    	context = this.getActivity();
		name = (EditText) RecipeAddView.findViewById(R.id.addName);
		ingredients = (EditText) RecipeAddView.findViewById(R.id.addIngredient);
		instructions = (EditText) RecipeAddView.findViewById(R.id.addInstructions);
		category = (Spinner) RecipeAddView.findViewById(R.id.categorySpinner);
		final Button add = (Button) RecipeAddView.findViewById(R.id.addRecipeButton);
		add.setOnClickListener(addClick);
        fieldList = new String[] {"name"};
        showList = new int[] {R.id.mealName};
        categoryAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.meal_cell, null, fieldList, showList, 0);
        category.setAdapter(categoryAdapter);
        new GetMealTask(context, categoryAdapter).execute((Object[]) null);

		if(name.getText().toString().equalsIgnoreCase("") ||ingredients.getText().toString().equalsIgnoreCase("")||instructions.getText().toString().equalsIgnoreCase("")){

			add.setEnabled(false);
		}

		instructions.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			if(!charSequence.toString().equals("")){
				add.setEnabled(true);
			}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});





        return RecipeAddView;
    }
    
    AsyncTask<Object, Object, Object> AddRecipeTask = new AsyncTask<Object, Object, Object>(){
    	@Override
        protected Object doInBackground(Object... params){
    		MealDatabase database = new MealDatabase(context);
	        Cursor categoryCursor = (Cursor) categoryAdapter.getItem(category.getSelectedItemPosition());
	        String categoryName = categoryCursor.getString(categoryCursor.getColumnIndex("name"));

				database.addRecipe(name.getText().toString(), categoryName,ingredients.getText().toString(),instructions.getText().toString());


			return null;
        } 

        @Override
        protected void onPostExecute(Object result){
			Toast.makeText(getActivity(),"Recipe Added!",Toast.LENGTH_SHORT).show();
        } 
    };
    
    public OnClickListener addClick = new OnClickListener() {
		public void onClick(View v){
			AddRecipeTask.execute((Object[]) null);



		}
	};


}
