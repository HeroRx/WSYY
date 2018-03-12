package com.gzucm.wsyy.utils;

import android.util.Log;

import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Fri;

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

public class Friutil {

    /**
     * 返回周五的所有医生记录,内置查询科室
     * @return
     */
    public static Observable<List<Fri>> getFriD(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Fri>>() {
            @Override
            public void call(final Subscriber<? super List<Fri>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Fri> query = new BmobQuery<Fri>();
                    BmobQuery<Doctor> innerQuery = new BmobQuery<Doctor>();
                    innerQuery.addWhereEqualTo("d_dcode", did);
                    query.addWhereMatchesQuery("f_d", "Doctor", innerQuery);
                    query.include("f_d");
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    Observable<List<Fri>> observable = query.findObjectsObservable(Fri.class);
                    observable.subscribe(new Subscriber<List<Fri>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Fri> fri) {

                            subscriber.onNext(fri);
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
    public static Observable<List<Fri>> getAllFri(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Fri>>() {
            @Override
            public void call(final Subscriber<? super List<Fri>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    BmobQuery<Fri> query = new BmobQuery<Fri>();
                    Doctor doctor = new Doctor();
                    doctor.setObjectId(did);
                    query.addWhereEqualTo("f_d", new BmobPointer(doctor));
                    query.include("f_d,f_t");
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
                    Observable<List<Fri>> observable = query.findObjectsObservable(Fri.class);
                    observable.subscribe(new Subscriber<List<Fri>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Fri> fris) {
                            subscriber.onNext(fris);
                        }
                    });
                }
            }
        });
    }

    /**
     * 观察者获取被观察者的数据 周五
     * @param did
     */
    public static void setFri(String did){
        getFriD(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Fri>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Fri> fris) {
                List<Doctor> doctorList = new ArrayList<Doctor>();
                for(Fri fri:fris){
                    int i = fri.getF_count();
                    Doctor doctor = fri.getF_d();
                    doctorList.add(doctor);
                    Log.i("医生-->>周五",doctor+"" + i);
                    String d=doctor.getD_name();
                    Log.i("医生-->>周五",d+""+ i);
                }
            }
        });
    }

}
