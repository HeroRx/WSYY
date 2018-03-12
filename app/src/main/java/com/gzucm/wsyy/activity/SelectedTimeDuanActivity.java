package com.gzucm.wsyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gzucm.wsyy.R;
import com.gzucm.wsyy.adapter.RecycleViewDivider;
import com.gzucm.wsyy.adapter.TimeDuanAdapter;
import com.gzucm.wsyy.bean.BigDepartment;
import com.gzucm.wsyy.bean.Department;
import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Fri;
import com.gzucm.wsyy.bean.Mon;
import com.gzucm.wsyy.bean.Sat;
import com.gzucm.wsyy.bean.Sun;
import com.gzucm.wsyy.bean.Thur;
import com.gzucm.wsyy.bean.TimeDuan;
import com.gzucm.wsyy.bean.Tues;
import com.gzucm.wsyy.bean.Weds;
import com.gzucm.wsyy.utils.Dateutil;
import com.gzucm.wsyy.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.gzucm.wsyy.utils.Friutil.getAllFri;
import static com.gzucm.wsyy.utils.Monutil.getAllMon;
import static com.gzucm.wsyy.utils.Satutil.getAllSat;
import static com.gzucm.wsyy.utils.Sunutil.getAllSun;
import static com.gzucm.wsyy.utils.Thurutil.getAllThur;
import static com.gzucm.wsyy.utils.Tuesutil.getAllTues;
import static com.gzucm.wsyy.utils.Wedsutil.getAllWeds;


/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class SelectedTimeDuanActivity extends AppCompatActivity{

    @BindView(R.id.td_riv)
    RoundImageView riv;
    @BindView(R.id.td_tvname)
    TextView tdname;
    @BindView(R.id.td_tvtitle)
    TextView tdtitle;
    @BindView(R.id.td_tvbd)
    TextView tdbd;
    @BindView(R.id.td_tvd)
    TextView tdd;
    private ImageLoader imageLoader;
    @BindView(R.id.tv_date)
    TextView date;
    @BindView(R.id.iv_left)
    ImageView leftImageView;
    @BindView(R.id.iv_right)
    ImageView rightImageView;
    @BindView(R.id.ra)
    LinearLayout rarrow;
    @BindView(R.id.rv_timeduan)
    RecyclerView rvtimeduan;
    @BindView(R.id.none)
    FrameLayout fl;
    @BindView(R.id.timeout)
    FrameLayout out;
    private static List<String> WEEK_STR;
    //当前一周7天的日期,这个是已经知道的
    private static List<String> DATE_STR;
    @BindView(R.id.sfl)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isFirst=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader=ImageLoader.getInstance();
        setContentView(R.layout.activity_selectedtimeduan);
        ButterKnife.bind(this);
        //这句就是添加我们自定义的分隔线
        rvtimeduan.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        initToolbar();

        setData();
        setDName();
        initDateSelect();
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
                        setDName();
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
                        setDName();
                        setData();
                        initDateSelect();
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
        out.setVisibility(View.VISIBLE);
        rvtimeduan.removeAllViews();
    }

    /**
     * 星期选择器选中某天 对应显示某一天并且将数据初始化
     */
    private String selected;
    private void initDateSelect() {
        DATE_STR = new Dateutil().getSevenDate(7);
        WEEK_STR = new Dateutil().getWeek(DATE_STR);
        Intent intent = getIntent();
        final String day = intent.getStringExtra("day");
        int index = WEEK_STR.indexOf(day);
        String d = DATE_STR.get(index);
        selected = day;
        date.setText(d+selected);
        getDay(day);
    }

    @OnClick(R.id.iv_left)
    public void left(){
        clear();
        int index = WEEK_STR.indexOf(selected);
        //如果等于1,说明要去跳转到第一天
        if(index == 1){
            if(isInTime()){
                setTimeOut();
                fl.setVisibility(View.GONE);
                String d = DATE_STR.get(0);
                String day = WEEK_STR.get(0);
                selected = day;
                date.setText(d+day);
            }else{
                String d = DATE_STR.get(0);
                String day = WEEK_STR.get(0);
                selected = day;
                date.setText(d+day);
                rvtimeduan.setVisibility(View.VISIBLE);
                fl.setVisibility(View.GONE);
                getDay(day);
            }
        }else {
            //如果等于0,第一页点击左边显示没数据并且设置为不可点击
            if(index == 0){
                Toast.makeText(SelectedTimeDuanActivity.this,"请您选择其他日期",Toast.LENGTH_SHORT).show();
                date.setText("没有更多数据了");
                fl.setVisibility(View.VISIBLE);
                out.setVisibility(View.GONE);
                rvtimeduan.setVisibility(View.GONE);
                leftImageView.setEnabled(false);
            }else{
                rightImageView.setEnabled(true);
                //当当前index=6,判断是没有数据状态还是有数据状态
                //没有更多数据了状态
                if(rvtimeduan.getVisibility() == View.GONE){
                    leftImageView.setEnabled(true);
                    rvtimeduan.setVisibility(View.VISIBLE);
                    fl.setVisibility(View.GONE);
                    out.setVisibility(View.GONE);
                    String d = DATE_STR.get(index);
                    String day = WEEK_STR.get(index);
                    selected = day;
                    date.setText(d+selected);
                    getDay(day);
                }else {
                    String d = DATE_STR.get(index - 1);
                    String day = WEEK_STR.get(index - 1);
                    selected = day;
                    date.setText(d+selected);
                    rvtimeduan.setVisibility(View.VISIBLE);
                    fl.setVisibility(View.GONE);
                    out.setVisibility(View.GONE);
                    getDay(day);
                }
            }
        }

    }
    @OnClick(R.id.iv_right)
    public void right(){
        clear();
        int index2 = WEEK_STR.indexOf(selected);
        if(index2 == 6){
            Toast.makeText(SelectedTimeDuanActivity.this,"请您选择其他日期",Toast.LENGTH_SHORT).show();
            date.setText("没有更多数据了");
            fl.setVisibility(View.VISIBLE);
            rvtimeduan.setVisibility(View.GONE);
            out.setVisibility(View.GONE);
            rightImageView.setEnabled(false);
        }else{
            leftImageView.setEnabled(true);
            //当当前index=6,判断是没有数据状态还是有数据状态
            //没有更多数据了状态
            if(rvtimeduan.getVisibility() == View.GONE){
                rightImageView.setEnabled(true);
                if(index2 == 0){
                    if(isInTime()){
                        setTimeOut();
                        fl.setVisibility(View.GONE);
                        String d = DATE_STR.get(0);
                        String day = WEEK_STR.get(0);
                        selected = day;
                        date.setText(d+day);
                    }else {
                        rvtimeduan.setVisibility(View.VISIBLE);
                        fl.setVisibility(View.GONE);
                        out.setVisibility(View.GONE);
                        String d = DATE_STR.get(index2);
                        String day = WEEK_STR.get(index2);
                        selected = day;
                        date.setText(d+selected);
                        getDay(day);
                    }
                }
                rvtimeduan.setVisibility(View.VISIBLE);
            }else {
                String d = DATE_STR.get(index2 + 1);
                String day = WEEK_STR.get(index2 + 1);
                selected = day;
                date.setText(d+selected);
                rvtimeduan.setVisibility(View.VISIBLE);
                fl.setVisibility(View.GONE);
                out.setVisibility(View.GONE);
                getDay(day);
            }
        }
    }


    Doctor mDoctor;
    public void setData(){
        Intent intent = getIntent();
        mDoctor = (Doctor) intent.getSerializableExtra("doctor");
        final String name = intent.getStringExtra("name");
        final String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        tdname.setText(name);
        tdtitle.setText(title);
        //创建DisplayImageOptions对象并进行相关选项配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_stub)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_x)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .build();// 创建DisplayImageOptions对象
        // 利用ImageView加载图片
        imageLoader.displayImage(url,riv,options);
    }
    String mbdname;
    String mdname;
    public void setDName(){
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        //可能因为这里查询所以就在这里跳转页面有点卡
        BmobQuery<Department> query = new BmobQuery<Department>();
        query.include("d_bigdepartmentcode");
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        Observable<Department> observable = query.getObjectObservable(Department.class,id);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Department>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Department department) {
                        mdname = department.getD_departmentname();
                        BigDepartment bd = department.getD_bigdepartmentcode();
                        mbdname= bd.getBd_name();
                        Log.i("查到的",mdname+mbdname+"");
                        tdbd.setText(mbdname);
                        tdd.setText(mdname);
                    }
                });
    }

    /**
     * 查询医生的当天所有可以挂号时间段
     * @return
     */
    public void getDay(String day) {
        Intent intent = getIntent();
        final String did = intent.getStringExtra("tdid");
        switch (day){
            case "周一":
                setMon(did);
                break;
            case "周二":
                setTue(did);
                break;
            case "周三":
                setWeds(did);
                break;
            case "周四":
                setThur(did);
                break;
            case "周五":
                setFri(did);
                break;
            case "周六":
                setSat(did);
                break;
            case "周日":
                setSun(did);
                break;

        }
    }

    //挂号详情
    final List<String> moneyList = new ArrayList<String>();
    final List<String> urlList = new ArrayList<String>();
    final List<Integer> countList = new ArrayList<Integer>();
    final List<TimeDuan> timeDuanList = new ArrayList<TimeDuan>();
    //具体挂的某一天的一条记录的id
    final List<String> dayid = new ArrayList<String>();

    /**
     * 观察者获取被观察者的数据 周一
     * @param did
     */
    public  void setMon(String did){
        getAllMon(did)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Mon>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<Mon> mons) {

                        for(Mon mon:mons){
                            Doctor doctor = mon.getM_d();
                            TimeDuan timeDuan = mon.getM_t();
                            int count = mon.getM_count();
                            countList.add(count);
                            String url = timeDuan.getTd_icon().getFileUrl();
                            String money = doctor.getD_money()+"";
                            String id = mon.getObjectId();
                            dayid.add(id);
                            moneyList.add(money);
                            urlList.add(url);
                            timeDuanList.add(timeDuan);
                            Log.i("医生挂号详情-->>链接",moneyList+"" +urlList);
                        }
                        setRv();
                    }
                });
    }
    /**
     * 观察者获取被观察者的数据 周二
     * @param did
     */
    public void setTue(String did){

        getAllTues(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Tues>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Tues> tues) {

                for(Tues tue:tues){
                    Doctor doctor = tue.getT_d();
                    TimeDuan timeDuan = tue.getT_t();
                    int count = tue.getT_count();
                    countList.add(count);
                    String url = timeDuan.getTd_icon().getFileUrl();
                    String money = doctor.getD_money()+"";
                    String id = tue.getObjectId();
                    dayid.add(id);
                    moneyList.add(money);
                    urlList.add(url);
                    timeDuanList.add(timeDuan);
                    Log.i("医生挂号详情-->>链接",moneyList+"" +urlList);
                }
                setRv();
            }
        });
    }
    /**
     * 观察者获取被观察者的数据 周三
     * @param did
     */
    public void setWeds(String did){
        getAllWeds(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Weds>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Weds> weds) {

                for(Weds wed:weds){
                    Doctor doctor = wed.getW_d();
                    TimeDuan timeDuan = wed.getW_t();
                    int count = wed.getW_count();
                    countList.add(count);
                    String url = timeDuan.getTd_icon().getFileUrl();
                    String money = doctor.getD_money()+"";
                    String id = wed.getObjectId();
                    dayid.add(id);
                    moneyList.add(money);
                    urlList.add(url);
                    timeDuanList.add(timeDuan);
                    Log.i("医生挂号详情-->>链接",moneyList+"" +urlList);
                }
                setRv();
            }
        });
    }
    /**
     * 观察者获取被观察者的数据 周四
     * @param did
     */
    public  void setThur(String did){
        getAllThur(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Thur>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Thur> thurs) {

                for(Thur thur:thurs){
                    Doctor doctor = thur.getTh_d();
                    TimeDuan timeDuan = thur.getTh_t();
                    int count = thur.getTh_count();
                    countList.add(count);
                    String url = timeDuan.getTd_icon().getFileUrl();
                    String money = doctor.getD_money()+"";
                    String id = thur.getObjectId();
                    dayid.add(id);
                    moneyList.add(money);
                    urlList.add(url);
                    timeDuanList.add(timeDuan);
                    Log.i("医生挂号详情-->>链接",moneyList+"" +urlList);
                }
               setRv();
            }
        });
    }
    /**
     * 观察者获取被观察者的数据 周五
     * @param did
     */
    public  void setFri(String did){
        getAllFri(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Fri>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Fri> fris) {

                for(Fri fri:fris){
                    Doctor doctor = fri.getF_d();
                    TimeDuan timeDuan = fri.getF_t();
                    int count = fri.getF_count();
                    countList.add(count);
                    String url = timeDuan.getTd_icon().getFileUrl();
                    String money = doctor.getD_money()+"";
                    String id = fri.getObjectId();
                    dayid.add(id);
                    moneyList.add(money);
                    urlList.add(url);
                    timeDuanList.add(timeDuan);
                    Log.i("医生挂号详情-->>链接",moneyList+"" +urlList);
                }
                setRv();
            }
        });
    }
    /**
     * 观察者获取被观察者的数据 周六
     * @param did
     */
    public  void setSat(String did){
        getAllSat(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Sat>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Sat> sats){

                for(Sat sat:sats){
                    Doctor doctor = sat.getS_d();
                    TimeDuan timeDuan = sat.getS_t();
                    int count = sat.getS_count();
                    countList.add(count);
                    String url = timeDuan.getTd_icon().getFileUrl();
                    String money = doctor.getD_money()+"";
                    String id = sat.getObjectId();
                    dayid.add(id);
                    moneyList.add(money);
                    urlList.add(url);
                    timeDuanList.add(timeDuan);
                    Log.i("医生挂号详情-->>链接",moneyList+"" +urlList);
                }
                setRv();
            }
        });
    }
    /**
     * 观察者获取被观察者的数据 周日
     * @param did
     */
    public  void setSun(String did){
        getAllSun(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Sun>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Sun> suns) {

                for(Sun sun:suns){
                    Doctor doctor = sun.getSu_d();
                    TimeDuan timeDuan = sun.getSu_t();
                    int count = sun.getSu_count();
                    countList.add(count);
                    String url = timeDuan.getTd_icon().getFileUrl();
                    String money = doctor.getD_money()+"";
                    String id = sun.getObjectId();
                    dayid.add(id);
                    moneyList.add(money);
                    urlList.add(url);
                    timeDuanList.add(timeDuan);
                    Log.i("医生挂号详情-->>链接",moneyList+"" +urlList);
                }
                setRv();
            }
        });
    }


    public void setRv(){
        setDName();
        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectedTimeDuanActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        TimeDuanAdapter timeDuanAdapter;
        timeDuanAdapter = new TimeDuanAdapter(SelectedTimeDuanActivity.this,urlList,moneyList,countList);
        timeDuanAdapter.notifyDataSetChanged();
        timeDuanAdapter.setOnItemClickListener(new TimeDuanAdapter.MyItemClickListener(){

            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(SelectedTimeDuanActivity.this,SelectPatientActivity.class);
                setData();
                intent.putExtra("doctor", mDoctor);
                intent.putExtra("mbdname",mbdname);
                intent.putExtra("mdname",mdname);
                intent.putExtra("money",moneyList.get(postion));
                int index = WEEK_STR.indexOf(selected);
                String date = DATE_STR.get(index);
                intent.putExtra("date",date);
                intent.putExtra("day",selected);
                intent.putExtra("timeduan",timeDuanList.get(postion));
                intent.putExtra("dayid",dayid.get(postion));
                //在这里要传预约的时间
                startActivity(intent);
            }
        });
        rvtimeduan.setLayoutManager(layoutManager);
        rvtimeduan.setAdapter(timeDuanAdapter);
        //这句就是添加我们自定义的分隔线
//        rvtimeduan.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
    }
    public void clear(){
        urlList.clear();
        moneyList.clear();
        countList.clear();
        rvtimeduan.removeAllViews();
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.td_toolbar);
        toolbar.setTitle("选择时间段挂号");
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

    @OnClick(R.id.ra)
    public void rarrow(){
        setData();
        Intent intent = new Intent(SelectedTimeDuanActivity.this,SelectedDoctorDetailActivity.class);
        intent.putExtra("doctor", mDoctor);
        intent.putExtra("mbdname",mbdname);
        intent.putExtra("mdname",mdname);
        Log.i("名字","" + mDoctor+mbdname);
        startActivityForResult(intent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    clear();
                    mSwipeRefreshLayout.setRefreshing(true);
                    String mday = data.getStringExtra("day");
                    String mdate = data.getStringExtra("date");
                    date.setText(mdate+mday);
                    setData();
                    setDName();
                    getDay(mday);
                    Log.i("最新数据",""+urlList+countList+moneyList+"");
                    getData();
                }
                break;
            default:
        }
    }



}
