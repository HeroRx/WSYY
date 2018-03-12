package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class Hospital extends BmobObject {

    private String h_dnum;//	科室总数
    private Integer h_doctornum;//	医生总数
    private String h_grade;//	医院级别
    private String h_haddress;//	医院地址
    private String h_hnum;//	医院编号
    private BmobFile h_image;//	医院图片
    private String h_importSpecialist;//	重点专科
    private String h_mobilenum;//	医院电话
    private String h_name;//	医院名称

    public String getH_dnum() {
        return h_dnum;
    }

    public void setH_dnum(String h_dnum) {
        this.h_dnum = h_dnum;
    }

    public Integer getH_doctornum() {
        return h_doctornum;
    }

    public void setH_doctornum(Integer h_doctornum) {
        this.h_doctornum = h_doctornum;
    }

    public String getH_grade() {
        return h_grade;
    }

    public void setH_grade(String h_grade) {
        this.h_grade = h_grade;
    }

    public String getH_haddress() {
        return h_haddress;
    }

    public void setH_haddress(String h_haddress) {
        this.h_haddress = h_haddress;
    }

    public String getH_hnum() {
        return h_hnum;
    }

    public void setH_hnum(String h_hnum) {
        this.h_hnum = h_hnum;
    }

    public BmobFile getH_image() {
        return h_image;
    }

    public void setH_image(BmobFile h_image) {
        this.h_image = h_image;
    }

    public String getH_importSpecialist() {
        return h_importSpecialist;
    }

    public void setH_importSpecialist(String h_importSpecialist) {
        this.h_importSpecialist = h_importSpecialist;
    }

    public String getH_mobilenum() {
        return h_mobilenum;
    }

    public void setH_mobilenum(String h_mobilenum) {
        this.h_mobilenum = h_mobilenum;
    }

    public String getH_name() {
        return h_name;
    }

    public void setH_name(String h_name) {
        this.h_name = h_name;
    }
}
