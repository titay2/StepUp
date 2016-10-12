package com.example.bang.stepup3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by haiphan on 10/11/16.
 */
public class FoodAdapter extends ArrayAdapter<FoodItem> {
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<FoodItem> foodList;

    public FoodAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        this.context = (Activity) context;
        this.foodList = objects;
    }

    static class ViewHolder {
        TextView foodName;
        TextView foodCalories;
        TextView foodSteps;
        ImageView foodImage;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.food_item, null);
            viewHolder = new ViewHolder();
            viewHolder.foodImage = (ImageView) convertView.findViewById(R.id.foodImage);
            viewHolder.foodName = (TextView) convertView.findViewById(R.id.foodName);
            viewHolder.foodCalories = (TextView) convertView.findViewById(R.id.totalCalories);
            viewHolder.foodSteps = (TextView) convertView.findViewById(R.id.totalSteps);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.foodImage.setImageResource(foodList.get(position).getImageId(context));
        viewHolder.foodName.setText(foodList.get(position).getFoodName());
        viewHolder.foodCalories.setText("Calories: " + String.format("%.2f", foodList.get(position).getCaloriesLeft()));
        viewHolder.foodSteps.setText("Steps: " + foodList.get(position).getStepsLeft().toString());

        return convertView;
    }


}
