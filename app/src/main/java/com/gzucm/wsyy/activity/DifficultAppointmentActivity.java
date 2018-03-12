package com.gzucm.wsyy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzucm.wsyy.R;
import com.gzucm.wsyy.bean.Patient;
import com.gzucm.wsyy.utils.Haoutil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class DifficultAppointmentActivity extends AppCompatActivity{



    @BindView(R.id.add_none)
    TextView mnone;
    @BindView(R.id.add)
    TextView madd;
    @BindView(R.id.patient)
    LinearLayout ll;
    @BindView(R.id.p_name)
    TextView mpname;
    @BindView(R.id.p_card)
    TextView mpcard;
    @BindView(R.id.btn_commit)
    Button btn;
    @BindView(R.id.p_et)
    EditText pet;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficultappointment);
        ButterKnife.bind(this);
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
    @OnClick(R.id.add)
    void addP(){
        Intent intent = new Intent(DifficultAppointmentActivity.this,AddPatientActivity.class);
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
    @OnClick(R.id.btn_commit)
    void commit(){
        String content = pet.getText().toString();
        Intent intent = getIntent();
        String dname = intent.getStringExtra("dname");
        SharedPreferences sp = getSharedPreferences("patient",MODE_PRIVATE);
        String id = sp.getString("pid","");
        Patient patient = new Patient();
        patient.setObjectId(id);
        new Haoutil(DifficultAppointmentActivity.this).addPost(patient,content,dname);
        Intent intent2 = new Intent(DifficultAppointmentActivity.this,SelectActivity.class);
        startActivity(intent2);
    }
}
