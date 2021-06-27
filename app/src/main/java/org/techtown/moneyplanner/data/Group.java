package org.techtown.moneyplanner.data;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Group {
    private String id;
    private HashMap<String, Boolean> members;
    private String name;

    public Group() {
    }

    public Group(String id, HashMap<String, Boolean> members, String name) {
        this.id = id;
        this.members = members;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, Boolean> getMembers() {
        return members;
    }

    public void setMembers(HashMap<String, Boolean> members) {
        this.members = members;
    }

    public void addMember(User user){
        this.members.put(user.getId(), false);
    }

    public int getMemberCount(){
        return this.members.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("id", this.id);
        result.put("members", this.members);
        result.put("name", this.name);

        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", members=" + members +
                ", name='" + name + '\'' +
                '}';
    }
}
