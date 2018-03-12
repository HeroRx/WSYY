package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Tues extends BmobObject{
    private Integer t_count;
    private Doctor t_d;
    private TimeDuan t_t;

    public Integer getT_count() {
        return t_count;
    }

    public void setT_count(Integer t_count) {
        this.t_count = t_count;
    }

    public Doctor getT_d() {
        return t_d;
    }

    public void setT_d(Doctor t_d) {
        this.t_d = t_d;
    }

    public TimeDuan getT_t() {
        return t_t;
    }

    public void setT_t(TimeDuan t_t) {
        this.t_t = t_t;
    }
}
