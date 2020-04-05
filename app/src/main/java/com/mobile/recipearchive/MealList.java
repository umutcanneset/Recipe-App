package com.mobile.recipearchive;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

public class MealList extends ListFragment {
	private CursorAdapter mealAdapter;
	private Context context;
	
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View mealListView = inflater.inflate(R.layout.meal_list, container, false);
    	context = this.getActivity();
        String[] fieldList = new String[] {"name"};
        int[] showList = new int[] {R.id.mealName};
        mealAdapter = new SimpleCursorAdapter(this.getActivity(), R.layout.meal_cell, null, fieldList, showList, 0);
        setListAdapter(mealAdapter);
        new GetMealTask(context, mealAdapter).execute((Object[]) null);
        return mealListView;
    }
    
}
