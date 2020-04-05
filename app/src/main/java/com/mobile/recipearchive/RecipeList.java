package com.mobile.recipearchive;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
 
public class RecipeList extends ListFragment {
	protected static final String ROW_ID = "row_id";
	private CursorAdapter recipeAdapter;
	private Context context;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View recipeListView = inflater.inflate(R.layout.recipe_list, container, false);
    	context = this.getActivity();
        String[] fieldList = new String[] {"name","category"};
        int[] showList = new int[] {R.id.recipeName,R.id.category};
        recipeAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.recipe_cell, null, fieldList, showList, 0);
        setListAdapter(recipeAdapter);
        new GetAllRecipesTask().execute((Object[]) null);
        return recipeListView;
    }
    
    private class GetAllRecipesTask extends AsyncTask<Object, Object, Cursor>{
    	MealDatabase database = new MealDatabase(context);

        @Override
        protected Cursor doInBackground(Object... params){
     	   database.open();
     	   return database.getAllRecipes();
        }

        @Override
        protected void onPostExecute(Cursor result){
     	   recipeAdapter.changeCursor(result);
     	   database.close();
        } 
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	Intent recipeGet = new Intent(l.getContext(), RecipeChangeDelete.class);
    	recipeGet.putExtra(ROW_ID, id);
    	startActivity(recipeGet);
    }
 
}