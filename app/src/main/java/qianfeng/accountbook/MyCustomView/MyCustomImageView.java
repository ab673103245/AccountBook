package qianfeng.accountbook.MyCustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class MyCustomImageView extends ImageView {

    private Paint paint;

    private static final int DEFAULTWIDTH = 400;
    private static final int DEFAULTHEIGHT = 400;

    private static final int ROUNDRECT = 0;
    private static final int DEMOND = 1;

    private int shape;
    private int rotate;

    public MyCustomImageView(Context context) {
        this(context, null);
    }

    public MyCustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyCustomImageView);
        shape = ta.getInt(R.styleable.MyCustomImageView_shape, 0);
        rotate = ta.getInt(R.styleable.MyCustomImageView_rotate, 45);
        ta.recycle(); // 用完记得要回收

        paint = new Paint();
        paint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 模式有三种：精确测量模式，用于match_parent以及有具体dp值的情况下,MOST：则用于wrap_content模式，最后一个是子控件无限大模式，处理方式与wrap_content相同
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                widthSize = DEFAULTWIDTH;
                break;

            case MeasureSpec.EXACTLY:
                break;
        }

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                heightSize = DEFAULTHEIGHT;
                break;

            case MeasureSpec.EXACTLY:
                break;
        }

        widthSize = heightSize = widthSize > heightSize ? heightSize : widthSize;
        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 画出图片，先画形状，再画传进来的图片
        // 第一步先得到Drawable
        Drawable drawable = getDrawable();

        if (drawable == null) // 先看用户有没有设置drawable，如果没有的话，那就不必往下画src图片了
        {
            return;
        }


        paint.reset(); // 每次进来，重置画笔


        // 接下来，画图片了，需要三个bitmap，一个bitmap是原图，一个bitmap是经过放缩后的图片，最后一个bitmap是自定义画布的内容要生成到的bitmap容器中
        // drawable中有个子类BitmapDrawable中有个方法getBitmap，将drawable转化为bitmap
        Bitmap srcBitmap = ((BitmapDrawable) drawable).getBitmap();

        // 接下来要创建一个用于获取图片放缩的bitmap
        // 得到控件的尺寸
        int width = getWidth();
        int height = getHeight();

        // 得到原图的尺寸
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();


        Matrix matrix = new Matrix();
        float scale = Math.max((width * 1f / srcWidth), (height * 1f / srcHeight));
        matrix.postScale(scale, scale);

        // bitmap在这里就变成了经过放缩后的原图了
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcWidth, srcHeight, matrix, true);

        // 创建一个和控件尺寸一样的空白的bitmap，用于自定义画布绘制的载体
        Bitmap blankBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(blankBitmap);

        // 旋转
        mCanvas.save();

        // 我所有的工作，都先在mCanvas中画好，即画好这个blankBitmap，然后再把blankBitmap画到canvas上就可以了
        switch (shape) {
            case ROUNDRECT:
                mCanvas.drawRoundRect(new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight()), 15, 15, paint); // 画一个圆角矩形

                break;

            case DEMOND:
                // 可以在这里进行旋转
                mCanvas.rotate(rotate,getWidth()/2,getHeight()/2); // 旋转

                Path path = new Path();
                path.moveTo(getMeasuredWidth() / 2, 0); // 图像绘制的起点移动到这里
                path.lineTo(getMeasuredWidth(), getMeasuredHeight() / 2);
                path.lineTo(getMeasuredWidth() / 2, getMeasuredHeight());
                path.lineTo(0, getMeasuredHeight() / 2);
                path.close(); // 这行代码就是最后一条线，会自动封闭图形。与起点联合在一起
                mCanvas.drawPath(path, paint);
                break;
        }

        // 还原旋转前的画布
        mCanvas.restore();

        // 设置画笔的属性，switch之后就有图像1了，再设置图像1与图像2之间的模式，是取交集还是其他
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawBitmap(bitmap,0,0,paint);

        // 系统画布显示我所画的blankBitmap中的所有图像
        canvas.drawBitmap(blankBitmap,0,0,null);

        // 最后一步，回收经过放缩后的bitmap
        if(bitmap != null && !bitmap.isRecycled())
        {
            bitmap.recycle();
        }


    }
}
