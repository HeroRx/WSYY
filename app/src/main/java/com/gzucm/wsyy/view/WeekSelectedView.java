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

import java.util.ArrayList;
import java.util.List;

import static com.gzucm.wsyy.R.drawable.selected;
import static com.gzucm.wsyy.R.id.ll01;


/**
 * Created by Administrator on 2017/11/19 0019.
 */

public class WeekSelectedView extends LinearLayout implements View.OnClickListener{

    //七个item
    private LinearLayout ll_1, ll_2, ll_3, ll_4, ll_5, ll_6, ll_7;
    //七个星期
    private TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7;
    private TextView tv_date1, tv_date2, tv_date3, tv_date4, tv_date5, tv_date6, tv_date7;
    //根据日期来获取是星期几
    private static List<String> WEEK_STR;
    //当前一周7天的日期,这个是已经知道的
    private static List<String> DATE_STR;
    //解析为月日
    private List<String> DATE_STR_format;
    //用于存放所有字条目的引用
    private List<LinearLayout> llList;

    private int background;
    private int itembackground;
    private float dateTextSize;
    private int dateTextColor;
    private float WeekTextSize;
    private int WeekTextColor;

    private LinearLayout turn_back;
    private LinearLayout back;
    //收起按钮是否有改变
    boolean isChange = false;
    private ImageView arrow;
    //选中的item
    private int Selected = 0;
    private TextView tvtb;

    public String getFirst(){
        int clickNum = (int)ll_1.getTag();
        return WEEK_STR.get(clickNum);
    }
    public String getFirstDay(){
        int clickNum = (int)ll_1.getTag();
        return DATE_STR.get(clickNum) + WEEK_STR.get(clickNum);
    }

    public void setSelectedDay(String day){
        int clickNum;
        clickNum = WEEK_STR.indexOf(day);
        if(listener!=null){

            listener.onItemClick(ll_1, WEEK_STR.get(clickNum),
                    clickNum, DATE_STR_format.get(clickNum));

        }

        Selected = clickNum;
        clearLL(0);
        clearLL(1);
        clearLL(2);
        clearLL(3);
        clearLL(4);
        clearLL(5);
        clearLL(6);
        setLL();
    }
    public static List<String> getDATE_STR() {
        return DATE_STR;
    }

    public static List<String> getWEEK_STR() {
        return WEEK_STR;
    }

    public WeekSelectedView(Context context) {
        this(context, null);
    }

    public WeekSelectedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekSelectedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_week, this, true);
        initView();
        if (attrs != null) {
            //设置默认的参数
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WeekSelected);
            background = ta.getColor(R.styleable.WeekSelected_android_background, Color.TRANSPARENT);
            itembackground = ta.getColor(R.styleable.WeekSelected_itembackground,Color.TRANSPARENT);
            dateTextSize = ta.getInteger(R.styleable.WeekSelected_DateTextSize, 10);
            dateTextColor = ta.getColor(R.styleable.WeekSelected_DateTextColor, Color.RED);
            WeekTextSize = ta.getInteger(R.styleable.WeekSelected_WeekTextSize, 14);
            WeekTextColor = ta.getColor(R.styleable.WeekSelected_WeekTextColor, Color.RED);
            ta.recycle();  //注意回收

        }
        initAttrs();
        initItem();
        initDate();
        setLLText(ll_1,0);
        setLL();
        setDateWeek();

    }

    private void setDateWeek() {
        for(int i = 0;i < llList.size();i++){
            setLLText(llList.get(i),i);
        }
    }

    private void initDate() {
        DATE_STR = new Dateutil().getSevenDate(7);
        WEEK_STR = new Dateutil().getWeek(DATE_STR);
        DATE_STR_format = new Dateutil().getSeven(DATE_STR);
    }

    private void initItem() {
        llList = new ArrayList<>();
        llList.add(ll_1);
        llList.add(ll_2);
        llList.add(ll_3);
        llList.add(ll_4);
        llList.add(ll_5);
        llList.add(ll_6);
        llList.add(ll_7);
        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        ll_4.setOnClickListener(this);
        ll_5.setOnClickListener(this);
        ll_6.setOnClickListener(this);
        ll_7.setOnClickListener(this);

        setBackgroundColor(background);
    }

    private void initAttrs() {

        tv_date1.setTextSize(dateTextSize);
        tv_date2.setTextSize(dateTextSize);
        tv_date3.setTextSize(dateTextSize);
        tv_date4.setTextSize(dateTextSize);
        tv_date5.setTextSize(dateTextSize);
        tv_date6.setTextSize(dateTextSize);
        tv_date7.setTextSize(dateTextSize);
        tv_date1.setTextColor(dateTextColor);
        tv_date2.setTextColor(dateTextColor);
        tv_date3.setTextColor(dateTextColor);
        tv_date4.setTextColor(dateTextColor);
        tv_date5.setTextColor(dateTextColor);
        tv_date6.setTextColor(dateTextColor);
        tv_date7.setTextColor(dateTextColor);

        tv_1.setTextSize(WeekTextSize);
        tv_2.setTextSize(WeekTextSize);
        tv_3.setTextSize(WeekTextSize);
        tv_4.setTextSize(WeekTextSize);
        tv_5.setTextSize(WeekTextSize);
        tv_6.setTextSize(WeekTextSize);
        tv_7.setTextSize(WeekTextSize);
        tv_1.setTextColor(WeekTextColor);
        tv_2.setTextColor(WeekTextColor);
        tv_3.setTextColor(WeekTextColor);
        tv_4.setTextColor(WeekTextColor);
        tv_5.setTextColor(WeekTextColor);
        tv_6.setTextColor(WeekTextColor);
        tv_7.setTextColor(WeekTextColor);

    }

    private void initView() {
        ll_1 = (LinearLayout) findViewById(ll01);
        ll_2 = (LinearLayout) findViewById(R.id.ll02);
        ll_3 = (LinearLayout) findViewById(R.id.ll03);
        ll_4 = (LinearLayout) findViewById(R.id.ll04);
        ll_5 = (LinearLayout) findViewById(R.id.ll05);
        ll_6 = (LinearLayout) findViewById(R.id.ll06);
        ll_7 = (LinearLayout) findViewById(R.id.ll07);
        tv_1 = (TextView) findViewById(R.id.w01);
        tv_2 = (TextView) findViewById(R.id.w02);
        tv_3 = (TextView) findViewById(R.id.w03);
        tv_4 = (TextView) findViewById(R.id.w04);
        tv_5 = (TextView) findViewById(R.id.w05);
        tv_6 = (TextView) findViewById(R.id.w06);
        tv_7 = (TextView) findViewById(R.id.w07);
        tv_date1 = (TextView) findViewById(R.id.d01);
        tv_date2 = (TextView) findViewById(R.id.d02);
        tv_date3 = (TextView) findViewById(R.id.d03);
        tv_date4 = (TextView) findViewById(R.id.d04);
        tv_date5 = (TextView) findViewById(R.id.d05);
        tv_date6 = (TextView) findViewById(R.id.d06);
        tv_date7 = (TextView) findViewById(R.id.d07);

        turn_back = (LinearLayout)findViewById(R.id.ll_turn);
        back = (LinearLayout)findViewById(R.id.ll_back);
        turn_back.setOnClickListener(this);
        arrow = (ImageView)findViewById(R.id.arrow);
        tvtb = (TextView)findViewById(R.id.tv_tb);
    }

    private void setLLText(LinearLayout ll, int witchDay){
        ll.setTag(witchDay);   //便于区分点击事件
        TextView tv = (TextView)ll.getChildAt(0);
        String text = WEEK_STR.get(witchDay);
        tv.setText(text);

        TextView tvDate = (TextView)ll.getChildAt(1);
        text = DATE_STR_format.get(witchDay);
        tvDate.setText(text);

    }



    @Override
    public void onClick(View v) {
        int clickNum;
        switch (v.getId()){
            case ll01:
                clickNum = (int)ll_1.getTag();
                if(listener!=null){

                    listener.onItemClick(ll_1, WEEK_STR.get(clickNum),
                            clickNum, DATE_STR_format.get(clickNum));

                }

                Selected = 0;
                clearLL(1);
                clearLL(2);
                clearLL(3);
                clearLL(4);
                clearLL(5);
                clearLL(6);
                setLL();
                Log.i("测试",clickNum+">>>"+ Selected);
                break;
            case R.id.ll02:

                clickNum = (int)ll_2.getTag();
                if(listener!=null){
                    listener.onItemClick(ll_2, WEEK_STR.get(clickNum),
                            clickNum, DATE_STR_format.get(clickNum));
                }
                Selected = 1;
                clearLL(0);
                clearLL(2);
                clearLL(3);
                clearLL(4);
                clearLL(5);
                clearLL(6);
                setLL();
                Log.i("测试",clickNum+">>>"+ Selected);
                break;
            case R.id.ll03:
                clickNum = (int)ll_3.getTag();
                if(listener!=null){
                    listener.onItemClick(ll_3, WEEK_STR.get(clickNum),
                            clickNum, DATE_STR_format.get(clickNum));
                }
                Selected = 2;
                clearLL(0);
                clearLL(1);
                clearLL(3);
                clearLL(4);
                clearLL(5);
                clearLL(6);
                setLL();
                Log.i("测试",clickNum+">>>"+ Selected);
                break;
            case R.id.ll04:
                clickNum = (int)ll_4.getTag();
                if(listener!=null){
                    listener.onItemClick(ll_4, WEEK_STR.get(clickNum),
                            clickNum, DATE_STR_format.get(clickNum));
                }
                Selected = 3;
                clearLL(0);
                clearLL(1);
                clearLL(2);
                clearLL(4);
                clearLL(5);
                clearLL(6);
                setLL();
                Log.i("测试",clickNum+">>>"+ Selected);
                break;
            case R.id.ll05:
                clickNum = (int)ll_5.getTag();
                if(listener!=null){
                    listener.onItemClick(ll_5, WEEK_STR.get(clickNum),
                            clickNum, DATE_STR_format.get(clickNum));
                }
                Selected = 4;
                clearLL(0);
                clearLL(1);
                clearLL(2);
                clearLL(3);
                clearLL(5);
                clearLL(6);
                setLL();
                Log.i("测试",clickNum+">>>"+ Selected);
                break;
            case R.id.ll06:
                clickNum = (int)ll_6.getTag();
                if(listener!=null){
                    listener.onItemClick(ll_6, WEEK_STR.get(clickNum),
                            clickNum, DATE_STR_format.get(clickNum));
                }
                Selected = 5;
                clearLL(0);
                clearLL(1);
                clearLL(2);
                clearLL(3);
                clearLL(4);
                clearLL(6);
                setLL();
                Log.i("测试",clickNum+">>>"+ Selected);
                break;
            case R.id.ll07:
                clickNum = (int)ll_7.getTag();
                if(listener!=null){
                    listener.onItemClick(ll_7, WEEK_STR.get(clickNum),
                            clickNum, DATE_STR_format.get(clickNum));
                }
                Selected = 6;
                clearLL(0);
                clearLL(1);
                clearLL(2);
                clearLL(3);
                clearLL(4);
                clearLL(5);
                setLL();
                Log.i("测试",clickNum+">>>"+ Selected);
                break;
            case R.id.ll_turn:
                isChange = true;
                if(isChange){
                    if(back.getVisibility() == VISIBLE){
                        back.setVisibility(GONE);
                        arrow.setImageResource(R.drawable.arrow_down);
                        tvtb.setText("展开");
                    }else{
                        back.setVisibility(VISIBLE);
                        arrow.setImageResource(R.drawable.packup);
                        tvtb.setText("收起");
                    }

                }

                break;
        }
    }


    public void clearLL(int i){
        LinearLayout ll = llList.get(i);
        TextView tv = (TextView)ll.getChildAt(0);
        TextView tvDate = (TextView)ll.getChildAt(1);
        ll.setBackgroundColor(background);
        tvDate.setTextColor(Color.rgb(255, 0, 0));
        tv.setTextColor(Color.rgb(255, 0, 0));

    }
    private void setLL(){
        LinearLayout ll = llList.get(Selected);
        TextView tv = (TextView)ll.getChildAt(0);
        TextView tvDate = (TextView)ll.getChildAt(1);
        ll.setBackgroundResource(selected);
        tvDate.setTextColor(Color.rgb(36, 207, 162));
        tv.setTextColor(Color.rgb(36, 207, 162));
    }



    private OnItemClickListener listener;
    public interface OnItemClickListener{

         void onItemClick(View v, String day, int dayNum, String date);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


}
