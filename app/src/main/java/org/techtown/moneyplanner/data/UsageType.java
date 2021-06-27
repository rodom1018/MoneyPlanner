package org.techtown.moneyplanner.data;

import androidx.annotation.NonNull;

public enum UsageType {
    IN {
        @NonNull
        @Override
        public String toString() {
            return "in";
        }
    },
    OUT{
        @NonNull
        @Override
        public String toString() {
            return "out";
        }
    },
    NONE;

    public static UsageType toUsageType(Object obj){
        if(obj instanceof String){
            if(obj.toString().equals(IN.toString())){
                return IN;
            }
            else if(obj.toString().equals(OUT.toString())){
                return OUT;
            }
        }
        else if(obj instanceof UsageType){
            return (UsageType)obj;
        }
        return NONE;
    }
}
