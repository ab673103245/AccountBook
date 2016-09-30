package qianfeng.accountbook.viewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class Three_viewPagerFragment_Adapter extends FragmentPagerAdapter {

    private List<Fragment> list; // 因为我这里不需要加载什么布局，所以不需要Context和Inflater?
    private List<String> titles;

    public Three_viewPagerFragment_Adapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    public Three_viewPagerFragment_Adapter(FragmentManager fm, List<Fragment> list, List<String> titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
