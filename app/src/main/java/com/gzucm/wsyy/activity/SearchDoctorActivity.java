package com.gzucm.wsyy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gzucm.wsyy.R;
import com.gzucm.wsyy.bean.Doctor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;



/**
 * Created by Administrator on 2017/12/13 0013.
 */
public class SearchDoctorActivity extends AppCompatActivity{

    @BindView(R.id.search_toolbar)
    Toolbar tb;
    @BindView(R.id.etdoctor)
    EditText et;
    @BindView(R.id.btndoctor)
    TextView search;
    @BindView(R.id.s_doctor)
    TextView hdoctor;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcch_doctor);
        ButterKnife.bind(this);
        initToolbar();
        initHaving();
    }

    private void initHaving() {
        SharedPreferences sp = getSharedPreferences("patient",MODE_PRIVATE);
        String hd = sp.getString("mhd","");
        if(hd.isEmpty()){
            hdoctor.setText("绑定患者无挂号记录");
        }else {
            hdoctor.setText(""+hd);
        }
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        tb.setTitle("搜索可挂号医生");
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

    @OnClick(R.id.btndoctor)
    void searchDoctor(){
        String mydoctor = et.getText().toString();
        BmobQuery<Doctor> query = new BmobQuery<Doctor>();
        query.addWhereEqualTo("d_name",mydoctor);
        query.include("d_dcode,d_photo");
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        Observable<List<Doctor>> observable = query.findObjectsObservable(Doctor.class);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Doctor>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<Doctor> ds) {
                        for(Doctor doctor:ds){
                            Intent intent = new Intent(SearchDoctorActivity.this,SelectedDDetailActivity.class);
                            intent.putExtra("mdname",doctor.getD_dcode().getD_departmentname());
                            intent.putExtra("doctor",doctor);
                            startActivity(intent);
                        }
                    }
                });
    }

}
