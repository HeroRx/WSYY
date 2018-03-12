package com.gzucm.wsyy.utils;

import android.util.Log;

import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Sat;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Satutil {


    /**
     * 返回周六的所有医生记录,内置查询科室
     * @return
     */
    public static Observable<List<Sat>> getSatD(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Sat>>() {
            @Override
            public void call(final Subscriber<? super List<Sat>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Sat> query = new BmobQuery<Sat>();
                    BmobQuery<Doctor> innerQuery = new BmobQuery<Doctor>();
                    innerQuery.addWhereEqualTo("d_dcode", did);
                    query.addWhereMatchesQuery("s_d", "Doctor", innerQuery);
                    query.include("s_d");
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    Observable<List<Sat>> observable = query.findObjectsObservable(Sat.class);
                    observable.subscribe(new Subscriber<List<Sat>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Sat> sat) {

                            subscriber.onNext(sat);
                        }
                    });
                }
            }
        });
    }
    /**
     * 返回周一的一个医生所有的时间段
     * @return
     */
    public static Observable<List<Sat>> getAllSat(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Sat>>() {
            @Override
            public void call(final Subscriber<? super List<Sat>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    BmobQuery<Sat> query = new BmobQuery<Sat>();
                    Doctor doctor = new Doctor();
                    doctor.setObjectId(did);
                    query.addWhereEqualTo("s_d", new BmobPointer(doctor));
                    query.include("s_d,s_t");
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
                    Observable<List<Sat>> observable = query.findObjectsObservable(Sat.class);
                    observable.subscribe(new Subscriber<List<Sat>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Sat> sats) {
                            subscriber.onNext(sats);
                        }
                    });
                }
            }
        });
    }
    /**
     * 观察者获取被观察者的数据 周六
     * @param did
     */
    public static void setSat(String did){
        getSatD(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Sat>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Sat> sats){
                List<Doctor> doctorList = new ArrayList<Doctor>();
                for(Sat sat:sats){
                    int i = sat.getS_count();
                    Doctor doctor = sat.getS_d();
                    doctorList.add(doctor);
                    Log.i("医生-->>周六",doctor+"" + i);
                    String d=doctor.getD_name();
                    Log.i("医生-->>周六",d+""+ i);
                }
            }
        });
    }
}
