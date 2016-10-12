package com.example.bang.stepup3;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

/**
 * Created by haiphan on 10/11/16.
 */
public class FoodListActivity extends AppCompatActivity {
    public final static String EXTRA_BT_DEVICE= "com.example.bang.stepup3.DeviceSetupActivity.EXTRA_BT_DEVICE";

    private ListView mListView;
    BluetoothDevice btDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);
        btDevice = getIntent().getParcelableExtra(EXTRA_BT_DEVICE);
        mListView = (ListView) findViewById(R.id.listView);
// 1
//        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);
// 2

//        String[] listItems = new String[recipeList.size()];
// 3
//        for(int i = 0; i < recipeList.size(); i++){
//            Recipe recipe = recipeList.get(i);
//            listItems[i] = recipe.title;
//        }
// 4
        FoodAdapter adapter = new FoodAdapter(this, R.layout.food_item, User.getInstance().getFoodList());
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
            Intent intent = new Intent(FoodListActivity.this, DeviceSetupActivity.class);
            intent.putExtra("position", position);
            intent.putExtra(DeviceSetupActivity.EXTRA_BT_DEVICE, btDevice);
            startActivityForResult(intent, 1);

        }
    };
}
