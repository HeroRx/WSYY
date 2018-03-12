package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Thur extends BmobObject{
    private Integer th_count;
    private Doctor th_d;
    private TimeDuan th_t;

    public Integer getTh_count() {
        return th_count;
    }

    public void setTh_count(Integer th_count) {
        this.th_count = th_count;
    }

    public Doctor getTh_d() {
        return th_d;
    }

    public void setTh_d(Doctor th_d) {
        this.th_d = th_d;
    }

    public TimeDuan getTh_t() {
        return th_t;
    }

    public void setTh_t(TimeDuan th_t) {
        this.th_t = th_t;
    }
}
