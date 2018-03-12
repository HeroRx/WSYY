package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class Mon extends BmobObject {
    private Integer m_count;
    private Doctor m_d;
    private TimeDuan m_t;
    private String md;

    public Integer getM_count() {
        return m_count;
    }

    public void setM_count(Integer m_count) {
        this.m_count = m_count;
    }

    public Doctor getM_d() {
        return m_d;
    }

    public void setM_d(Doctor m_d) {
        this.m_d = m_d;
    }

    public TimeDuan getM_t() {
        return m_t;
    }

    public void setM_t(TimeDuan m_t) {
        this.m_t = m_t;
    }
}
