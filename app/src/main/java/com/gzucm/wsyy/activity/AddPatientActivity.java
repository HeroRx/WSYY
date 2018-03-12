package com.gzucm.wsyy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.gzucm.wsyy.R;
import com.gzucm.wsyy.bean.Patient;
import com.gzucm.wsyy.utils.IDutil;
import com.gzucm.wsyy.utils.Patientutil;
import com.gzucm.wsyy.utils.Phoneutil;
import com.gzucm.wsyy.view.XCDropDownListView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class AddPatientActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    public static final String DATEPICKER_TAG = "datepicker";
    @BindView(R.id.br)
    XCDropDownListView br;
    @BindView(R.id.sfz)
    XCDropDownListView sfz;
    @BindView(R.id.sp_name)
    EditText etname;
    @BindView(R.id.sp_phone)
    EditText etphone;
    @BindView(R.id.sp_idcard)
    EditText etidcard;
    @BindView(R.id.sp_card)
    EditText etcard;
    @BindView(R.id.btn_commit)
    Button commit;
    @BindView(R.id.sp_date)
    TextView tvdate;
    @BindView(R.id.add_toolbar)
    Toolbar tb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        ButterKnife.bind(this);
        initToolbar();
        initXCDropDownListView();
        initClick();
    }
    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        tb.setTitle("添加就诊人");
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

    String name;
    String phone;
    String idcard;
    String card;
    @OnClick(R.id.btn_commit)
    void commit(){
        //获取键盘输入
        name = etname.getText().toString();
        phone = etphone.getText().toString();
        idcard = etidcard.getText().toString();
        card = etcard.getText().toString();
        if (name.isEmpty()){
            Toast.makeText(AddPatientActivity.this,"名字不能为空",Toast.LENGTH_SHORT).show();
        }else if(phone.isEmpty()){
            Toast.makeText(AddPatientActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
        }else if(idcard.isEmpty()){
            Toast.makeText(AddPatientActivity.this,"身份证不能为空",Toast.LENGTH_SHORT).show();
        }else if(card.isEmpty()){
            Toast.makeText(AddPatientActivity.this,"诊疗卡号不能为空",Toast.LENGTH_SHORT).show();
        }else if(tvdate.getText().equals("请选择")){
            Toast.makeText(AddPatientActivity.this,"出生日期必须选择",Toast.LENGTH_SHORT).show();
        }else{
            if (!Phoneutil.isMobileNO(phone)){
                Toast.makeText(AddPatientActivity.this,"输入的手机号码有误",Toast.LENGTH_SHORT).show();
            }
            IDutil cc = new IDutil();
            String isID = null;
            try {
                isID = cc.IDCardValidate(idcard);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(!isID.isEmpty()){
                Toast.makeText(AddPatientActivity.this,""+isID,Toast.LENGTH_SHORT).show();
            }
        }
        findPatient(Integer.parseInt(card));
        Log.i("数据",date+name+phone+idcard+card+mbr+msfz+""+mPatients);


    }

    String mbr = "本人";
    String msfz = "身份证";
    /**
     * 两人下拉框的回调
     */
    private void initClick() {
        br.setOnItemClickListener(new XCDropDownListView.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                String br = brlist.get(postion);
                mbr = brlist.get(postion);
//                Toast.makeText(AddPatientActivity.this,""+br,Toast.LENGTH_SHORT).show();
                Log.i("数据",br+"");
            }
        });
        sfz.setOnItemClickListener(new XCDropDownListView.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                String sfz = sfzlist.get(postion);
                msfz = sfzlist.get(postion);
//                Toast.makeText(AddPatientActivity.this,""+sfz,Toast.LENGTH_SHORT).show();
                Log.i("数据",sfz+"");
            }
        });
    }

    ArrayList<String> brlist = new ArrayList<String>();
    ArrayList<String> sfzlist = new ArrayList<String>();
    /**
     * 初始化下拉框数据
     */
    private void initXCDropDownListView() {
        brlist.add(0,"本人");
        brlist.add(1,"父母");
        brlist.add(2,"子女");
        brlist.add(3,"兄弟");
        brlist.add(4,"姐妹");
        brlist.add(5,"伴侣");
        brlist.add(6,"其他");
        sfzlist.add(0,"身份证");
        sfzlist.add(1,"港澳台身份证");
        sfzlist.add(2,"护照");
        br.setItemsData(brlist);
        sfz.setItemsData(sfzlist);
    }

    /**
     * 日历控件
     */
    @OnClick(R.id.sp_date)
    void initDate() {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        datePickerDialog.setYearRange(1903, 2028);
        datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
    }
    String date;
    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        int truemonth = month + 1;
        date = year + "-" + truemonth + "-" + day;
        tvdate.setTextColor(getResources().getColor(R.color.main_color));
        tvdate.setText(date);
        Toast.makeText(AddPatientActivity.this, "new date:" + date, Toast.LENGTH_LONG).show();
    }

    List<Patient> mPatients = new ArrayList<>();
    private void findPatient(final int card){
        new Patientutil().getPatient(card).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Patient>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.i("填入数据",""+throwable);
                        if(throwable != null){
                            Toast.makeText(AddPatientActivity.this,"找不到此患者,请到医院办理诊疗卡",Toast.LENGTH_SHORT).show();
                            clearText();
                        }
                    }

                    @Override
                    public void onNext(List<Patient> patients) {
                        for (Patient patient : patients) {
                            String mname = patient.getP_name();
                            String midcard = patient.getP_idcard();
                            String mbirth = patient.getP_birth();
                            String pid = patient.getObjectId();
                            Log.i("填入数据",""+name+mname+idcard+midcard+date);
                            //第一次为空
                            SharedPreferences sp = getSharedPreferences("patient",MODE_PRIVATE);
                            String id = sp.getString("pid","");
                            if(id.isEmpty() || !id.equals(pid)){
                                if(!name.equals(mname)){
                                    Toast.makeText(AddPatientActivity.this,"您的姓名信息与在医院记录的信息不一致,请重新填写",Toast.LENGTH_SHORT).show();
                                }else if(!idcard.equals(midcard)){
                                    Toast.makeText(AddPatientActivity.this,"您的身份证信息与在医院记录的信息不一致,请重新填写",Toast.LENGTH_SHORT).show();
                                }else if(!date.equals(mbirth)){
                                    Toast.makeText(AddPatientActivity.this,"您的出生日期信息与在医院记录的信息不一致,请重新填写",Toast.LENGTH_SHORT).show();
                                }else {
                                    Intent intent = new Intent();
                                    intent.putExtra("pname", mname);
                                    intent.putExtra("pcard", String.valueOf(card));
                                    setResult(RESULT_OK, intent);
                                    sp.edit().putString("pid",pid).commit();
                                    sp.edit().putString("pname",mname).commit();
                                    sp.edit().putString("pcard", String.valueOf(card)).commit();
                                    sp.edit().putString("pbr", mbr).commit();
                                    Toast.makeText(AddPatientActivity.this,"您已经成功绑定该患者",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }else {
                                Toast.makeText(AddPatientActivity.this,"您已经绑定该患者,请勿重复绑定",Toast.LENGTH_SHORT).show();
                                clearText();

                            }
                        }
                    }
                });
    }

    private void clearText() {
        etname.setText("");
        etidcard.setText("");
        etphone.setText("");
        etcard.setText("");
        tvdate.setText("请选择");
    }
    public void clear(){
        SharedPreferences pref = getSharedPreferences("patient",MODE_PRIVATE);
        if(pref!=null){
            pref.edit().clear().commit();
        }
    }
}
