package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/11/3 0003.
 */

public class Appointment extends BmobObject {
    private Doctor a_doctor;//	选择科室和医院
    private Patient a_name;//		患者名字
    private Integer a_queueno;//		挂号流水号

    private String a_day;//挂号的当天
    private String a_dayid;//当天的某一条记录id,也就是某一个时间段
    private String a_relation;//此条记录与本人的关系

    public String getA_day() {
        return a_day;
    }

    public void setA_day(String a_day) {
        this.a_day = a_day;
    }

    public String getA_dayid() {
        return a_dayid;
    }

    public void setA_dayid(String a_dayid) {
        this.a_dayid = a_dayid;
    }

    public String getA_relation() {
        return a_relation;
    }

    public void setA_relation(String a_relation) {
        this.a_relation = a_relation;
    }

    public Doctor getA_doctor() {
        return a_doctor;
    }

    public void setA_doctor(Doctor a_doctor) {
        this.a_doctor = a_doctor;
    }

    public Patient getA_name() {
        return a_name;
    }

    public void setA_name(Patient a_name) {
        this.a_name = a_name;
    }

    public Integer getA_queueno() {
        return a_queueno;
    }

    public void setA_queueno(Integer a_queueno) {
        this.a_queueno = a_queueno;
    }


}
