package qianfeng.accountbook.viewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class ThreeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.three_fragment, container, false);
        TabLayout tab_layout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.three_viewpager);

        Three_viewPagerFragment three_viewPagerFragment = new Three_viewPagerFragment();
        Three_viewPagerFragment three_viewPagerFragment2 = new Three_viewPagerFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(three_viewPagerFragment);
        fragments.add(three_viewPagerFragment2);

        List<String> titles = new ArrayList<>();

        for (int i = 0; i < fragments.size(); i++) {
            titles.add("标题 " + (i + 1));
        }

        // 在setAdapter的时候，就要传进去TabLayout的标题了！在FragmentPagerAdapter里面有个方法，是显示传进来的集合的文本，显示在标题栏上
        viewPager.setAdapter(new Three_viewPagerFragment_Adapter(getActivity().getSupportFragmentManager(), fragments, titles));

        // 将TabLayout和Viewpager关联起来！
        tab_layout.setupWithViewPager(viewPager);

        return view;
    }


}
