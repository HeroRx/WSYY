package com.gzucm.wsyy.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.gzucm.wsyy.bean.Appointment;
import com.gzucm.wsyy.bean.Doctor;
import com.gzucm.wsyy.bean.Fri;
import com.gzucm.wsyy.bean.Mon;
import com.gzucm.wsyy.bean.Patient;
import com.gzucm.wsyy.bean.Post;
import com.gzucm.wsyy.bean.Sat;
import com.gzucm.wsyy.bean.Sun;
import com.gzucm.wsyy.bean.Thur;
import com.gzucm.wsyy.bean.Tues;
import com.gzucm.wsyy.bean.Weds;
import com.gzucm.wsyy.bean.Week;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class Haoutil {
    private static Context mContext;

    public Haoutil(Context context) {
        mContext = context;
    }

    public static void addAppoint(Patient patient, Doctor doctor, String day, String dayid, String relation) {
        Appointment appointment = new Appointment();
        appointment.setA_name(patient);
        appointment.setA_day(day);
        appointment.setA_dayid(dayid);
        appointment.setA_doctor(doctor);
        appointment.setA_relation(relation);
        appointment.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(mContext, "挂号成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "挂号失败,请重新确认挂号信息", Toast.LENGTH_SHORT).show();
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public static void addPost(Patient patient,String content, String dname) {
        Post post = new Post();
        post.setP_d(dname);
        post.setP_s(content);
        post.setP_p(patient);
        post.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(mContext, "预约成功,在预约时间段到护士站报到", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "预约失败,请重新确认预约信息", Toast.LENGTH_SHORT).show();
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
    public static void updateHao(String day, String id) {
        switch (day) {
            case "周一":
                updateMon(id);
                break;
            case "周二":
                updateTue(id);
                break;
            case "周三":
                updateWeds(id);
                break;
            case "周四":
                updateThur(id);
                break;
            case "周五":
                updateFri(id);
                break;
            case "周六":
                updateSat(id);
                break;
            case "周日":
                updateSun(id);
                break;

        }
    }


    private static void updateMon(String id) {
        Mon mon = new Mon();
        //当天某一行记录
        mon.increment("m_count", -1);
        mon.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob更新挂号", "更新成功");
                } else {
                    Log.i("bmob更新挂号", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private static void updateTue(String id) {
        Tues tues = new Tues();
        //当天某一行记录
        tues.increment("t_count", -1);
        tues.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob更新挂号", "更新成功");
                } else {
                    Log.i("bmob更新挂号", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private static void updateWeds(String id) {
        Weds weds = new Weds();
        //当天某一行记录
        weds.increment("w_count", -1);
        weds.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob更新挂号", "更新成功");
                } else {
                    Log.i("bmob更新挂号", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private static void updateThur(String id) {
        Thur thur = new Thur();
        //当天某一行记录
        thur.increment("th_count", -1);
        thur.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob更新挂号", "更新成功");
                } else {
                    Log.i("bmob更新挂号", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private static void updateFri(String id) {
        Fri fri = new Fri();
        //当天某一行记录
        fri.increment("f_count", -1);
        fri.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob更新挂号", "更新成功");
                } else {
                    Log.i("bmob更新挂号", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private static void updateSat(String id) {
        Sat sat = new Sat();
        //当天某一行记录
        sat.increment("s_count", -1);
        sat.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob更新挂号", "更新成功");
                } else {
                    Log.i("bmob更新挂号", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private static void updateSun(String id) {
        Sun sun = new Sun();
        //当天某一行记录
        sun.increment("su_count", -1);
        sun.update(id, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob更新挂号", "更新成功");
                } else {
                    Log.i("bmob更新挂号", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


    public static void updateWeek(String day, String did) {
        Week week = new Week();
        switch (day) {
            case "周一":
                week.increment("w_c1", -1);
                break;
            case "周二":
                week.increment("w_c2", -1);
                break;
            case "周三":
                week.increment("w_c3", -1);
                break;
            case "周四":
                week.increment("w_c4", -1);
                break;
            case "周五":
                week.increment("w_c5", -1);
                break;
            case "周六":
                week.increment("w_c6", -1);
                break;
            case "周日":
                week.increment("w_c7", -1);
                break;
        }
        //查询该医生一周的情况，并更改对应的值
        new Weekutil().getDWeek(did).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Week>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(List<Week> weeks) {
                        for (Week week : weeks) {
                            String id = week.getObjectId();
                            week.update(id, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Log.i("Week更新挂号", "更新成功");
                                    } else {
                                        Log.i("Week更新挂号", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                                    }
                                }
                            });
                        }
                    }
                });

    }
}
