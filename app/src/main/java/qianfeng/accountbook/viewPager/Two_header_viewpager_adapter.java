package qianfeng.accountbook.viewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import qianfeng.accountbook.MyCustomView.MyCustomImageView;
import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class Two_header_viewpager_adapter extends PagerAdapter {

    private List<String> urlList;
    private Context context;
    private LayoutInflater inflater;

    public Two_header_viewpager_adapter(List<String> urlList, Context context) {
        this.urlList = urlList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        Log.d("google-my:", "Two_header_viewpager_adapter: two_header_viewpager_adapter的适配器构造方法");
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        Log.d("google-my:", "destroyItem: 我有被清除吗？two_header_viewpager_adapter");
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.d("google-my:", "instantiateItem: ----------->我执行了多少次？two_header_viewpager_adapter");

        View view = inflater.inflate(R.layout.two_viewpager_item, container, false);

        MyCustomImageView customImageView = (MyCustomImageView) view.findViewById(R.id.myImageView);
//        ImageView imageView = (ImageView) view.findViewById(R.id.myImageView);
        Picasso.with(context).load(urlList.get(position % 5)).into(customImageView);

        container.addView(view);
        return view;
    }
}
