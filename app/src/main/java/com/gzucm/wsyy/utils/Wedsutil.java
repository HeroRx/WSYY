package com.gzucm.wsyy.utils;

import android.util.Log;

import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Weds;

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

public class Wedsutil {
    /**
     * 返回周三的所有医生记录,内置查询科室
     * @return
     */
    public static Observable<List<Weds>> getWedsD(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Weds>>() {
            @Override
            public void call(final Subscriber<? super List<Weds>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Weds> query = new BmobQuery<Weds>();
                    BmobQuery<Doctor> innerQuery = new BmobQuery<Doctor>();
                    innerQuery.addWhereEqualTo("d_dcode", did);
                    query.addWhereMatchesQuery("w_d", "Doctor", innerQuery);
                    query.include("w_d");
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    Observable<List<Weds>> observable = query.findObjectsObservable(Weds.class);
                    observable.subscribe(new Subscriber<List<Weds>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Weds> weds) {

                            subscriber.onNext(weds);
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
    public static Observable<List<Weds>> getAllWeds(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Weds>>() {
            @Override
            public void call(final Subscriber<? super List<Weds>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    BmobQuery<Weds> query = new BmobQuery<Weds>();
                    Doctor doctor = new Doctor();
                    doctor.setObjectId(did);
                    query.addWhereEqualTo("w_d", new BmobPointer(doctor));
                    query.include("w_d,w_t");
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
                    Observable<List<Weds>> observable = query.findObjectsObservable(Weds.class);
                    observable.subscribe(new Subscriber<List<Weds>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Weds> weds) {
                            subscriber.onNext(weds);
                        }
                    });
                }
            }
        });
    }

    /**
     * 观察者获取被观察者的数据 周三
     * @param did
     */
    public static void setWeds(String did){
        getWedsD(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Weds>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Weds> weds) {
                List<Doctor> doctorList = new ArrayList<Doctor>();
                for(Weds wed:weds){
                    int i = wed.getW_count();
                    Doctor doctor = wed.getW_d();
                    doctorList.add(doctor);
                    Log.i("医生-->>周三",doctor+"" + i);
                    String d=doctor.getD_name();
                    Log.i("医生-->>周三",d+""+ i);
                }
            }
        });
    }
}
