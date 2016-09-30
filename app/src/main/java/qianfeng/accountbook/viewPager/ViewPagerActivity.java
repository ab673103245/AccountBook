package qianfeng.accountbook.viewPager;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import qianfeng.accountbook.MyBroadcastReceiver.MyBroadcastReceiver;
import qianfeng.accountbook.R;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private EditText et_get;
    private EditText et_pain;
    private EditText et_comm;
    private ExpandableListView exlv;
    private RadioGroup rg;
    private qianfeng.accountbook.viewPager.three_viewpager_fragment_inner_right three_viewpager_fragment_inner_right;

    private MyBroadcastReceiver myBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = ((ViewPager) findViewById(R.id.viewPager));
        rg = ((RadioGroup) findViewById(R.id.rg));
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rela);
        final View view = LayoutInflater.from(this).inflate(R.layout.one_fragment, relativeLayout, false);

        View view2 = LayoutInflater.from(this).inflate(R.layout.two_fragment, relativeLayout, false);
        exlv = ((ExpandableListView) view2.findViewById(R.id.exlv)); // 现查找好控件ExpandListView





        OneFragment oneFragment = new OneFragment();
        TwoFragment twoFragment = new TwoFragment();
        ThreeFragment threeFragment = new ThreeFragment();
        FourFragment fourFragment = new FourFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
        fragmentList.add(fourFragment);

        viewPager.setAdapter(new MyFragmentViewPagerAdapter(getSupportFragmentManager(),fragmentList));
        // 设置最小预加载页数
        viewPager.setOffscreenPageLimit(3);// 解决Fragment1跳转到Fragment3，Fragment3页面的空白问题，因为Fragment3被销毁了，现在就是不让Fragment3销毁

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                viewPager.setCurrentItem(position);

                ((RadioButton) rg.getChildAt(position)).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId)
//                {
//                    case R.id.rb1:
//                        viewPager.setCurrentItem(0);
//                        break;
//                    case R.id.rb2:
//                        viewPager.setCurrentItem(1);
//                        break;
//
//                }
//            }
//        });


//        View view1 = LayoutInflater.from(this).inflate(R.layout.three_viewpager_fragment, null);
//
//        LinearLayout three_viewpager_ll2 = (LinearLayout) view1.findViewById(R.id.three_viewpager_ll2);
//
//        three_viewpager_fragment_inner_right = new three_viewpager_fragment_inner_right();
//
//        FragmentManager supportFragmentManager = getSupportFragmentManager();
//        // 这个布局无法找到这个控件！
//        supportFragmentManager.beginTransaction().replace(R.id.three_viewpager_ll2,three_viewpager_fragment_inner_right).commit();


    }

    @Override
    protected void onStart() {
        super.onStart();

        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.submitSuccessful");  // 给注册好的广播添加一个频道，用来接收发送给这个频道的sendBroadcast
        registerReceiver(myBroadcastReceiver,filter);// 注册一个广播而已,还没有发送广播呢!

    }

    public void rb1(View view) { // rb1的点击事件
        viewPager.setCurrentItem(0);
        ((RadioButton) rg.getChildAt(0)).setChecked(true);
    }


    public void rb2(View view) { // rb2的点击事件
        viewPager.setCurrentItem(1);
        ((RadioButton) rg.getChildAt(1)).setChecked(true);
    }


    public void rb3(View view) { // rb3
//        viewPager.setAdapter(new MyFragmentViewPagerAdapter(getSupportFragmentManager(),fragmentList));

        viewPager.setCurrentItem(2);
        ((RadioButton) rg.getChildAt(2)).setChecked(true);
    }

    public void rb4(View view) { // 点击第四个按钮
        viewPager.setCurrentItem(3);
        ((RadioButton) rg.getChildAt(3)).setChecked(true);
    }

//    @Override
//    public void update(String objectId) {
//        three_viewpager_fragment_inner_right.updateRightFragment(objectId);
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(myBroadcastReceiver);
    }
}
