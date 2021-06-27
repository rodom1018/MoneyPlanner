//package org.techtown.moneyplanner;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class PlanItem {
//    String title;
//    String content;
//    Map<String, List<Integer>> moneyitem = new HashMap<String, List<Integer>>();
//
//    public PlanItem(String name, String content){
//        this.title=name;
//        this.content=content;
//    }
//
//    public String getTitle(){
//        return title;
//    }
//
//    public void setTitle(String title){
//        this.title=title;
//    }
//    public String  getContent(){
//        return content;
//    }
//
//    public void setContent(String content){
//        this.content=content;
//    }
//
//    public void setItem(String name, int value){ // name: 교통비, value: 금액
//        if(moneyitem.containsKey(name)){
//            moneyitem.get(name).add(value);
//        }
//        moneyitem.put(name, value);
//    }
//
//
//}
