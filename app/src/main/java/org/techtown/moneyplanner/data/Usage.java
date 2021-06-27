package org.techtown.moneyplanner.data;

import android.util.Log;

import com.google.firebase.database.Exclude;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Usage {
    private LocalDate startDate;
    private LocalDate endDate;
    private String owner;
    private String id;
    private String memo;
    private String name;
    private UsageType type;
    private long money;

    public Usage(){

    }

    public Usage(String owner, String id, String name, HashMap<String, Object> date, String type, long money, String memo) {
        this.owner = owner;
        this.id = id;
        this.name = name;
        this.money = money;
        this.memo = memo;

        this.setType(type);
        this.setDate(date);
    }

    public String getId() {
        return this.id;
    }

    public void setDate(Map<String, Object> date){
        this.startDate = LocalDate.from(DateTimeFormatter.BASIC_ISO_DATE.parse(date.get("from").toString()));
        this.endDate = LocalDate.from(DateTimeFormatter.BASIC_ISO_DATE.parse(date.get("to").toString()));

        Log.i("USAGE", this.name +"_" + this.startDate.toString() +" to " + this.endDate.toString());
    }

    public Map<String, String> getDate(){
        HashMap<String, String> date = new HashMap<>();
        date.put("from", this.startDate.format(DateTimeFormatter.BASIC_ISO_DATE));
        date.put("to", this.startDate.format(DateTimeFormatter.BASIC_ISO_DATE));

        return date;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UsageType getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = UsageType.toUsageType(type);
    }


    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("id", this.id);
        result.put("date", this.getDate());
        result.put("name", this.name);
        result.put("money", this.money);
        result.put("owner", this.owner);
        result.put("memo", this.memo);
        result.put("type", this.type.toString());

        return result;
    }

    @Override
    public String toString() {
        return "Usage{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", owner='" + owner + '\'' +
                ", id='" + id + '\'' +
                ", memo='" + memo + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", money=" + money +
                '}';
    }
}
