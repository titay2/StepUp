package com.example.bang.stepup3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by haiphan on 10/13/16.
 */
public class AddFoodActivity extends AppCompatActivity {
    private ListView mListView;
    ArrayList<FoodItem> foodList;
    FoodAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);

        mListView = (ListView) findViewById(R.id.listView);
        foodList = new ArrayList<FoodItem>();
        FoodItem hamburger = new FoodItem("hamburger2", "Hamburger", 30);
        FoodItem hotdog = new FoodItem("hotdog", "Hot Dog", 40);
        FoodItem burrito = new FoodItem("burrito", "Chicken Burritos", 20);
        FoodItem frenchfries = new FoodItem("frenchfries", "French fries", 10);
        FoodItem icecream = new FoodItem("ice_cream", "Ice Cream", 20);
        FoodItem crepe = new FoodItem("crepe", "Crepes", 30);

        foodList.add(hamburger);
        foodList.add(hotdog);
        foodList.add(burrito);
        foodList.add(frenchfries);
        foodList.add(icecream);
        foodList.add(crepe);

        adapter = new FoodAdapter(this, R.layout.food_item, foodList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            User.getInstance().getFoodList().add(foodList.get(position));
            onBackPressed();
        }
    };
}
