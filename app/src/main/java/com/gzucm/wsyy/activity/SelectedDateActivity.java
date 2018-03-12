package com.gzucm.wsyy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gzucm.wsyy.R;
import com.gzucm.wsyy.adapter.DateAdapter;
import com.gzucm.wsyy.adapter.RecycleViewDivider;
import com.gzucm.wsyy.utils.Dateutil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class SelectedDateActivity extends AppCompatActivity {
    @BindView(R.id.date_toolbar)
    Toolbar tb;
    @BindView(R.id.rv)
    RecyclerView rv;
    private static List<String> WEEK_STR;
    //当前一周7天的日期,这个是已经知道的
    private static List<String> DATE_STR;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);
        ButterKnife.bind(this);
        initToolbar();
        //这句就是添加我们自定义的分隔线
        rv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        setRv();
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        tb.setTitle("选择就诊日期");
        setSupportActionBar(tb);
        //关键下面两句话，设置了回退按钮，及点击事件的效果
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setRv() {
        DATE_STR = new Dateutil().getSevenDate(7);
        WEEK_STR = new Dateutil().getWeek(DATE_STR);
        LinearLayoutManager layoutManager = new LinearLayoutManager(SelectedDateActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DateAdapter dateAdapter;
        dateAdapter = new DateAdapter(SelectedDateActivity.this, DATE_STR, WEEK_STR);
        dateAdapter.notifyDataSetChanged();
        dateAdapter.setOnItemClickListener(new DateAdapter.MyItemClickListener() {

            @Override
            public void onItemClick(View view, int postion) {
                String day = WEEK_STR.get(postion);
                SharedPreferences sp = getSharedPreferences("date",MODE_PRIVATE);
                sp.edit().putString("dday",day).commit();
                Intent intent = new Intent(SelectedDateActivity.this, SelectDepartmentActivity.class);
                startActivity(intent);
            }
        });
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(dateAdapter);
    }


}
