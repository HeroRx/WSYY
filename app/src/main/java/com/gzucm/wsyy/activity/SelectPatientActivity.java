package com.gzucm.wsyy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gzucm.wsyy.R;
import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Patient;
import com.gzucm.wsyy.bean.TimeDuan;
import com.gzucm.wsyy.utils.Haoutil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gzucm.wsyy.R.id.patient;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class SelectPatientActivity extends AppCompatActivity {

    @BindView(R.id.sp_money)
    TextView mmoney;
    @BindView(R.id.sp_keshi)
    TextView mks;
    @BindView(R.id.sp_name)
    TextView mname;
    @BindView(R.id.sp_time)
    TextView mtime;
    @BindView(R.id.add_none)
    TextView mnone;
    @BindView(R.id.add)
    TextView madd;
    @BindView(R.id.sp_toolbar)
    Toolbar tb;
    @BindView(patient)
    LinearLayout ll;
    @BindView(R.id.p_name)
    TextView mpname;
    @BindView(R.id.p_card)
    TextView mpcard;
    @BindView(R.id.payment_bt)
    Button mhao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_patient);
        ButterKnife.bind(this);
        initToolbar();
        getData();
        initSelected();

    }

    /**
     * 设置绑定的患者
     */
    private void initSelected() {
        SharedPreferences sp = getSharedPreferences("patient",MODE_PRIVATE);
        String id = sp.getString("pid","");
        String name = sp.getString("pname","");
        String card = sp.getString("pcard","");
        if(!id.isEmpty()){
            mnone.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            mpcard.setText(card);
            mpname.setText(name);
        }else {
            mnone.setVisibility(View.VISIBLE);
            ll.setVisibility(View.GONE);
        }
    }

    /**
     * 设置页面数据
     */
    private void getData() {
        Intent intent = getIntent();
        String money = intent.getStringExtra("money");
        String mbdname = intent.getStringExtra("mbdname");
        String mdname = intent.getStringExtra("mdname");
        mmoney.setText(money);
        mks.setText(mbdname + " - " + mdname);

        String date = intent.getStringExtra("date");
        TimeDuan timeduan = (TimeDuan) intent.getSerializableExtra("timeduan");
        Doctor doctor = (Doctor) intent.getSerializableExtra("doctor");
        String end = timeduan.getT_start();
        String start = timeduan.getT_end();
        mname.setText(doctor.getD_name());
        mtime.setText(date+" " + start + "-" + end);
    }

    List<String> weekid=new ArrayList<>();
    @OnClick(R.id.payment_bt)
    void guahao(){
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        final String msday = intent.getStringExtra("day");
        final String mdayid = intent.getStringExtra("dayid");
        final Doctor doctor = (Doctor) intent.getSerializableExtra("doctor");
        final SharedPreferences sp = getSharedPreferences("patient",MODE_PRIVATE);
        String id = sp.getString("pid","");
        final Patient patient = new Patient();
        patient.setObjectId(id);
        final String mrelation = sp.getString("pbr","");
        String p = sp.getString("pname","");
        String d = doctor.getD_name();
        //患者不能为空
        if(id.isEmpty()){
            Toast.makeText(SelectPatientActivity.this, "患者不能为空,请您绑定患者", Toast.LENGTH_SHORT).show();
        }else{
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("请您确定挂号信息");
            builder.setItems(new String[]{"就诊人："+p, "所挂医生："+d,"就诊时间："+date+" "+msday}, null);
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(SelectPatientActivity.this, "取消挂号成功", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pay();
                    //如果支付成功就挂号
                    //挂号
                    new Haoutil(SelectPatientActivity.this).addAppoint(patient, doctor, msday, mdayid,mrelation);
                    //更新两张表
                    new Haoutil(SelectPatientActivity.this).updateHao(msday,mdayid);
                    new Haoutil(SelectPatientActivity.this).updateWeek(msday,doctor.getObjectId());
                    sp.edit().putString("mhd",doctor.getD_name()).commit();
                    Intent mintent = new Intent(SelectPatientActivity.this,SelectActivity.class);
                    startActivity(mintent);
                }
            });
            builder.show();
        }
        Log.i("Week更新挂号", "id" + weekid);


    }
    private void pay() {
        Toast.makeText(SelectPatientActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
    }


    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        tb.setTitle("选择就诊人");
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
    @OnClick(R.id.add)
    void addP(){
        Intent intent = new Intent(SelectPatientActivity.this,AddPatientActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    //接收下一个活动返回数据,设置给已绑定的患者
                    String name = data.getStringExtra("pname");
                    String card = data.getStringExtra("pcard");
                    mnone.setVisibility(View.GONE);
                    ll.setVisibility(View.VISIBLE);
                    mpcard.setText(card);
                    mpname.setText(name);
                }
                break;
            default:
        }
    }
}
