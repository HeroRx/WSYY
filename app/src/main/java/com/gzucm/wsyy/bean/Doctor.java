package com.gzucm.wsyy.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class Doctor extends BmobObject implements Serializable {

    private Department d_dcode;//	所在科室
    private boolean d_isprofess;    //是否为教授
    private String d_name;    //医生名字
    private String d_num;    //医生编号
    private BmobFile d_photo;    //医生照片
    private boolean d_sex;    //1男0女
    private String d_specialty;    //专长
    private String d_titles;    //职称
    private String d_room;
    private Integer d_money;

    public Integer getD_money() {
        return d_money;
    }

    public void setD_money(Integer d_money) {
        this.d_money = d_money;
    }


    public String getD_room() {
        return d_room;
    }

    public void setD_room(String d_room) {
        this.d_room = d_room;
    }


    public Department getD_dcode() {
        return d_dcode;
    }

    public void setD_dcode(Department d_dcode) {
        this.d_dcode = d_dcode;
    }

    public boolean isD_isprofess() {
        return d_isprofess;
    }

    public void setD_isprofess(boolean d_isprofess) {
        this.d_isprofess = d_isprofess;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public String getD_num() {
        return d_num;
    }

    public void setD_num(String d_num) {
        this.d_num = d_num;
    }

    public BmobFile getD_photo() {
        return d_photo;
    }

    public void setD_photo(BmobFile d_photo) {
        this.d_photo = d_photo;
    }

    public boolean isD_sex() {
        return d_sex;
    }

    public void setD_sex(boolean d_sex) {
        this.d_sex = d_sex;
    }

    public String getD_specialty() {
        return d_specialty;
    }

    public void setD_specialty(String d_specialty) {
        this.d_specialty = d_specialty;
    }

    public String getD_titles() {
        return d_titles;
    }

    public void setD_titles(String d_titles) {
        this.d_titles = d_titles;
    }



}
