package com.example.bang.stepup3;

import java.util.ArrayList;

/**
 * Created by haiphan on 10/13/16.
 */
public class User {
    private ArrayList<FoodItem> foodList;
    private static final User INSTANCE = new User();

    private User() {
        FoodItem hamburger = new FoodItem("hamburger2", "Hamburger", 1000.0, 1000, 1000.0, 1000);
        FoodItem hotdog = new FoodItem("hotdog", "Hot Dog", 1200.0, 1200, 1200.0, 1200);
        FoodItem burrito = new FoodItem("burrito", "Chicken Burritos", 100.0, 100, 100.0, 1000);
        FoodItem frenchfries = new FoodItem("frenchfries", "French fries", 1200.0, 1200, 1200.0, 1200);
        foodList.add(hamburger);
        foodList.add(hotdog);
        foodList.add(burrito);
        foodList.add(frenchfries);
    }

    public static User getInstance() {
        return INSTANCE;
    }

    public ArrayList<FoodItem> getFoodList() {
        return foodList;
    }
}
