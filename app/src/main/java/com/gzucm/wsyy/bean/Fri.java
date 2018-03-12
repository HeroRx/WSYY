package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Fri extends BmobObject{
    private Integer f_count;
    private Doctor f_d;
    private TimeDuan f_t;

    public Integer getF_count() {
        return f_count;
    }

    public void setF_count(Integer f_count) {
        this.f_count = f_count;
    }

    public Doctor getF_d() {
        return f_d;
    }

    public void setF_d(Doctor f_d) {
        this.f_d = f_d;
    }

    public TimeDuan getF_t() {
        return f_t;
    }

    public void setF_t(TimeDuan f_t) {
        this.f_t = f_t;
    }
}
