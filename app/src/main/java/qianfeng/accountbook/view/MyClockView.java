package qianfeng.accountbook.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class MyClockView extends View {
    private Paint cyclePaint;

    public MyClockView(Context context) {
        this(context,null);
    }

    public MyClockView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 首先初始化一个
    }
}
