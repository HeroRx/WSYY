package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Sat extends BmobObject{
    private Integer s_count;
    private Doctor s_d;
    private TimeDuan s_t;

    public Integer getS_count() {
        return s_count;
    }

    public void setS_count(Integer s_count) {
        this.s_count = s_count;
    }

    public Doctor getS_d() {
        return s_d;
    }

    public void setS_d(Doctor s_d) {
        this.s_d = s_d;
    }

    public TimeDuan getS_t() {
        return s_t;
    }

    public void setS_t(TimeDuan s_t) {
        this.s_t = s_t;
    }
}
