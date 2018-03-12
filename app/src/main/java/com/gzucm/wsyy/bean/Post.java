package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class Post extends BmobObject{
    private String p_d;
    private Patient p_p;
    private String p_s;

    public String getP_d() {
        return p_d;
    }

    public void setP_d(String p_d) {
        this.p_d = p_d;
    }

    public Patient getP_p() {
        return p_p;
    }

    public void setP_p(Patient p_p) {
        this.p_p = p_p;
    }

    public String getP_s() {
        return p_s;
    }

    public void setP_s(String p_s) {
        this.p_s = p_s;
    }
}
