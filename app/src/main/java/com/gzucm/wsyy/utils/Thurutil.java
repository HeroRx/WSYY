package com.gzucm.wsyy.utils;

import android.util.Log;

import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Thur;

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

public class Thurutil {

    /**
     * 返回周四的所有医生记录,内置查询科室
     * @return
     */
    public static Observable<List<Thur>> getThurD(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Thur>>() {
            @Override
            public void call(final Subscriber<? super List<Thur>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Thur> query = new BmobQuery<Thur>();
                    BmobQuery<Doctor> innerQuery = new BmobQuery<Doctor>();
                    innerQuery.addWhereEqualTo("d_dcode", did);
                    query.addWhereMatchesQuery("th_d", "Doctor", innerQuery);
                    query.include("th_d");
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    Observable<List<Thur>> observable = query.findObjectsObservable(Thur.class);
                    observable.subscribe(new Subscriber<List<Thur>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Thur> thur) {

                            subscriber.onNext(thur);
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
    public static Observable<List<Thur>> getAllThur(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Thur>>() {
            @Override
            public void call(final Subscriber<? super List<Thur>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    BmobQuery<Thur> query = new BmobQuery<Thur>();
                    Doctor doctor = new Doctor();
                    doctor.setObjectId(did);
                    query.addWhereEqualTo("th_d", new BmobPointer(doctor));
                    query.include("th_d,th_t");
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
                    Observable<List<Thur>> observable = query.findObjectsObservable(Thur.class);
                    observable.subscribe(new Subscriber<List<Thur>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Thur> thurs) {
                            subscriber.onNext(thurs);
                        }
                    });
                }
            }
        });
    }
    /**
     * 观察者获取被观察者的数据 周四
     * @param did
     */
    public static void setThur(String did){
        getThurD(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Thur>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Thur> thurs) {
                List<Doctor> doctorList = new ArrayList<Doctor>();
                for(Thur thur:thurs){
                    int i = thur.getTh_count();
                    Doctor doctor = thur.getTh_d();
                    doctorList.add(doctor);
                    Log.i("医生-->>周四",doctor+"" + i);
                    String d=doctor.getD_name();
                    Log.i("医生-->>周四",d+""+ i);
                }
            }
        });
    }
}
