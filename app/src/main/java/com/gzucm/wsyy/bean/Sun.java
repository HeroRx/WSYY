package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Sun extends BmobObject{
    private Integer su_count;
    private Doctor su_d;
    private TimeDuan su_t;

    public Integer getSu_count() {
        return su_count;
    }

    public void setSu_count(Integer su_count) {
        this.su_count = su_count;
    }

    public Doctor getSu_d() {
        return su_d;
    }

    public void setSu_d(Doctor su_d) {
        this.su_d = su_d;
    }

    public TimeDuan getSu_t() {
        return su_t;
    }

    public void setSu_t(TimeDuan su_t) {
        this.su_t = su_t;
    }
}
