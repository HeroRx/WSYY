package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class BigDepartment extends BmobObject {

    private String bd_code;//	大科编号
    private String bd_name;//	大科名字
//    private Hospital db_hosptialnum;//	所属医院编号

    public String getBd_code() {
        return bd_code;
    }

    public void setBd_code(String bd_code) {
        this.bd_code = bd_code;
    }

    public String getBd_name() {
        return bd_name;
    }

    public void setBd_name(String bd_name) {
        this.bd_name = bd_name;
    }

//    public Hospital getDb_hosptialnum() {
//        return db_hosptialnum;
//    }
//
//    public void setDb_hosptialnum(Hospital db_hosptialnum) {
//        this.db_hosptialnum = db_hosptialnum;
//    }
}
