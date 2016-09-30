package qianfeng.accountbook.viewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class Three_viewPagerFragment extends Fragment {

    private three_viewpager_fragment_inner_right fragment_inner_right;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.three_viewpager_fragment, container, false);
//        LinearLayout three_viewpager_layoutLeft = (LinearLayout) view.findViewById(R.id.three_viewpager_ll1);
        LinearLayout three_viewpager_layoutRight = (LinearLayout) view.findViewById(R.id.three_viewpager_ll2);


        // 动态时添加的东西，只会加载一次，这是与Activity和Fragment的声明周期有关的东西！！！！！
        // 这个onCreateView方法只会加载一次！！！！！
        // 要适配不同的数据的话，还是静态添加吧

        // !!* 动态添加下一个的话，你就再新建一个自定义类啊！继承自Fragment，再写里面的onCreateView方法，使他加载和这个Fragment不同的布局啊！

        // 进行动态添加，add 将新建出来的左边和右边的碎片，填补这个Layout左边和右边的布局
//        FragmentManager fragmentmanager = getActivity().getSupportFragmentManager(); // 记住在Fragment里面都要先getAcitvity 再 getSupportFragmentmanager()
//        FragmentTransaction transaction = fragmentmanager.beginTransaction();
//
//        Three_viewpager_fragment_inner_left fragment_inner_left = new Three_viewpager_fragment_inner_left();
//        transaction.replace(R.id.three_viewpager_ll1, fragment_inner_left);

//        fragment_inner_right = new three_viewpager_fragment_inner_right();
//        transaction.replace(R.id.three_viewpager_ll2, fragment_inner_right);

//        transaction.commit();

        // Fragment里面可以找到宿主Activity的布局


        return view;

    }


//    @Override
//    public void update(String objectId) {  // 这个类要实现接口，证明它只是传递参数的桥梁而已！
//
//        fragment_inner_right.updateRightFragment(objectId);
//
//    }
}
