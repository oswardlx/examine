package com.dsideal.space.examine.entity;

//局级，科室查看校级的类
public class SchoolName {
    private String id;
    private String schoolName ;
    private String stage_type;
    private String money;



    public SchoolName(String id, String schoolName, String stage_type, String  money) {
        this.id = id;
        this.schoolName = schoolName;
        this.stage_type = stage_type;
        this.money = money;
    }

    public String getMoney() {
        return money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getStage_type() {
        return stage_type;
    }
    public void setMoney(String money) {
        this.money = money;
    }


    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setStage_type(String stage_type) {
        this.stage_type = stage_type;
    }


    @Override
    public String toString() {
        return "SchoolName{" +
                "schoolName='" + schoolName + '\'' +
                ", stage_type='" + stage_type + '\'' +
                ", money=" + money +
                '}';
    }
}
