package com.example.bang.stepup3;

import android.content.Context;
/**
 * Created by haiphan on 10/12/16.
 */
public class FoodItem {
    private String foodImage;
    private String foodName;
    private Double totalCalories;
    private Integer totalSteps;
    private Double caloriesLeft;
    private Integer stepsLeft;

    public FoodItem(String foodImage, String foodName, Double totalCalories, Integer totalSteps, Double caloriesLeft, Integer stepsLeft) {
        this.foodImage = foodImage;
        this.foodName = foodName;
        this.totalCalories = totalCalories;
        this.totalSteps = totalSteps;
        this.caloriesLeft = caloriesLeft;
        this.stepsLeft = stepsLeft;
    }

    public void setCaloriesLeft(Double caloriesLeft) {
        this.caloriesLeft = caloriesLeft;
    }

    public void setStepsLeft(Integer stepsLeft) {
        this.stepsLeft = stepsLeft;
    }

    public int getImageId(Context context) {
        return context.getResources().getIdentifier("drawable/" + foodImage, null, context.getPackageName());
    }

    public String getFoodName() {
        return foodName;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public Double getCaloriesLeft() {
        return caloriesLeft;
    }

    public Integer getStepsLeft() {
        return stepsLeft;
    }
}
