package com.gzucm.wsyy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzucm.wsyy.R;
import com.gzucm.wsyy.utils.Dateutil;

import java.util.List;

/**
 * Created by Administrator on 2017/12/2 0002.
 */
public class DaySelectedView extends LinearLayout implements View.OnClickListener{
    private ImageView Left;
    private ImageView Right;
    private TextView date;
    //根据日期来获取是星期几
    private static List<String> WEEK_STR;
    //当前一周7天的日期,这个是已经知道的
    private static List<String> DATE_STR;
    //当前选中的天
    private String selected;
    private String mDay;
    public String getmDay() {
        return mDay;
    }

    public void setmDay(String Day) {
        this.mDay = Day;
    }

    public DaySelectedView(Context context) {
        this(context,null);

    }
    public DaySelectedView(Context context, AttributeSet attrs) {
        this(context, attrs,0,null);
    }
    public DaySelectedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,String day) {
        super(context, attrs, defStyleAttr);
        mDay = day;
        init(context, attrs,day);
    }

    private void init(Context context, AttributeSet attrs,String day) {
        LayoutInflater.from(context).inflate(R.layout.layout_date, this, true);
        initView();
        initDate();
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DaySelectedView);
            //处理titleBar背景色
            int titleBarBackGround = attributes.getResourceId(R.styleable.DaySelectedView_title_background_color, Color.TRANSPARENT);
            setBackgroundResource(titleBarBackGround);
            //先处理左边按钮
            int leftButtonDrawable = attributes.getResourceId(R.styleable.DaySelectedView_left_button_drawable, R.drawable.larrow);
            Left.setBackgroundResource(leftButtonDrawable);
            //先处理右边按钮
            int rightButtonDrawable = attributes.getResourceId(R.styleable.DaySelectedView_right_button_drawable, R.drawable.rarrow);
            Right.setBackgroundResource(rightButtonDrawable);

            //获取标题显示颜色
            int titleTextColor = attributes.getColor(R.styleable.DaySelectedView_title_text_color, Color.WHITE);
            date.setTextColor(titleTextColor);
            attributes.recycle();
            selected = day;
            Log.i("天天",""+day);
        }
//        setSelected(day);

//        String today = setSelected(day);
//        Log.i("天天",""+today);
//        date.setText(today);
    }
    private void initDate() {
        DATE_STR = new Dateutil().getSevenDate(7);
        WEEK_STR = new Dateutil().getWeek(DATE_STR);
    }
    public void setSelected(String day){
        int index = WEEK_STR.indexOf(day);
        String d = DATE_STR.get(index);
        selected = mDay;
        date.setText(d+mDay);
    }
    private void initView() {
        Left = (ImageView) findViewById(R.id.iv_left);
        Right= (ImageView) findViewById(R.id.iv_right);
        date = (TextView) findViewById(R.id.tv_date);
        Left.setOnClickListener(this);
        Right.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                int index = WEEK_STR.indexOf(selected);
                if((index - 1) < 0){
//                    Toast.makeText(getContext(),"没有数据了",Toast.LENGTH_SHORT).show();
                }else {
                    String d = DATE_STR.get(index - 1);
                    String day = WEEK_STR.get(index - 1);
                    selected = day;
                    date.setText(d+day);
                }
                break;
            case R.id.iv_right:
                int index2 = WEEK_STR.indexOf(selected);
                if((index2 + 1) > 6){
//                    Toast.makeText(getContext(),"没有数据了",Toast.LENGTH_SHORT).show();
                }else {
                    String d = DATE_STR.get(index2 + 1);
                    String day = WEEK_STR.get(index2 + 1);
                    selected = day;
                    date.setText(d+day);
                }
                break;
        }
    }
}
