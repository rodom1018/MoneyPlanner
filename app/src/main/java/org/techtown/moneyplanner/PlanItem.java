package org.techtown.moneyplanner;

import java.util.HashMap;
import java.util.Map;

public class PlanItem {
    String title;
    String content;
    Map<String, Integer> moneyitem = new HashMap<String, Integer>();

    public PlanItem(String name, String content){
        this.title=name;
        this.content=content;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }
    public String  getContent(){
        return content;
    }

    public void setContent(String content){
        this.content=content;
    }

    public void setitem(String name, int value){
        moneyitem.put(name, value);
    }


}
