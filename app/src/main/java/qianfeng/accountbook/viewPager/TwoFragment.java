package qianfeng.accountbook.viewPager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import qianfeng.accountbook.Accout;
import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class TwoFragment extends Fragment {


    private ExpandableListView exlv;
    private List<YearAndMonthBean> yearAndMonthList;
    private String start;

    private int prePosition = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) { // 由于我只发一条消息，就不用switch了

            header_viewpager.setCurrentItem(header_viewpager.getCurrentItem() + 1);
            mHandler.sendEmptyMessageDelayed(0, 5000);
        }
    };
    private ViewPager header_viewpager;


    @Override
    public void onAttach(Context context) { // 与宿主Activity建立连接
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // container就是使布局参数生效的父容器
        View view = inflater.inflate(R.layout.two_fragment, container, false);
        exlv = ((ExpandableListView) view.findViewById(R.id.exlv));

//        exlv.setAdapter();
        // 在这里要进行数据库查询,查询所有的数据并加载出来

        yearAndMonthList = new ArrayList<>();
        // 查询
        BmobQuery<Accout> accoutBmobQuery = new BmobQuery<>();
        String login_username = getActivity().getIntent().getStringExtra("login_username");
        accoutBmobQuery.addWhereEqualTo("loginUsername", login_username);
//        String createDate = "";
//        accoutBmobQuery.addWhereEqualTo("createAt",createDate);
//        accoutBmobQuery.ad
        String start = "2016-09-01 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        accoutBmobQuery.addWhereGreaterThanOrEqualTo("createdAt",new BmobDate(date)); 这两行代码是查询不成功的根源！！

        String end = "2016-09-30 23:59:59";
        Date date1 = null;
        try {
            date1 = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        accoutBmobQuery.addWhereLessThanOrEqualTo("createdAt",new BmobDate(date1)); 这两行代码无法实现查询结果，请不要使用


        accoutBmobQuery.findObjects(new FindListener<Accout>() {
            @Override
            public void done(List<Accout> list, BmobException e) {
                if (e == null) { // 只能说明网络连接没有问题
                    Log.d("google-my:", "done: Fragment02的网络连接没有问题");
                    if (list != null && list.size() > 0) { // 说明查询到数据

//                        List<Accout> accoutList = new ArrayList<Accout>();
                        List<DayBean> dayBeanList = new ArrayList<>();

                        Log.d("google-my:", "done: Fragment2能查询到数据");
                        for (Accout a : list) {
                            //list里面的数据:qianfeng.accountbook.Accout@419b6a88
                            Log.d("google-my:", "done: list里面的数据:" + a + "\t");
                            DayBean accout = new DayBean();
                            accout.setGet(a.getGet());
                            accout.setPain(a.getPain());
                            accout.setComm(a.getComm());
                            accout.setDate(a.getCreatedAt());
                            dayBeanList.add(accout);

//                            accout.setValue("createAt",);
                            //2016-09-23 19:52:09
//                            accoutList.add(accout);  // 还要根据日期进行再查询吧？
//                            Log.d("google-my:", "done: " + dayBean);
                        }
                        String str = "2016-09";
                        yearAndMonthList.add(new YearAndMonthBean(str, dayBeanList));

                        {

                            mHandler.sendEmptyMessageDelayed(0, 5000); // 图片自动循环播放

                            // 在set适配器之前，添加头部视图（ViewPager）
                            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.two_ex_header, null);
                            header_viewpager = (ViewPager) view1.findViewById(R.id.header_viewpager);
                            final LinearLayout dot_layout = (LinearLayout) view1.findViewById(R.id.dot_layout); // 加载一个点点布局
//                        header_viewpager.setAdapter();

                            List<String> urlList = new ArrayList<String>();
                            urlList.add("http://tnfs.tngou.net/image/cook/150802/1340f07baad474a757825191701d5e1e.jpg");
                            urlList.add("http://tnfs.tngou.net/image/cook/150802/d93e6aa700243192ca4f78f26bf14fe9.jpg");
                            urlList.add("http://tnfs.tngou.net/image/cook/150802/0c8f93ae57fbf175361d6949932a285e.jpg");
                            urlList.add("http://tnfs.tngou.net/image/cook/150802/2914d4174a8fb57f1b974879537f1b6b.jpg");
                            urlList.add("http://tnfs.tngou.net/image/cook/150802/1ab358cb54759f7f83928c4d5b97be87.jpg");

                            for (int i = 0; i < urlList.size(); i++) {

                                View dot = new View(getActivity());
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getActivity().getResources().getDisplayMetrics())),
                                        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getActivity().getResources().getDisplayMetrics()));
                                layoutParams.leftMargin = 20;
                                dot.setLayoutParams(layoutParams);

                                dot.setEnabled(false);
                                dot.setBackgroundResource(R.drawable.dot_bg);
                                dot_layout.addView(dot);
                            }
                            dot_layout.getChildAt(0).setEnabled(true);

                            // 下一步要做的操作，就是让他们适配起来
                            header_viewpager.setAdapter(new Two_header_viewpager_adapter(urlList, getActivity()));

                            header_viewpager.setCurrentItem(Integer.MAX_VALUE / 2 - 3);

                            header_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {

                                    position = position % 5;

                                    dot_layout.getChildAt(prePosition).setEnabled(false);

                                    dot_layout.getChildAt(position).setEnabled(true);

                                    prePosition = position;


                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });


                            Log.d("google-my:", "done: 我有进入到这个花括号吗？header");
                            exlv.addHeaderView(view1);
                            Log.d("google-my:", "done: 我有进入到这个花括号吗？footer");
                            exlv.addFooterView(view1);
                        }

                        Log.d("google-my:", "done: logd代码好诡异");
                        // 写适配器
                        exlv.setAdapter(new MyBaseExAdapter(getActivity(), yearAndMonthList));

                        for (int i = 0; i < yearAndMonthList.size(); i++) {
                            exlv.expandGroup(i); // 展开所有选项
                        }
                        exlv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                return true;
                            }
                        });



                        /*
09-24 12:48:14.745 28095-28095/qianfeng.accountbook D/google-my:: done: Fragment02的网络连接没有问题
09-24 12:48:14.745 28095-28095/qianfeng.accountbook D/google-my:: done: Fragment2能查询到数据
09-24 12:48:14.745 28095-28095/qianfeng.accountbook D/google-my:: done: list里面的数据:Accout{loginUsername='zhangsan', get='10', pain='88', comm='gggg'}
09-24 12:48:14.745 28095-28095/qianfeng.accountbook D/google-my:: done: list里面的数据:Accout{loginUsername='zhangsan', get='20', pain='12', comm='vdvdhdh'}
                         */

                    }
                } else {
                    Toast.makeText(getActivity(), "accountBmobQuery.findObjects(),网络连接有问题", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d("google-my:", "done: logd代码好诡异2222222");
        return view;
    }
}
