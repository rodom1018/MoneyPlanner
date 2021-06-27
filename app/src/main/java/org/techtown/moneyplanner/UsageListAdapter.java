package org.techtown.moneyplanner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.techtown.moneyplanner.data.Usage;

import java.util.List;

public class UsageListAdapter extends BaseAdapter {
    private Context context;
    private List<Usage> planList;

    public UsageListAdapter(Context context, List<Usage> planList){
        this.context=context;
        this.planList=planList;
    }

    public void setPlanList(List<Usage> planList) {
        this.planList = planList;
    }

    @Override
    public int getCount() {
        if(planList == null)
            return 0;
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

        title.setText(planList.get(position).getName());
        content.setText(planList.get(position).getMemo());

        return v;
    }
}
