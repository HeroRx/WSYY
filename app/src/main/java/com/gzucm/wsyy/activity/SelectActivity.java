package com.gzucm.wsyy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.gzucm.wsyy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/18 0018.
 */

public class SelectActivity extends AppCompatActivity{

    @BindView(R.id.cv01)
    CardView cv01;
    @BindView(R.id.cv02)
    CardView cv02;
    @BindView(R.id.cv03)
    CardView cv03;
    @BindView(R.id.cv04)
    CardView cv04;
    @BindView(R.id.cv05)
    CardView cv05;
    @BindView(R.id.cv06)
    CardView cv06;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_homepage);
        ButterKnife.bind(this);//绑定framgent
    }

    @OnClick(R.id.cv01)
    public void click01(View view) {
        Intent intent = new Intent(SelectActivity.this,SelectDepartmentActivity.class);
        startActivity(intent);

    }
    @OnClick(R.id.cv02)
    public void click02(View view) {
        Intent intent = new Intent(SelectActivity.this,SelectedDateActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.cv03)
    public void click03(View view) {
        Intent intent = new Intent(SelectActivity.this,SearchDoctorActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.cv04)
    public void click04(View view) {
        Intent intent = new Intent(SelectActivity.this, DifficultConsultationActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.cv05)
    public void click05(View view) {

    }
    @OnClick(R.id.cv06)
    public void click06(View view) {

    }
}
