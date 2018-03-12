package com.gzucm.wsyy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Checkable;
import android.widget.TextView;

import com.gzucm.wsyy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class PayActivity extends AppCompatActivity{
    @BindView(R.id.pay_toolbar)
    Toolbar tb;
    @BindView(R.id.alipay_cb)
    Checkable mAlipayCb;
    @BindView(R.id.wx_cb)
    Checkable mWXCb;
    @BindView(R.id.pay_count)
    TextView count;
    private static Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        initToolbar();
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        tb.setTitle("确定就诊信息");
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

    @OnClick(R.id.payment_bt)
    void pay(){

    }

}
