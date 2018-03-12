package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Weds extends BmobObject{
    private Integer w_count;
    private Doctor w_d;
    private TimeDuan w_t;

    public Integer getW_count() {
        return w_count;
    }

    public void setW_count(Integer w_count) {
        this.w_count = w_count;
    }

    public Doctor getW_d() {
        return w_d;
    }

    public void setW_d(Doctor w_d) {
        this.w_d = w_d;
    }

    public TimeDuan getW_t() {
        return w_t;
    }

    public void setW_t(TimeDuan w_t) {
        this.w_t = w_t;
    }
}
