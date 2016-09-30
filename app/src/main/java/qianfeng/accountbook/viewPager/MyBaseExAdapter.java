package qianfeng.accountbook.viewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class MyBaseExAdapter extends BaseExpandableListAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<YearAndMonthBean> yearAndMonthBeanList;

    public MyBaseExAdapter(Context context, List<YearAndMonthBean> yearAndMonthBeanList) {
        this.context = context;
        this.yearAndMonthBeanList = yearAndMonthBeanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return yearAndMonthBeanList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return yearAndMonthBeanList.get(groupPosition).getDayBeanList().size(); // 待会有多少个item可以滑动多少次，就根据这些数量构建出来
    }

    @Override
    public Object getGroup(int groupPosition) {
        return yearAndMonthBeanList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return yearAndMonthBeanList.get(groupPosition).getDayBeanList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.ex_group,parent,false);
            holder = new GroupViewHolder();
            holder.tv_ex_group = (TextView) convertView.findViewById(R.id.tv_ex_group);
            convertView.setTag(holder);
        }else
        {
            holder = (GroupViewHolder) convertView.getTag();
        }

        holder.tv_ex_group.setText(yearAndMonthBeanList.get(groupPosition).getYearAndMonth());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.ex_child,parent,false);
            holder = new ChildViewHolder();
            holder.tv_child_get = (TextView) convertView.findViewById(R.id.tv_child_get);
            holder.tv_child_pain = (TextView) convertView.findViewById(R.id.tv_child_pain);
            holder.tv_child_comm = (TextView) convertView.findViewById(R.id.tv_child_comm);
            holder.tv_child_date = (TextView) convertView.findViewById(R.id.tv_child_date);
            convertView.setTag(holder);
        }else
        {
           holder = (ChildViewHolder) convertView.getTag();
        }

        DayBean dayBean = yearAndMonthBeanList.get(groupPosition).getDayBeanList().get(childPosition);
        String get = "收入:"+dayBean.getGet();
        holder.tv_child_get.setText(get);
        String pain = "支出:"+ dayBean.getPain();
        holder.tv_child_pain.setText(pain);
        String comm = "备注信息:"+dayBean.getComm();
        holder.tv_child_comm.setText(comm);
        String date = "日期:" + dayBean.getDate();
        holder.tv_child_date.setText(date);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { // 子item是否可以被选择，false

        return false;
    }

    class GroupViewHolder
    {
        ImageView iv_ex_group;
        TextView tv_ex_group;
    }

    class ChildViewHolder
    {
        TextView tv_child_get;
        TextView tv_child_pain;
        TextView tv_child_comm;
        TextView tv_child_date;
    }

}
