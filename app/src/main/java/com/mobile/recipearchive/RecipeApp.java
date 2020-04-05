package com.mobile.recipearchive;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.os.Bundle;
import android.app.Activity;

public class RecipeApp extends Activity implements
		ActionBar.TabListener {
	private Fragment recipeList;
	private Fragment addRecipe;
	private Fragment updateRecipe;
	private Fragment addMealType;
	private Fragment mealList;


		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab().setText(R.string.recipeList)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.addRecipe)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.updateRecipe)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.addMealType)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(R.string.mealList)
				.setTabListener(this));


	}
	
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		switch (tab.getPosition()){
			case 0:
				recipeList = Fragment.instantiate(this, RecipeList.class.getName());
				fragmentTransaction.add(android.R.id.content, recipeList, "RecipeList");
				break;
			case 1:
				addRecipe = Fragment.instantiate(this, RecipeAdd.class.getName());
				fragmentTransaction.add(android.R.id.content, addRecipe, "AddRecipe");
				break;
			case 2:
				updateRecipe = Fragment.instantiate(this, RecipeList.class.getName());
				fragmentTransaction.add(android.R.id.content, updateRecipe, "UpdateRecipe");
				break;
			case 3:
				addMealType=Fragment.instantiate(this,MealAdd.class.getName());
				fragmentTransaction.add(android.R.id.content,addMealType,"AddMealType");
				break;

			case 4:
				mealList=Fragment.instantiate(this,MealList.class.getName());
				fragmentTransaction.add(android.R.id.content,mealList,"MealList");
				break;

		}
	}

	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		switch (tab.getPosition()){
			case 0:
				fragmentTransaction.detach(recipeList);
				break;
			case 1:
				fragmentTransaction.detach(addRecipe);
				break;
			case 2:
				fragmentTransaction.detach(updateRecipe);
				break;
			case 3:
				fragmentTransaction.detach(addMealType);
				break;
			case 4:
				fragmentTransaction.detach(mealList);
				break;

		}
	}

	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

}
