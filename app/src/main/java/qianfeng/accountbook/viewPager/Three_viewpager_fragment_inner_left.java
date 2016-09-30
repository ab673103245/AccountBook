package qianfeng.accountbook.viewPager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import qianfeng.accountbook.Accout;
import qianfeng.accountbook.R;

/*
 *  * 接口回调，五个步骤：
 * 1.定义一个接口
 * 2.定义一个接口变量
 * 3.初始化接口变量
 * 4.调用接口中的方法
 * 5.实现接口
 * 步骤 1-4 在本类中做，第5步在其他类中做
 */

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class Three_viewpager_fragment_inner_left extends Fragment {

    List<String> stringList;

    List<ThreeInnerListViewItemBean> threeInnerListViewItemBeanList;

    private IUpdateRightFragment iUpdateRightFragment;  // 第2个步骤完成

    public interface IUpdateRightFragment{  // 第1个步骤完成
        void update(String objectId);
        //  String objectId = a.getObjectId();
    }

    @Override
    public void onAttach(Activity activity) { // 跟宿主Activity建立连接时，立刻让宿主Activity实现这个接口，并把宿主Activity传到自己这里来。
        super.onAttach(activity);
        if(activity instanceof IUpdateRightFragment) // 看宿主是否实现了这个类中的接口，如果实现了，才赋值，否则，不赋值。传回来的activity毫无疑问就是宿主Activity, getActivity()里面拿到的也是这个传回来的Activity
        { // 第3个步骤完成
            iUpdateRightFragment = (IUpdateRightFragment) activity; // 强转为它(Activity)的父类,转化为父类，要强转。
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stringList = new ArrayList<>();
//        list.add("123");
//        list.add("34");
//        list.add("45");
//        list.add("67");
//        list.add("123");
//        list.add("123");
//        list.add("123");
//        list.add("123");
        BmobQuery<Accout> accoutBmobQuery = new BmobQuery<>();
        String login_username = getActivity().getIntent().getStringExtra("login_username");
        accoutBmobQuery.addWhereEqualTo("loginUsername",login_username);

        threeInnerListViewItemBeanList = new ArrayList<>();

        accoutBmobQuery.findObjects(new FindListener<Accout>() {
            @Override
            public void done(List<Accout> list, BmobException e) {
                if(e == null) // 网络连接没有问题
                {
                    if(list != null && list.size() > 0)
                    {
                        for(Accout a : list)
                        {
                            ThreeInnerListViewItemBean threeInnerListViewItemBean = new ThreeInnerListViewItemBean();
                            threeInnerListViewItemBean.setDate(a.getCreatedAt());
                            threeInnerListViewItemBean.setId(a.getObjectId());
                            threeInnerListViewItemBeanList.add(threeInnerListViewItemBean); //把数据添加进Bean集合中，主要是数据的id,进行网络查找时可以传递这个数据。

                            stringList.add(a.getCreatedAt()); // 这个是ArrayAdapter的集合,需要显示的是日期
                        }
                    }
                }else
                {
                    Log.d("google-my:", "done: Three_viewpager里面的查询请求，网络连接有问题");
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.three_viewpager_fragment_inner_left, container, false);
        ListView left_listView = (ListView) view.findViewById(R.id.three_viewpager_fragment_inner_left_listview);


        left_listView.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,stringList));
        // 在这里进行查询，然后把查询到的结果显示在ListView上，然后给listView设置点击事件，传递给另一个Fragment的数据是点击的日期
        // 对应的网络数据库的id，再用这个id进行查询，将查询到的结果显示在右边的Fragment上，运用的Fragment的通信方式是两种中的第一种，
        // 运用接口回调进行Fragment通信，把结果显示在右边的ListView上，将ViewPagerActivity作为实现内部接口的Activity类。

        // 设定点击事件，开始进行Fragment间的通信了！用的是接口回调
        left_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
//                // 在这里进行接口回调
//                if(iUpdateRightFragment != null) // 第4个步骤完成 ，就是在监听ListView点击事件里面，进行调用接口中的方法
//                {
//                    Log.d("google-my:", "onItemClick: 应该是不会调用到这里的，因为我的宿主Activity没有实现接口，而是ViewFragment实现了！");
//                    iUpdateRightFragment.update(threeInnerListViewItemBeanList.get(position).getId());
//                }

                // 根据点击事件，动态创建右边的Fragment
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                three_viewpager_fragment_inner_right fragment = new three_viewpager_fragment_inner_right();
                Bundle args = new Bundle();
                String id1 = threeInnerListViewItemBeanList.get(position).getId();
                args.putString("objectId",id1);
                fragment.setArguments(args);
                transaction.replace(R.id.three_viewpager_ll2, fragment);
                transaction.commit();


            }
        });




        return view;
    }




}
