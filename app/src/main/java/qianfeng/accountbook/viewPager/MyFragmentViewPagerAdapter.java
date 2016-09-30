package qianfeng.accountbook.viewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class MyFragmentViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public MyFragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    // 不销毁ViewPager里面的Fragment，就不会在广播里引发空指针问题。

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//
//    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
