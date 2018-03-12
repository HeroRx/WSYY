package com.gzucm.wsyy.utils;

import com.gzucm.wsyy.bean.Patient;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class Patientutil {
    public Observable<List<Patient>> getPatient(final int card){

        return Observable.create(new Observable.OnSubscribe<List<Patient>>() {
            @Override
            public void call(final Subscriber<? super List<Patient>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    BmobQuery<Patient> bmobQuery = new BmobQuery<Patient>();
                    bmobQuery.addWhereEqualTo("p_card",card);
                    bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    Observable<List<Patient>> observable = bmobQuery.findObjectsObservable(Patient.class);
                    observable.subscribe(new Subscriber<List<Patient>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable throwable) {
                        }

                        @Override
                        public void onNext(List<Patient> patients) {
                            subscriber.onNext(patients);
                        }
                    });

                }
            }
        });
    }



}
