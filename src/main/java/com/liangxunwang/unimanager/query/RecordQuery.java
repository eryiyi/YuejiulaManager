package com.liangxunwang.unimanager.query;

/**
 * Created by liuzwei on 2015/2/2.
 */
public class RecordQuery {
    private int index;
    private int size;

    private String empId;
    private String schoolId;
    private String schoolIdEmp;//当前登陆者的学校ID， 用于查询首页推广用

    public String getSchoolIdEmp() {
        return schoolIdEmp;
    }

    public void setSchoolIdEmp(String schoolIdEmp) {
        this.schoolIdEmp = schoolIdEmp;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
