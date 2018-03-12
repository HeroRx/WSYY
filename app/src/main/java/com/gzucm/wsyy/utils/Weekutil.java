package com.gzucm.wsyy.utils;

import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Week;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import rx.Observable;
import rx.Subscriber;

/**
 * IGNORE_CACHE :只从网络获取数据，且不会将数据缓存在本地，这是默认的缓存策略。
 CACHE_ONLY :只从缓存读取数据，如果缓存没有数据会导致一个BmobException,可以忽略不处理这个BmobException.
 NETWORK_ONLY :只从网络获取数据，同时会在本地缓存数据。
 NETWORK_ELSE_CACHE:先从网络读取数据，如果没有，再从缓存中获取。
 CACHE_ELSE_NETWORK:先从缓存读取数据，如果没有，再从网络获取。
 CACHE_THEN_NETWORK:先从缓存取数据，无论结果如何都会再次从网络获取数据。也就是说会产生2次调用。
 * Created by Administrator on 2017/11/26 0026.
 */

public class Weekutil {
    /**
     * 返回一个科的所有医生的一周排班表
     * @return
     */
    public static Observable<List<Week>> getWeek(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Week>>() {
            @Override
            public void call(final Subscriber<? super List<Week>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Week> query = new BmobQuery<Week>();
                    BmobQuery<Doctor> innerQuery = new BmobQuery<Doctor>();
                    innerQuery.addWhereEqualTo("d_dcode", did);
                    query.addWhereMatchesQuery("w_doctor", "Doctor", innerQuery);
                    query.include("w_doctor");
//                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);     // 先从缓存获取数据，如果没有，再从网络获取。
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
                    Observable<List<Week>> observable = query.findObjectsObservable(Week.class);
                    observable.subscribe(new Subscriber<List<Week>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Week> weeks) {

                            subscriber.onNext(weeks);
                        }
                    });
                }
            }
        });
    }

    /**
     * 返回一个医生的一周排班表
     * @return
     */
    public static Observable<List<Week>> getDWeek(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Week>>() {
            @Override
            public void call(final Subscriber<? super List<Week>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Week> query = new BmobQuery<Week>();
                    Doctor doctor = new Doctor();
                    doctor.setObjectId(did);
                    query.addWhereEqualTo("w_doctor",new BmobPointer(doctor));
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 先从缓存获取数据，如果没有，再从网络获取。
                    Observable<List<Week>> observable = query.findObjectsObservable(Week.class);
                    observable.subscribe(new Subscriber<List<Week>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Week> weeks) {

                            subscriber.onNext(weeks);
                        }
                    });
                }
            }
        });
    }
}
