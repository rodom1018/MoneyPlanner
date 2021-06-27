package org.techtown.moneyplanner.data.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.techtown.moneyplanner.R;
import org.techtown.moneyplanner.data.Group;
import org.techtown.moneyplanner.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DatabaseManager {
    private static final String TAG = "DatabaseManager";

    private static FirebaseDatabase FireDB;
    public static void setFirebaseDB(){
        FireDB = FirebaseDatabase.getInstance();
    }

    public static DatabaseReference getUserDB(){

        return FireDB.getReference("USERS");
    }

    public static DatabaseReference getGroupDB(){
        return FireDB.getReference("GROUPS");
    }

    public static DatabaseReference getUsageDB() {
        return FireDB.getReference("USAGE");
    }

    private static MutableLiveData<ArrayList<Group>> groups;

    public static MutableLiveData<ArrayList<Group>> getGroups(User user){
        if(groups == null){
            groups = new MutableLiveData<>();
            groups.setValue(new ArrayList<>());

            getGroupDB().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(user == null)
                        return;
                    ArrayList<Group> groupList = new ArrayList<>();

                    Log.i(TAG, "GROUP TOTAL CNT: " + snapshot.getChildrenCount());

                    snapshot.getChildren().forEach((DataSnapshot data)->{
                        Group g = data.getValue(Group.class);
                        if(g == null)
                            return;
                        Log.i(TAG, g.toString());
                        if(g.getMembers().containsKey(user.getId())){
                            groupList.add(g);
                        }
                    });

                    groups.setValue(groupList);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

        return groups;
    }

    private static MutableLiveData<User> sUser;
    public static MutableLiveData<User> getUser(){
        if(sUser == null){
            sUser = new MutableLiveData<>();
        }
        return sUser;
    }
    public static MutableLiveData<User> getUser(Activity activity){
        // 만약 MutableLiveData가 존재하는 경우
        if(sUser != null){
            // 유저가 이미 존재하는 경우.
            if(sUser.getValue() != null) {
                return sUser;
            }
        }
        else{
            sUser = new MutableLiveData<>();
        }
        Context context = activity.getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.pref_key),
                Context.MODE_PRIVATE);

        if(sharedPreferences == null){
            return sUser;
        }
        else{
            String id = sharedPreferences.getString(context.getString(R.string.user_key), null);

            Log.i(TAG, ""+id);

            if(id == null)
                return sUser;

            getUserDB().get().addOnCompleteListener((Task<DataSnapshot> task)->{
                if(task.isComplete()) {
                    task.getResult().getChildren().forEach((DataSnapshot data)-> {
                        if(sUser.getValue() != null){
                            return;
                        }
                        User u = data.getValue(User.class);

                        if (id.equals(u.getId())) {
                            u.setGroups(getGroups(u).getValue());
                            Log.i(TAG, u.toString());

                            groups.observeForever(u::setGroups);

                            sUser.setValue(u);
                        }
                    });
                }
            });
        }

        if(sUser.getValue() != null){
            Log.i(TAG, "User Available " + sUser.getValue().toString());
        }

        return sUser;
    }
    public static MutableLiveData<User> setUser(Activity activity, User user){
        SharedPreferences pref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


        getUserDB().get().addOnCompleteListener((Task<DataSnapshot> task)->{
            user.setId("user"+ (task.getResult().getChildrenCount() + 1));
            Log.i(TAG, user.toString());
            getUserDB().child(user.getId()).setValue(user);
            sUser.setValue(user);
            editor.putString(activity.getString(R.string.user_key), user.getId());
            editor.apply();
        });
        return sUser;
    }
}
