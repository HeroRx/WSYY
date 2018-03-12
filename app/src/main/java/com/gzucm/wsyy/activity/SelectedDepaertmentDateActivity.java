package com.gzucm.wsyy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.gzucm.wsyy.R;
import com.gzucm.wsyy.adapter.DayDoctorAdapter;
import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Week;
import com.gzucm.wsyy.utils.Dateutil;
import com.gzucm.wsyy.view.WeekSelectedView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.gzucm.wsyy.utils.Weekutil.getWeek;

/**
 *
 * Created by Administrator on 2017/11/19 0019.
 */

public class SelectedDepaertmentDateActivity extends AppCompatActivity {

    //VLjs0006
    //P2RxHHHK
    @BindView(R.id.wsv)
    WeekSelectedView wsv;
    @BindView(R.id.rv)
    RecyclerView rv;
    private Context context;
    @BindView(R.id.none)
    CardView nonecd;
    @BindView(R.id.out)
    CardView outcd;

    String mday;
    String dname;
    String bdname;
    public Context getContext() {
        context = SelectedDepaertmentDateActivity.this;
        return context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecteddepaertmentdate);
        ButterKnife.bind(this);
        initToolbar();
        clear();
        initWeek();
        getData();
    }
    /**
     * 首次刷新
     * @param hasFocus
     */
    @BindView(R.id.sfl)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isFirst=true;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        if (isFirst){
            mSwipeRefreshLayout.setRefreshing(true);
            isFirst=false;
        }
    }
    private void getData() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }.start();
    }
    protected void initRefreshLayout(final String day) {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        //给swipeRefreshLayout绑定刷新监听
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //在这里清空之前的数据
                        clear();
                        getDayDoctor(day);
                        //重置为没有刷新，即为可刷新
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });
    }
    /**
     * 判断是否为四点后
     */
    private boolean isInTime() {
        return Dateutil.isCurrentInTimeScope(16,0,0,0);
    }
    private void setTimeOut(){
        outcd.setVisibility(View.VISIBLE);
        nonecd.setVisibility(View.GONE);
        rv.removeAllViews();
    }


    /**
     * 初始化week的数据
     */
    private void initWeek() {
        //设置选中的当天
        SharedPreferences sp = getSharedPreferences("date",MODE_PRIVATE);
        String dday = sp.getString("dday","");
        final String first = wsv.getFirst();
        if(dday.isEmpty()||dday == first){
            //点击按时间挂号 获取天数
            //设置初始状态
            Log.i("第一天",first+"");
            if(isInTime()){
                setTimeOut();
            }else {
                getDayDoctor(first);
            }
        }else {
            getDayDoctor(dday);
            wsv.setSelectedDay(dday);
            sp.edit().clear().commit();
        }

        wsv.setOnItemClickListener(new WeekSelectedView.OnItemClickListener() {
            @Override
            public void onItemClick(View v, String day, int dayNum, String date) {
                //在设置之前把数据清除
                clear();
                clearView();
                //如果还是点击第一天就去刷新第一天的状态
                //然后再去加载其他天
                if(day == first){
                    if(isInTime()){
                        setTimeOut();
                    }else {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getData();
                        getDayDoctor(first);
                        initRefreshLayout(first);
                    }
                }else {
                    mSwipeRefreshLayout.setRefreshing(true);
                    getData();
                    getDayDoctor(day);
                    initRefreshLayout(day);

                }
            }
        });

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("选择医生挂号");
        setSupportActionBar(toolbar);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //医生列表
    final List<Doctor> doctorList = new ArrayList<Doctor>();
    final List<Integer> ilist = new ArrayList<Integer>();

    public void getDayDoctor(final String day){
        //获取具体的科室
        Intent intent = getIntent();
        final String did = intent.getStringExtra("did");
        getWeek(did).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Week>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Week> weeks) {
                if(weeks.isEmpty()){
                    nonecd.setVisibility(View.VISIBLE);
                }
                for(Week week:weeks){
                    //判断该医生周几是否上班
                    //周几有上班就查出该数据
                    switch (day){
                        case "周一":

                            if(week.isW_mon()){
                                Doctor doctor = week.getW_doctor();
                                doctorList.add(doctor);
                                int wc1 = week.getW_c1();
                                ilist.add(wc1);
                            }else {
                                if (outcd.getVisibility() != View.VISIBLE){
                                    nonecd.setVisibility(View.VISIBLE);
                                }
                            }
                            mday = "周一";
                            break;
                        case "周二":

                            if(week.isW_tues()){
                                Doctor doctor = week.getW_doctor();
                                doctorList.add(doctor);
                                int wc2 = week.getW_c2();
                                ilist.add(wc2);
                            }else {
                                if (outcd.getVisibility() != View.VISIBLE){
                                    nonecd.setVisibility(View.VISIBLE);
                                }
                            }
                            mday = "周二";
                            break;
                        case "周三":

                            if(week.isW_weds()){
                                Doctor doctor = week.getW_doctor();
                                doctorList.add(doctor);
                                int wc3 = week.getW_c3();
                                ilist.add(wc3);
                            }else {
                                if (outcd.getVisibility() != View.VISIBLE){
                                    nonecd.setVisibility(View.VISIBLE);
                                }
                            }
                            mday = "周三";
                            break;
                        case "周四":


                            if(week.isW_thur()){
                                Doctor doctor = week.getW_doctor();
                                doctorList.add(doctor);
                                int wc4 = week.getW_c4();
                                ilist.add(wc4);
                            }else {
                                if (outcd.getVisibility() != View.VISIBLE){
                                    nonecd.setVisibility(View.VISIBLE);
                                }
                            }
                            mday = "周四";
                            break;
                        case "周五":

                            if(week.isW_fri()){
                                Doctor doctor = week.getW_doctor();
                                doctorList.add(doctor);
                                int wc5 = week.getW_c5();
                                ilist.add(wc5);
                            }else {
                                if (outcd.getVisibility() != View.VISIBLE){
                                    nonecd.setVisibility(View.VISIBLE);
                                }
                            }
                            mday = "周五";
                            break;
                        case "周六":
                            if(week.isW_sat()){
                                Doctor doctor = week.getW_doctor();
                                doctorList.add(doctor);
                                int wc6 = week.getW_c6();
                                ilist.add(wc6);
                            }else {
                                if (outcd.getVisibility() != View.VISIBLE){
                                    nonecd.setVisibility(View.VISIBLE);
                                }
                            }
                            mday = "周六";
                            break;
                        case "周日":


                            if(week.isW_sun()){
                                Doctor doctor = week.getW_doctor();
                                doctorList.add(doctor);
                                int wc7 = week.getW_c7();
                                ilist.add(wc7);
                            }else {
                                if (outcd.getVisibility() != View.VISIBLE){
                                    nonecd.setVisibility(View.VISIBLE);
                                }
                            }
                            mday = "周日";
                            break;
                    }
                }
                Log.i("两医生",""+doctorList+ilist+""+weeks);

                setRv();
            }
        });
    }

    /**
     * 配置rv
     */
    public void setRv(){
        DayDoctorAdapter adapter;
        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectedDepaertmentDateActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter  = new DayDoctorAdapter(SelectedDepaertmentDateActivity.this,doctorList,ilist);
        Log.i("适配器",adapter+"");
        adapter.notifyDataSetChanged();
        //在这里设置rv的item点击
        adapter.setOnItemClickListener(new DayDoctorAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, final int postion) {
                //在这里先查数据去传给下一个界面就不会出现在查数据的卡顿
                final Doctor doctor = doctorList.get(postion);
                final Intent intent = new Intent(SelectedDepaertmentDateActivity.this,SelectedTimeDuanActivity.class);
                final String title = doctorList.get(postion).getD_titles();
                final String name = doctorList.get(postion).getD_name();
                final String id = doctorList.get(postion).getD_dcode().getObjectId();
                final String url = doctorList.get(postion).getD_photo().getFileUrl();
                intent.putExtra("tdid",doctorList.get(postion).getObjectId());
                intent.putExtra("day",mday);
                intent.putExtra("name",name);
                intent.putExtra("title",title);
                intent.putExtra("url",url);
                intent.putExtra("doctor",doctor);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
    }
    public void clear(){
        doctorList.clear();
        rv.removeAllViews();
        ilist.clear();

    }
    public void clearView(){
        nonecd.setVisibility(View.GONE);
        outcd.setVisibility(View.GONE);
    }
    // 捕获返回键的方法2
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences sp = getSharedPreferences("date",MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
