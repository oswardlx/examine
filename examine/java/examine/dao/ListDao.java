package com.dsideal.space.examine.dao;

import com.dsideal.space.examine.entity.BrandType;
import com.dsideal.space.examine.entity.SchoolName;
import com.dsideal.space.examine.entity.User;
import com.dsideal.space.model.SurveyModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.apache.poi.util.SystemOutLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListDao {
    private Logger logger = LoggerFactory.getLogger(ListDao.class);

    //静态化一个实例，这样在controller中不用使用时再一次一次的实例化
    public static final SurveyModel dao = new SurveyModel();

    //stage_type对应的中文学校类型
    private final static HashMap<String, String> map = new HashMap<String, String>();

    {
        map.put("1", "小学");
        map.put("2", "初中");
        map.put("3", "高中");
        map.put("4", "完全中心");
        map.put("5", "九年一贯制");
        map.put("6", "二年一贯制");
        map.put("7", "大学");
        map.put("8", "职业");
        map.put("9", "幼儿");
        map.put("10", "小幼一体");

    }

    //status对应的审批状态
    private final static HashMap<String, String> map1 = new HashMap<String, String>();

    {
        map1.put("0", "未提交");
        map1.put("1", "审批中");
        map1.put("2", "已通过");
        map1.put("3", "未通过");
        map1.put("4", "已结束");
    }

    //返回学校类型
    private String getSchoolTypeName(String stage_type) {
        return map.get(stage_type);
    }

    //返回审批状态
    private String getStatus(String status) {
        return map1.get(status);
    }

    //获得局级，科室级按照时间查看的方法
    public ArrayList<SchoolName> getUserList(String end_time, String start_time, Integer stage_type, String status, String office_id, String index) {
        String addsql1 = "";
        String addsql2 = "";
        String addsql3 = "";
        String addsql4 = " and status = 2";
//		if(status!="0"){
//			addsql4 = " and status="+status;
//		}
        if (start_time != "0" && end_time != "0") {
            start_time = start_time + " 00:00:00";
            end_time = end_time + " 23:59:59";
            addsql2 = " and i.create_time between '" + start_time + "' and '" + end_time + "' ";
        }
        if (stage_type != 0) {
            addsql1 = " and i.stage_type= " + stage_type;
        }
        if (index.equals("2") && (office_id != null) && Integer.parseInt(office_id)!=-1) {
            addsql3 = " and i.office_id =" + office_id;
        }
        String sql = "select  i.stage_type,o.org_name,i.org_id,i.org_type,Sum(i.money) as sum,(@i:=@i+1) as seq From t_social_examine_info i ,T_BASE_ORGANIZATION o  ,(select   @i:=0)   as   it  WHERE  i.org_id = o.org_id " + addsql1 + addsql2 + addsql3 + addsql4 + " and i.status <> 0  and (project_code like '%%' or project_name like '%%') group By  i.org_id,i.org_type  order by i.create_time ";
        logger.debug(sql);
        List<Record> userList = Db.find(sql);
        ArrayList<SchoolName> schoolNames = new ArrayList<SchoolName>();
        for (Record record : userList) {
            String idt = String.valueOf(record.getDouble("seq"));
            String id = idt.substring(0, idt.length() - 2);
            String org_name = String.valueOf(record.getStr("org_name"));
            String stage_type1 = String.valueOf(record.getStr("stage_type"));
            String stage_type2 = getSchoolTypeName(stage_type1);
            String sum = String.valueOf(record.get("sum"));
            SchoolName sn = new SchoolName(id, org_name, stage_type2, sum);
            schoolNames.add(sn);
        }

        return schoolNames;
    }

    //获得校级的统计列表
    public ArrayList<User> getDetail(String start_time, String end_time, String org_id, String org_type, String office_id, String index, int stage_type) {
        String addsql1 = "and org_id = " + org_id + " and org_type = " + org_type;
        String addsql2 = "";
        String addsql3 = "";
        String addsql4 = "";
        System.out.println("start_time:" + start_time + ",end_time= " + end_time);
        if (stage_type != 0) {
            addsql4 = " and i.stage_type= " + stage_type;
        }
        if (start_time != "0" && end_time != "0") {
            start_time = start_time + " 00:00:00";
            logger.debug(end_time);
            end_time = end_time + " 23:59:59";
            addsql3 = " and create_time between '" + start_time + "' and '" + end_time + "' ";
        }

        if ((office_id != null)&& Integer.parseInt(office_id)!=-1) {
            addsql2 = " and office_id = " + office_id;
        }
        String sql = "select i.* ,(@i:=@i+1) as seq from t_social_examine_info i,(select   @i:=0)   as   it where status=2  " + addsql1 + addsql2 + addsql3 + addsql4 + " order by create_time DESC limit 0,100; ";
        logger.debug(sql);
        List<Record> resultList = Db.find(sql);
        ArrayList<User> userList = new ArrayList<User>();
        for (Record record : resultList) {
            String idt = String.valueOf(record.getNumber("seq"));
            String id = idt.substring(0, idt.length() - 2);
            Date datet = record.getDate("create_time");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String date = simpleDateFormat.format(datet);
            String projectCode = String.valueOf(record.getStr("project_code"));
            String projectName = String.valueOf(record.getStr("project_name"));

            String status = String.valueOf(record.getInt("status"));//获取状态
            String status1 = getStatus(status);

            String info_id = String.valueOf(record.getInt("id"));//获取细节
            ArrayList<BrandType> bt = getInfoDetial(info_id);

            String sum = String.valueOf(record.get("money"));

            String person_id = String.valueOf(record.getInt("person_id"));//获取人名
            String person_name = getPeronName(person_id);

            String office_id1 = String.valueOf(record.getInt("office_id"));//获取科室名
            String office_name = getOfficeName(office_id1);

            User user1 = new User(id, person_name, date, projectCode, projectName, status1, bt, sum, office_name);
            userList.add(user1);
        }
        System.out.println(userList);
        return userList;
    }

    //获取每个申请单的细节列表
    private ArrayList<BrandType> getInfoDetial(String info_id) {
        String sql = "Select * From t_social_examine_detail where info_id = " + info_id;
        List<Record> resultList = Db.find(sql);
        ArrayList<BrandType> BTList = new ArrayList<BrandType>();
        for (Record record : resultList) {
            String thingName = String.valueOf(record.getStr("thing_name"));
            String brandAndType = String.valueOf(record.getStr("name"));
            String price = String.valueOf(record.getFloat("price_money"));
            String mount = String.valueOf(record.getInt("num"));
            String unintype = String.valueOf(record.getStr("unit"));
            String total_price = String.valueOf(record.getFloat("total_money"));
            BrandType bt = new BrandType(thingName, brandAndType, price, mount, unintype, total_price);
            BTList.add(bt);
        }
        return BTList;
    }

    //获得申请人名的方法
    private String getPeronName(String person_id) {
        String sql = "select t1.person_id,t1.person_name,xb_name,ifnull(identity_num,'') as identity_num,ifnull(email,'') as email,ifnull(tel,'') as tel,ifnull(person_num,'') as person_num,ifnull(finance_no,'') as finance_no,t2.login_name,ifnull(t1.stage_id,0) as stage_id,ifnull(t1.subject_id,0) as subject_id,ifnull(stage_name,'') as stage_name,ifnull(subject_name,'') as subject_name,t1.workers_no,t1.org_id,t3.org_name from t_base_person t1 inner join t_sys_loginperson t2 on t1.person_id=t2.person_id and t1.identity_id=t2.identity_id INNER JOIN  t_base_organization  t3 on  t3.ORG_ID=t1.ORG_ID where t1.person_id = " + person_id;
        List<Record> recordList = Db.find(sql);
        for (Record record : recordList) {
            String person_name = record.getStr("person_name");
            return person_name;
        }
        return "无";
    }

    //获取科室名的方法
    private String getOfficeName(String office_id) {
        String sql = " Select office_name from t_social_examine_office where id = " + office_id;
        List<Record> recordList = Db.find(sql);
        for (Record record : recordList) {
            String person_name = record.getStr("office_name");
            return person_name;
        }
        return "无";
    }
//		BrandType pp1  = new BrandType("三星笔记本",8000 , 10, "个", 80000);
//		BrandType pp2  = new BrandType("申请粉笔", 10, 150, "箱", 1500);
//		BrandType pp3  = new BrandType("eeeeee", 100000, 1, "辆", 100000);
//		ArrayList<BrandType> orderList = new ArrayList<>();
//		orderList.add(pp1);
//		orderList.add(pp2);
//		orderList.add(pp3);
//		User user1 = new User();
//		user1.setOrder(1);
//		user1.setName("张老师");
//		user1.setDate("2019-09-20");
//		user1.setProjectCode("df4567df6s4fss");
//		user1.setProjectName("dfdsfsdsf胜多负少56");
//		user1.setOrderList(orderList);
//		user1.setSum(555555555);
//
//		BrandType pp4  = new BrandType("三星笔记本",8000 , 10, "个", 80000);
//		ArrayList<BrandType> orderList2 = new ArrayList<>();
//		orderList2.add(pp4);
//		User user2 = new User();
//		user2.setOrder(1);
//		user2.setName("张老师");
//		user2.setDate("2019-09-20");
//		user2.setProjectCode("df4567df6s4fss");
//		user2.setProjectName("dfdsfsdsf胜多负少56");
//		user2.setOrderList(orderList2);
//		user2.setSum(555555555);
//
//
//
//		BrandType pp5  = new BrandType("三星笔记本",8000 , 10, "个", 80000);
//		BrandType pp6  = new BrandType("申请粉笔", 10, 150, "箱", 1500);
//		BrandType pp7  = new BrandType("eeeeee", 100000, 1, "辆", 100000);
//		ArrayList<BrandType> orderList1 = new ArrayList<>();
//		orderList1.add(pp1);
//		orderList1.add(pp2);
//		orderList1.add(pp3);
//		User user3 = new User();
//		user3.setOrder(1);
//		user3.setName("张老师");
//		user3.setDate("2019-09-20");
//		user3.setProjectCode("df4567df6s4fss");
//		user3.setProjectName("dfdsfsdsf胜多负少56");
//		user3.setOrderList(orderList1);
//		user3.setSum(555555555);
//
//		ArrayList<User> userList = new ArrayList<>();
//		userList.add(user1);
//		userList.add(user2);
//		userList.add(user3);
//		return userList;

}
