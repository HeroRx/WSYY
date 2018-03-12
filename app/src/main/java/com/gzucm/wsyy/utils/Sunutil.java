package com.gzucm.wsyy.utils;

import android.util.Log;

import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Sun;

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

public class Sunutil {

    /**
     * 返回周日的所有医生记录,内置查询科室
     * @return
     */
    public static Observable<List<Sun>> getSunD(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Sun>>() {
            @Override
            public void call(final Subscriber<? super List<Sun>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Sun> query = new BmobQuery<Sun>();
                    BmobQuery<Doctor> innerQuery = new BmobQuery<Doctor>();
                    innerQuery.addWhereEqualTo("d_dcode", did);
                    query.addWhereMatchesQuery("su_d", "Doctor", innerQuery);
                    query.include("su_d");
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    Observable<List<Sun>> observable = query.findObjectsObservable(Sun.class);
                    observable.subscribe(new Subscriber<List<Sun>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Sun> sun) {

                            subscriber.onNext(sun);
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
    public static Observable<List<Sun>> getAllSun(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Sun>>() {
            @Override
            public void call(final Subscriber<? super List<Sun>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    BmobQuery<Sun> query = new BmobQuery<Sun>();
                    Doctor doctor = new Doctor();
                    doctor.setObjectId(did);
                    query.addWhereEqualTo("su_d", new BmobPointer(doctor));
                    query.include("su_d,su_t");
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
                    Observable<List<Sun>> observable = query.findObjectsObservable(Sun.class);
                    observable.subscribe(new Subscriber<List<Sun>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Sun> suns) {
                            subscriber.onNext(suns);
                        }
                    });
                }
            }
        });
    }

    /**
     * 观察者获取被观察者的数据 周日
     * @param did
     */
    public static void setSun(String did){
        getSunD(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Sun>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Sun> suns) {
                List<Doctor> doctorList = new ArrayList<Doctor>();
                for(Sun sun:suns){
                    int i = sun.getSu_count();
                    Doctor doctor = sun.getSu_d();
                    doctorList.add(doctor);
                    Log.i("医生-->>周日",doctor+"" + i);
                    String d=doctor.getD_name();
                    Log.i("医生-->>周日",d+""+ i);
                }
            }
        });
    }

}
