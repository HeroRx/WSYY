package com.gzucm.wsyy.utils;

import com.gzucm.wsyy.bean.Department;
import com.gzucm.wsyy.bean.Doctor;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/18 0018.
 */

public class GetDoctorUtil {

    public static Observable<List<Doctor>> getD(final String objectId){
        return Observable.create(new Observable.OnSubscribe<List<Doctor>>() {
            @Override
            public void call(final Subscriber<? super List<Doctor>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Doctor> query = new BmobQuery<Doctor>();
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
                    Department department = new Department();
                    department.setObjectId(objectId);
                    query.addWhereEqualTo("d_dcode",new BmobPointer(department));
                    query.include("Department,d_dcode");
                    Observable<List<Doctor>> observable = query.findObjectsObservable(Doctor.class);
                    observable.subscribe(new Subscriber<List<Doctor>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Doctor> doctors) {
                            subscriber.onNext(doctors);
                        }
                    });
                }
            }
        });
    }
    public static Observable<List<Doctor>> getDoctor(final String dname){
        return Observable.create(new Observable.OnSubscribe<List<Doctor>>() {
            @Override
            public void call(final Subscriber<? super List<Doctor>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Doctor> query = new BmobQuery<Doctor>();
                    query.addWhereEqualTo("d_name",dname);
                    query.include("d_dcode,d_photo");
                    Observable<List<Doctor>> observable = query.findObjectsObservable(Doctor.class);
                    observable.subscribe(new Subscriber<List<Doctor>>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable throwable) {

                        }
                        @Override
                        public void onNext(List<Doctor> doctors) {
                            subscriber.onNext(doctors);
                        }
                    });
                }
            }
        });
    }

}
