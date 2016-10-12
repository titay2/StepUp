package com.example.bang.stepup3;

import java.util.ArrayList;

/**
 * Created by haiphan on 10/13/16.
 */
public class User {
    private ArrayList<FoodItem> foodList;
    private static final User INSTANCE = new User();

    private User() {
        foodList = new ArrayList<FoodItem>();
    }

    public static User getInstance() {
        return INSTANCE;
    }

    public ArrayList<FoodItem> getFoodList() {
        return foodList;
    }
}
