package com.gzucm.wsyy.utils;

import android.util.Log;

import com.gzucm.wsyy.activity.SelectedDepaertmentDateActivity;
import com.gzucm.wsyy.adapter.DayDoctorAdapter;
import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Tues;

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

public class Tuesutil{

    /**
     * 返回周二的所有医生记录,内置查询科室
     * @return
     */
    public static Observable<List<Tues>> getTuesD(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Tues>>() {
            @Override
            public void call(final Subscriber<? super List<Tues>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Tues> query = new BmobQuery<Tues>();
                    BmobQuery<Doctor> innerQuery = new BmobQuery<Doctor>();
                    innerQuery.addWhereEqualTo("d_dcode", did);
                    query.addWhereMatchesQuery("t_d", "Doctor", innerQuery);
                    query.include("t_d");
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    Observable<List<Tues>> observable = query.findObjectsObservable(Tues.class);
                    observable.subscribe(new Subscriber<List<Tues>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Tues> tues) {

                            subscriber.onNext(tues);
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
    public static Observable<List<Tues>> getAllTues(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Tues>>() {
            @Override
            public void call(final Subscriber<? super List<Tues>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    BmobQuery<Tues> query = new BmobQuery<Tues>();
                    Doctor doctor = new Doctor();
                    doctor.setObjectId(did);
                    query.addWhereEqualTo("t_d", new BmobPointer(doctor));
                    query.include("t_d,t_t");
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
                    Observable<List<Tues>> observable = query.findObjectsObservable(Tues.class);
                    observable.subscribe(new Subscriber<List<Tues>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Tues> tues) {
                            subscriber.onNext(tues);
                        }
                    });
                }
            }
        });
    }

    /**
     * 观察者获取被观察者的数据 周二
     * @param did
     */
    public static DayDoctorAdapter setTues(String did){
        final List<Doctor> doctorList = new ArrayList<Doctor>();
        final List<Integer> ilist = new ArrayList<Integer>();
        DayDoctorAdapter adapter = null;
        getTuesD(did).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<Tues>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(List<Tues> tues) {

                for(Tues tue:tues){
                    int i = tue.getT_count();
                    Doctor doctor = tue.getT_d();
                    doctorList.add(doctor);
                    ilist.add(i);
                    Log.i("医生-->>周二",doctor+"" + i);
                    String d=doctor.getD_name();
                    Log.i("医生-->>周二",d+""+ i);

                }

            }
        });

        adapter = new DayDoctorAdapter(new SelectedDepaertmentDateActivity().getContext(),doctorList,ilist);
        return adapter;
    }
}
