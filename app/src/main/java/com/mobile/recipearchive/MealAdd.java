package com.mobile.recipearchive;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MealAdd extends Fragment{
	private EditText name;
	private Context context;
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) { 
        View addMealView = inflater.inflate(R.layout.meal_add, container, false);
    	context = this.getActivity();
		name = (EditText) addMealView.findViewById(R.id.addMealName);
		Button add = (Button) addMealView.findViewById(R.id.addMeal);
		add.setOnClickListener(addClick);
        return addMealView;
    }
    
    AsyncTask<Object, Object, Object> AddMealTask = new AsyncTask<Object, Object, Object>(){
    	@Override
        protected Object doInBackground(Object... params){
    		MealDatabase database = new MealDatabase(context);
    		database.addMeal(name.getText().toString());
    		return null;
        } 

        @Override
        protected void onPostExecute(Object result){
        } 
    };
    
    public OnClickListener addClick = new OnClickListener() {
		public void onClick(View v){
			AddMealTask.execute((Object[]) null);
			Toast.makeText(getActivity(),"Meal Added!",Toast.LENGTH_SHORT).show();
		}
	};

}
