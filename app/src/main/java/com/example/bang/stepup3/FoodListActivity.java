package com.example.bang.stepup3;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
    FoodAdapter adapter;
    BluetoothDevice btDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_list);
        btDevice = getIntent().getParcelableExtra(EXTRA_BT_DEVICE);
        mListView = (ListView) findViewById(R.id.listView);

        adapter = new FoodAdapter(this, R.layout.food_item, User.getInstance().getFoodList());
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(onItemClickListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodListActivity.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
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
