package com.gzucm.wsyy.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/10/29 0029.
 */

public class Department extends BmobObject {

    private BigDepartment d_bigdepartmentcode;	//所属大科编号
    private String d_departmentCode;	//	科室编号
    private String d_departmentname;	//	科室名
//    private Hospital d_hosptialnum;	//	所属医院编号

    public BigDepartment getD_bigdepartmentcode() {
        return d_bigdepartmentcode;
    }

    public void setD_bigdepartmentcode(BigDepartment d_bigdepartmentcode) {
        this.d_bigdepartmentcode = d_bigdepartmentcode;
    }

    public String getD_departmentCode() {
        return d_departmentCode;
    }

    public void setD_departmentCode(String d_departmentCode) {
        this.d_departmentCode = d_departmentCode;
    }

    public String getD_departmentname() {
        return d_departmentname;
    }

    public void setD_departmentname(String d_departmentname) {
        this.d_departmentname = d_departmentname;
    }

//    public Hospital getD_hosptialnum() {
//        return d_hosptialnum;
//    }
//
//    public void setD_hosptialnum(Hospital d_hosptialnum) {
//        this.d_hosptialnum = d_hosptialnum;
//    }


}
