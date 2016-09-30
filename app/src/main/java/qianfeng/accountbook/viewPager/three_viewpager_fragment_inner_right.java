package qianfeng.accountbook.viewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import qianfeng.accountbook.Accout;
import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class three_viewpager_fragment_inner_right extends Fragment {

    private List<DayBean> dayBeanList;
    private ListView right_listView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dayBeanList = new ArrayList<>();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.three_viewpager_fragment_inner_right, container, false);
        right_listView = (ListView) view.findViewById(R.id.three_viewpager_fragment_inner_right_listview);

        Bundle bundle = getArguments();
        String objectId = bundle.getString("objectId");
        updateRightFragment(objectId);

        return view;
    }

    public void updateRightFragment(String objectId)  // 根据宿主Activity(有时候也不一定是Activity，也可以是Fragment)传进来的String objectId来更新右边Fragment的UI就可以了
    {
        BmobQuery<Accout> accoutBmobQuery = new BmobQuery<>();
        accoutBmobQuery.addWhereEqualTo("objectId",objectId);
        accoutBmobQuery.findObjects(new FindListener<Accout>() {
            @Override
            public void done(List<Accout> list, BmobException e) {
                if(e == null) // 连接成功
                {
                    if(list != null && list.size() > 0) // 如果集合中的数据不为空，就赋值，一般只有一个啦！
                    {

                        for(Accout a : list)
                        {
                            Log.d("google-my:", "done:three里面是什么对象？ " + a);
                            DayBean dayBean = new DayBean();
                            dayBean.setGet(a.getGet());
                            dayBean.setPain(a.getPain());
                            dayBean.setComm(a.getComm());
                            dayBean.setDate(a.getCreatedAt());
                            dayBeanList.add(dayBean);
                        }
                        // 查询成功之后，马上更新UI啦！
                        right_listView.setAdapter(new Three_right_listView_Adapter(getActivity(),dayBeanList));
                    }
                }else
                {
                    Log.d("google-my:", "done: 在fragment_inner_right 里面连接不成功!");
                     List<String> llist = new ArrayList<String>();
                    llist.add("12212");
                    llist.add("134342");
                    llist.add("3fff212");
                    llist.add("1fsf");
                    llist.add("fdfsf12");
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, llist);
                    right_listView.setAdapter(adapter);

                }
            }
        });
    }

}
