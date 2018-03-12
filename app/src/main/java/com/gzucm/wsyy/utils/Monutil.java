package com.gzucm.wsyy.utils;

import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Mon;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Monutil {

    /**
     * 返回周一的所有医生记录,内置查询科室
     * @return
     */
    public static Observable<List<Mon>> getMonD(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Mon>>() {
            @Override
            public void call(final Subscriber<? super List<Mon>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    List<Doctor> doctorList = new ArrayList<Doctor>();
                    BmobQuery<Mon> query = new BmobQuery<Mon>();
                    BmobQuery<Doctor> innerQuery = new BmobQuery<Doctor>();
                    innerQuery.addWhereEqualTo("d_dcode", did);
                    query.addWhereMatchesQuery("m_d", "Doctor", innerQuery);
                    query.include("m_d");
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    Observable<List<Mon>> observable = query.findObjectsObservable(Mon.class);
                    observable.subscribe(new Subscriber<List<Mon>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Mon> mons) {

                            subscriber.onNext(mons);
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
    public static Observable<List<Mon>> getAllMon(final String did){
        return Observable.create(new Observable.OnSubscribe<List<Mon>>() {
            @Override
            public void call(final Subscriber<? super List<Mon>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    BmobQuery<Mon> query = new BmobQuery<Mon>();
                    Doctor doctor = new Doctor();
                    doctor.setObjectId(did);
                    query.addWhereEqualTo("m_d", new BmobPointer(doctor));
                    query.include("m_d,m_t");
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);    // 先从缓存获取数据，如果没有，再从网络获取。
                    Observable<List<Mon>> observable = query.findObjectsObservable(Mon.class);
                    observable.subscribe(new Subscriber<List<Mon>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Mon> mons) {
                            subscriber.onNext(mons);
                        }
                    });
                }
            }
        });
    }


}
