package com.gzucm.wsyy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.gzucm.wsyy.R;


/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class ProportionImageView extends ImageView {

    private float mWidthProportion;
    private float mHeightProportion;
    //new时使用下面的构造方法
    public ProportionImageView(Context context) {
        this(context,null);
    }

    //layout声明时用
    public ProportionImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    //布局声明并且自己有style的
    public ProportionImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context,attrs);
    }

    private void initAttribute(Context context, AttributeSet attrs) {

        //获取属性的数组
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProportionImageView);

        //获取单个属性值
        mWidthProportion = array.getFloat(R.styleable.ProportionImageView_widthProportion,0);
        mHeightProportion = array.getFloat(R.styleable.ProportionImageView_heightProportion,0);

        Log.e("TAG",mWidthProportion + " " + mHeightProportion);
        array.recycle();
    }

    //1.基本控件 extends View TextView Button
    //2.高级控件 ListView
    //3.自定义控件 就是官方给的View不能实现的结果

    //自定义View都会继承自系统的 View ViewGroup


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //宽
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //高 比例2/1
        int height = (int) (width*mHeightProportion/mWidthProportion);

        //设置宽高
        setMeasuredDimension(width,height);
    }
}
