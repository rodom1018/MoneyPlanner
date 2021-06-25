package org.techtown.moneyplanner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PlanAdapter extends BaseAdapter {
    private Context context;
    private List<PlanItem> planList;

    public PlanAdapter(Context context, List<PlanItem> planList){
        this.context=context;
        this.planList=planList;
    }


    @Override
    public int getCount() {
        return planList.size();
    }

    @Override
    public Object getItem(int position) {
        return planList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.plan_item, null);

        TextView title=(TextView) v.findViewById(R.id.title);
        TextView content=(TextView) v.findViewById(R.id.description);

        title.setText(planList.get(position).getTitle());
        content.setText(planList.get(position).getContent());

        return v;
    }
}
