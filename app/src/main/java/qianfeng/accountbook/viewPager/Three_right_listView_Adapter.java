package qianfeng.accountbook.viewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class Three_right_listView_Adapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<DayBean> dayBeanList;

    public Three_right_listView_Adapter(Context context, List<DayBean> dayBeanList) {
        this.context = context;
        this.dayBeanList = dayBeanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dayBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return dayBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.three_viewpager_fragment_inner_right_listview_item,parent,false);
            holder = new ViewHolder();
            holder.tv_get__right_listview = (TextView) convertView.findViewById(R.id.tv_get__right_listview);
            holder.tv_pain__right_listview = (TextView) convertView.findViewById(R.id.tv_pain__right_listview);
            holder.tv_comm__right_listview = (TextView) convertView.findViewById(R.id.tv_comm__right_listview);
            holder.tv_date__right_listview = (TextView) convertView.findViewById(R.id.tv_date__right_listview);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        // private List<DayBean> dayBeanList;
        DayBean dayBean = dayBeanList.get(position);
        String get = "收入是:(元)" + dayBean.getGet();
        holder.tv_get__right_listview.setText(get);
        String pain = "支出是:(元)" + dayBean.getPain();
        holder.tv_pain__right_listview.setText(pain);
        String comm = "备注信息是:" + dayBean.getComm();
        holder.tv_comm__right_listview.setText(comm);
        String date = "日期是:" + dayBean.getDate();
        holder.tv_date__right_listview.setText(date);


        return convertView;
    }

    class ViewHolder
    {
        TextView tv_get__right_listview;
        TextView tv_pain__right_listview;
        TextView tv_comm__right_listview;
        TextView tv_date__right_listview;
    }
}
