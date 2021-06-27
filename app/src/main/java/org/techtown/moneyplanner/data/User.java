package org.techtown.moneyplanner.data;

import android.util.Log;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static final String TAG = "USER";
    private String email;
    private String id;
    private String name;
    private long balance;

    private ArrayList<Group> groups = new ArrayList<>();

    public User() {
    }

    public User(String email, String id, String name, long balance) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", groups=" + groups +
                '}';
    }

    @Exclude
    public void setGroups(ArrayList<Group> groupList){
        groups = groupList;
        Log.i(TAG, "SET GROUPS " + groups.toString());
    }

    @Exclude
    public ArrayList<Group> getGroups(){
        return groups;
    }

    public boolean isMyGroup(String groupId){
        if(groups.size() == 0){
            return false;
        }
        for(Group g: groups){
            if(g.getId().equals(groupId))
                return true;
        }
        return false;
    }
}
