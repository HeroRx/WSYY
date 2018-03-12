package com.gzucm.wsyy.utils;

import com.gzucm.wsyy.bean.BigDepartment;
import com.gzucm.wsyy.bean.Department;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/18 0018.
 */

public class GetDepartmentUtil {

    //初始化要的东西
    public GetDepartmentUtil() {
    }

    public Observable<List<BigDepartment>> getBD(){

        return Observable.create(new Observable.OnSubscribe<List<BigDepartment>>() {
            @Override
            public void call(final Subscriber<? super List<BigDepartment>> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    BmobQuery<BigDepartment> bmobQuery = new BmobQuery<BigDepartment>();
                    bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    Observable<List<BigDepartment>> observable = bmobQuery.findObjectsObservable(BigDepartment.class);
                    observable.subscribe(new Subscriber<List<BigDepartment>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable throwable) {

                        }

                        @Override
                        public void onNext(List<BigDepartment> bigDepartments) {
                            subscriber.onNext(bigDepartments);
                        }
                    });

                }
            }
        });
    }


    public Observable<List<Department>> getBDName(final String mdname){

        return Observable.create(new Observable.OnSubscribe<List<Department>>() {
            @Override
            public void call(final Subscriber<? super List<Department>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Department> query = new BmobQuery<Department>();
                    query.addWhereEqualTo("d_departmentname",mdname);
                    query.include("d_bigdepartmentcode");
                    Observable<List<Department>> observable = query.findObjectsObservable(Department.class);
                    observable.subscribe(new Subscriber<List<Department>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable throwable) {

                        }

                        @Override
                        public void onNext(List<Department> departments) {
                            subscriber.onNext(departments);
                        }
                    });
                }
            }
        });
    }

    public Observable<List<Department>> getD(final String objectId){

        return Observable.create(new Observable.OnSubscribe<List<Department>>() {
            @Override
            public void call(final Subscriber<? super List<Department>> subscriber) {
                if(!subscriber.isUnsubscribed()){

                    BmobQuery<Department> query = new BmobQuery<Department>();
                    query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                    //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
                    BigDepartment bigDepartment = new BigDepartment();
                    bigDepartment.setObjectId(objectId);
                    query.addWhereEqualTo("d_bigdepartmentcode",new BmobPointer(bigDepartment));
                    //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
                    query.include("Department,BigDepartment,d_bigdepartmentcode");
                    Observable<List<Department>> observable = query.findObjectsObservable(Department.class);
                    observable.subscribe(new Subscriber<List<Department>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable throwable) {

                        }

                        @Override
                        public void onNext(List<Department> departments) {
                            subscriber.onNext(departments);
                        }
                    });
                }
            }
        });
    }
}
