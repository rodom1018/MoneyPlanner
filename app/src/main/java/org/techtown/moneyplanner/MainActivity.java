package org.techtown.moneyplanner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.techtown.moneyplanner.data.Usage;
import org.techtown.moneyplanner.data.User;
import org.techtown.moneyplanner.data.manager.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listView;
    private UsageListAdapter adapter;
    private List<Usage> planList= new ArrayList<>();

    private User user;
    private ValueEventListener usageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseManager.setFirebaseDB();

//        planList.add(new PlanItem("제주도", "2박3일"));
//        planList.add(new PlanItem("교통비","많이 "));

        listView = (ListView) findViewById(R.id.ListView);
        adapter = new UsageListAdapter(this, null);

        DatabaseManager.getUser().observe(this, (User user)->{
            this.user = user;
            Log.i(TAG, "Observe User");
            if(usageListener != null)
                DatabaseManager.getUsageDB().removeEventListener(usageListener);
            usageListener = DatabaseManager.getUsageDB().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    Log.i(TAG, "Call On Data Change");

                    // 유저 등록이 아직 안되었을 경우.
                    if(DatabaseManager.getUser() == null){
                        return;
                    }
                    else{
                        if(DatabaseManager.getUser().getValue() == null)
                            return;
                    }

                    Log.i(TAG, user.toString());

                    ArrayList<Usage> usages = new ArrayList<>();

                    // 리스트에서 사용자의 것만 가져온다.
                    snapshot.getChildren().forEach((DataSnapshot data)->{
                        Usage u = data.getValue(Usage.class);

                        if(u == null)
                            return;
                        if(u.getOwner() == null){
                            return;
                        }

                        if(user.getId().equals(u.getOwner()) || user.isMyGroup(u.getOwner())){
                            Log.i(TAG, u.toString());
                            usages.add(u);
                        }
                    });

                    adapter.setPlanList(usages);
                    listView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        });

//        DatabaseManager.getGroups(user).observe(this, (ArrayList<Group> groupList) -> {
//            Log.i(TAG, "Observe GroupList");
//            if(user != null){
//                Log.i(TAG, "Set GROUP LIST");
//                user.setGroups(groupList);
//            }
//        });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usage item = (Usage) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "선택 : "+item.getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void button1(View v){
        Intent intent = new Intent(getApplicationContext(), AddPlanActivity.class);
        startActivity(intent);
    }

    public void button2(View v){

    }

    @Override
    protected void onStart() {
        super.onStart();
        user = DatabaseManager.getUser().getValue();
        if(user == null){
            user = DatabaseManager.getUser(this).getValue();


            if(user == null){
                Intent intent = new Intent(this.getApplicationContext(), UserSetUpActivity.class);

                ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        (ActivityResult result)-> {
                            if (result.getResultCode() == RESULT_OK) {
                                Intent r = result.getData();

                                String email = r.getStringExtra("email");
                                String name = r.getStringExtra("name");
                                long balance = r.getLongExtra("balance", 0);


                                User newUser = new User(email, "", name, balance);

                                user = DatabaseManager.setUser(this, newUser).getValue();
                            }
                            else{
                                getSharedPreferences(getString(R.string.pref_key), MODE_PRIVATE)
                                        .edit()
                                        .putString(getString(R.string.user_key), "user1")
                                        .apply();

                                user = DatabaseManager.getUser(this).getValue();
                            }
                        }
                );

                launcher.launch(intent);
            }
        }
    }
}

