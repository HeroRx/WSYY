package com.gzucm.wsyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzucm.wsyy.R;
import com.gzucm.wsyy.adapter.RecycleViewDivider;
import com.gzucm.wsyy.adapter.WeekDayAdapter;
import com.gzucm.wsyy.bean.BigDepartment;
import com.gzucm.wsyy.bean.Department;
import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Week;
import com.gzucm.wsyy.event.AppBarStateChangeEvent;
import com.gzucm.wsyy.utils.Dateutil;
import com.gzucm.wsyy.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class SelectedDDetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_head)
    CircleImageView civ;
    @BindView(R.id.detail_name)
    TextView dname;
    @BindView(R.id.detail_titles)
    TextView dtitles;
    @BindView(R.id.detail_special)
    TextView dspecial;
    @BindView(R.id.detail_department)
    TextView ddepartment;
    ImageLoader imageLoader;
    @BindView(R.id.rv_time)
    RecyclerView rv;
    @BindView(R.id.detail_tb)
    Toolbar toolbar;
    @BindView(R.id.user_sex)
    ImageView sex;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    List<String> datelist= new ArrayList<>();
    List<String> daylist= new ArrayList<>();

    @BindView(R.id.sfl)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isFirst=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        ButterKnife.bind(this);
        //这句就是添加我们自定义的分隔线
        rv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        imageLoader= ImageLoader.getInstance();
        datelist = new Dateutil().getSevenDate(7);
        daylist = new Dateutil().getWeek(datelist);
        getDDetail();
        findBD();
        getWeekDay();
        getData();
        initRefreshLayout();
    }
    /**
     * 首次刷新
     * @param hasFocus
     */
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
                    Thread.sleep(1500);
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

    /**
     * 刷新控件
     */
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        //给swipeRefreshLayout绑定刷新监听
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        clear();
                        findBD();
                        getDDetail();
                        getWeekDay();
                        //重置为没有刷新，即为可刷新
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    private void initAppBarLayout(final String str) {
        setSupportActionBar(toolbar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeEvent() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {
                if (state == State.EXPANDED) {
                    //展开状态
                    collapsingToolbar.setTitle("");

                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    collapsingToolbar.setTitle(str);

                } else {
                    collapsingToolbar.setTitle("");
                }
            }
        });
    }

    public void getDDetail(){
        Intent intent = getIntent();
        Doctor doctor = (Doctor)intent.getSerializableExtra("doctor");
        String d = intent.getStringExtra("mdname");

        //设置用户性别
        if (doctor.isD_sex()) {
            sex.setImageResource(R.drawable.ic_user_male);
        }else {
            sex.setImageResource(R.drawable.ic_user_female);
        }
        final String name = doctor.getD_name();
        final String title = doctor.getD_titles();
        final String special = doctor.getD_specialty();
        final String url = doctor.getD_photo().getFileUrl();
        //设置toolbar
        initAppBarLayout(name + "(" + title + ")");
        //设置性别
        dname.setText(name);
        dtitles.setText(title);
        dspecial.setText(special);
        //创建DisplayImageOptions对象并进行相关选项配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_stub)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_x)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .build();// 创建DisplayImageOptions对象
        // 利用ImageView加载图片
        imageLoader.displayImage(url,civ,options);
    }
    List<String> timelist = new ArrayList<>();
    List<String> haolist = new ArrayList<>();
    public void getWeekDay() {
        //获取具体的科室
        Intent intent = getIntent();
        Doctor doctor = (Doctor) intent.getSerializableExtra("doctor");
        String id = doctor.getObjectId();

        BmobQuery<Week> query = new BmobQuery<Week>();
        Doctor doctor1 = new Doctor();
        doctor1.setObjectId(id);
        query.addWhereEqualTo("w_doctor",new BmobPointer(doctor1));
        query.include("w_doctor");
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_THEN_NETWORK);
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        Observable<List<Week>> observable = query.findObjectsObservable(Week.class);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Week>>() {
            @Override
            public void onCompleted() {
                Log.i("日期星期","日期星期完成"+datelist+daylist);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.i("日期星期","日期星期错误"+datelist+daylist);
            }

            @Override
            public void onNext(List<Week> weeks) {

                Log.i("日期星期hhh","日期星期"+weeks);
                for(Week week:weeks){
                    for(int i = 0;i < daylist.size();i++){
                        switch (daylist.get(i)){

                            case "周一":
                                int j = daylist.indexOf("周一");
                                if(week.isW_mon()){
                                    String wc1 = week.getW_c1()+"";
                                    String time1 = datelist.get(j);
                                    timelist.add(time1);
                                    haolist.add(wc1);
                                }
                                break;
                            case "周二":
                                int j2 = daylist.indexOf("周二");
                                if(week.isW_tues()){
                                    String wc2 = week.getW_c2()+"";
                                    String time2 = datelist.get(j2);
                                    timelist.add(time2);
                                    haolist.add(wc2);
                                }
                                break;
                            case "周三":
                                int j3 = daylist.indexOf("周三");
                                if(week.isW_weds()){
                                    String wc3 = week.getW_c3()+"";
                                    String time3 = datelist.get(j3);
                                    timelist.add(time3);
                                    haolist.add(wc3);
                                }
                                break;
                            case "周四":
                                int j4 = daylist.indexOf("周四");
                                if(week.isW_thur()){
                                    String wc4 = week.getW_c4()+"";
                                    String time4= datelist.get(j4);
                                    timelist.add(time4);
                                    haolist.add(wc4);
                                }
                                break;
                            case "周五":
                                int j5 = daylist.indexOf("周五");
                                if(week.isW_fri()){
                                    String wc5 = week.getW_c5()+"";
                                    String time5 = datelist.get(j5);
                                    timelist.add(time5);
                                    haolist.add(wc5);
                                }
                                break;
                            case "周六":
                                int j6 = daylist.indexOf("周六");
                                if(week.isW_sat()){
                                    String wc6 = week.getW_c6()+"";
                                    String time6 = datelist.get(j6);
                                    timelist.add(time6);
                                    haolist.add(wc6);
                                }
                                break;
                            case "周日":
                                int j7 = daylist.indexOf("周日");
                                if(week.isW_sun()){
                                    String wc7 = week.getW_c7()+"";
                                    String time7 = datelist.get(j7);
                                    timelist.add(time7);
                                    haolist.add(wc7);
                                }
                                break;
                        }
                    }
                }
                setRv();
            }
        });
    }
    public void setRv(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectedDDetailActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        final WeekDayAdapter weekDayAdapter;
        weekDayAdapter = new WeekDayAdapter(SelectedDDetailActivity.this,timelist,haolist);
        weekDayAdapter.notifyDataSetChanged();
        weekDayAdapter.setOnItemClickListener(new WeekDayAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(SelectedDDetailActivity.this,SelectedTDuanActivity.class);
                Intent intent2 = getIntent();
                Doctor doctor = (Doctor) intent2.getSerializableExtra("doctor");
                intent.putExtra("day", daylist.get(postion));
                intent.putExtra("date",datelist.get(postion));
                intent.putExtra("doctor",doctor);
                startActivity(intent);
            }
        });
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(weekDayAdapter);
        //这句就是添加我们自定义的分隔线
        rv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
    }
    public void clear(){
        timelist.clear();
        haolist.clear();
        rv.removeAllViews();
    }


    private void findBD(){
        Intent intent = getIntent();
        final String d = intent.getStringExtra("mdname");
        Doctor doctor = (Doctor) intent.getSerializableExtra("doctor");
        Department department = doctor.getD_dcode();
        BmobQuery<Department> query = new BmobQuery<Department>();
        query.include("d_bigdepartmentcode");
        Observable<Department> observable = query.getObjectObservable(Department.class,department.getObjectId());
        observable.subscribe(new Observer<Department>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Department department) {
                BigDepartment bd = department.getD_bigdepartmentcode();
                String bdid = bd.getBd_name();
                Log.i("hh", "" + bdid + bd);

                ddepartment.setText(bdid + "--" + d);
            }
        });
    }

}
