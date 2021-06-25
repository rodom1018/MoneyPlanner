package org.techtown.moneyplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private PlanAdapter adapter;
    private List<PlanItem> planList= new ArrayList<PlanItem>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        planList.add(new PlanItem("제주도", "2박3일"));
        planList.add(new PlanItem("교통비","많이 "));

        listView = (ListView) findViewById(R.id.ListView);
        adapter = new PlanAdapter(getApplicationContext(), planList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlanItem item = (PlanItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : "+item.getTitle(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void button1(View v){
        Intent intent = new Intent(getApplicationContext(), AddPlanActivity.class);
        startActivity(intent);
    }

    public void button2(View v){

    }
}

