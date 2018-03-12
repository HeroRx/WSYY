package com.gzucm.wsyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gzucm.wsyy.R;
import com.gzucm.wsyy.adapter.MostLeftAdapter;
import com.gzucm.wsyy.adapter.MostRightAdapter;
import com.gzucm.wsyy.bean.BigDepartment;
import com.gzucm.wsyy.bean.Department;
import com.gzucm.wsyy.utils.GetDepartmentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * Created by Administrator on 2017/11/14 0014.
 */

public class SelectDepartmentActivity extends AppCompatActivity{


    @BindView(R.id.left_list)
    RecyclerView leftrv;
    @BindView(R.id.right_list)
    RecyclerView rightrv;
    @BindView(R.id.sfl)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsRefreshing = false;

    private MostLeftAdapter leftAdapter;
    private MostRightAdapter rightAdapter;
    List<String> bdnames = new ArrayList<String>();
    List<String> bdids = new ArrayList<String>();
    List<String> dnames = new ArrayList<String>();
    List<String> dids = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_department);
        ButterKnife.bind(this);
        setLeftName();
        getData();
    }
    /**
     * 首次刷新
     * @param hasFocus
     */
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

    /**
     * 设置左边的数据源
     */
    private void setLeftName() {

        new GetDepartmentUtil().getBD().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<BigDepartment>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<BigDepartment> bigDepartments) {

                for(BigDepartment bigDepartment:bigDepartments){
                    String name = bigDepartment.getBd_name();
                    String id = bigDepartment.getObjectId();
                    bdnames.add(name);
                    bdids.add(id);
                }

                Log.i("数据",bdnames + "");
                Log.i("数据",bdids.get(0) + "");
                Log.i("数据",bdids + "");
                LinearLayoutManager layoutManager = new LinearLayoutManager(SelectDepartmentActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                leftrv.setLayoutManager(layoutManager);
                leftAdapter = new MostLeftAdapter(SelectDepartmentActivity.this,bdnames);
                leftrv.setAdapter(leftAdapter);

                mSwipeRefreshLayout.measure(0,0);
                initRefreshLayout(0);
                //默认根据left的第一项数据去加载右边得数据
                setRightData(0);
                if(!mIsRefreshing){
                    initRefreshLayout(0);
                }
                leftAdapter.notifyDataSetChanged();
                leftAdapter.setOnItemClickListener(new MostLeftAdapter.MyItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        clearData();
                        //设置position，根据position的状态刷新
                        leftAdapter.setPosition(position);
                        leftAdapter.notifyDataSetChanged();
//                        //在这里清空之前的数据
//                        clearData();
                        //加载当前界面完成

                        setRightData(position);
                        //如果还想继续刷新该页
                        if(!mIsRefreshing){
                            //刷新后重新置为没有刷新
                            initRefreshLayout(position);
                        }
                    }
                });
            }
        });

    }

    private void setRightData(final int i) {

        new GetDepartmentUtil().getD(bdids.get(i)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Department>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Department> departments) {

                for(Department department:departments){
                    String name = department.getD_departmentname();
                    String id = department.getObjectId();
                    dnames.add(name);
                    dids.add(id);
                }
                Log.i("数据",dnames + "");
                Log.i("数据",dids + "");
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(SelectDepartmentActivity.this);
                layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                rightrv.setLayoutManager(layoutManager1);
                rightAdapter = new MostRightAdapter(SelectDepartmentActivity.this,dnames);
//                rightrv.removeAllViews();
//                rightAdapter.notifyDataSetChanged();
                rightrv.setAdapter(rightAdapter);
                rightAdapter.setOnItemClickListener(new MostRightAdapter.MyItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("星期","星期星期星期星期" + dids.get(position) + dnames.get(position));
                        //跳转到星期选择界面
                        Intent intent = new Intent(SelectDepartmentActivity.this,SelectedDepaertmentDateActivity.class);
                        intent.putExtra("did",dids.get(position));
                        startActivity(intent);
                    }
                });

            }
        });
    }


    protected void initRefreshLayout(final int position) {
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
                        clearData();
                        //清空数据后还得传入该页的position
                        leftAdapter.notifyDataSetChanged();
                        leftAdapter.setPosition(position);
                        leftAdapter.notifyDataSetChanged();
                        //此时才刷新右边列表
                        setRightData(position);
                        rightAdapter.notifyDataSetChanged();
                        //重置为没有刷新，即为可刷新
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }


    /**
     * 清除数据
     */
    private void clearData() {
        dnames.clear();
        rightrv.removeAllViews();
        //每次点击大科都去清除下小科数组的数据，这样才能一一对应
        //不然就会重复或者是追加
        dids.clear();
    }

}
