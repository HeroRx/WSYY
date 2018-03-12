package com.gzucm.wsyy.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 患者的信息表
 * Created by Administrator on 2017/10/28 0028.
 */

public class Patient extends BmobObject  implements Serializable {

    private String p_address;
    private String p_allergy;
    private String p_birth;
    private Integer p_card;
    private String p_idcard;
    //姓名和手机邮箱都在这
//    private BmobUser user;
    private String p_name;
    private String p_phone;
    private boolean p_sex;

    public String getP_address() {
        return p_address;
    }

    public void setP_address(String p_address) {
        this.p_address = p_address;
    }

    public String getP_allergy() {
        return p_allergy;
    }

    public void setP_allergy(String p_allergy) {
        this.p_allergy = p_allergy;
    }


    public String getP_birth() {
        return p_birth;
    }

    public void setP_birth(String p_birth) {
        this.p_birth = p_birth;
    }

    public Integer getP_card() {
        return p_card;
    }

    public void setP_card(Integer p_card) {
        this.p_card = p_card;
    }

    public String getP_idcard() {
        return p_idcard;
    }

    public void setP_idcard(String p_idcard) {
        this.p_idcard = p_idcard;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_phone() {
        return p_phone;
    }

    public void setP_phone(String p_phone) {
        this.p_phone = p_phone;
    }

    public boolean isP_sex() {
        return p_sex;
    }

    public void setP_sex(boolean p_sex) {
        this.p_sex = p_sex;
    }

}
