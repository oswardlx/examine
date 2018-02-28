package com.dsideal.space.examine.exceloutput;

import com.dsideal.space.examine.dao.ListDao;
import com.dsideal.space.examine.entity.BrandType;
import com.dsideal.space.examine.entity.SchoolName;
import com.dsideal.space.examine.entity.User;
import com.dsideal.space.examine.util.CreateTotalWb;
import com.dsideal.space.examine.util.CreateWb;
import com.jfinal.core.Controller;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class ExamineController extends Controller {
    private Logger logger = LoggerFactory.getLogger(ExamineController.class);

    public void index() throws IOException {
//            renderText("Hello 111 World,");
        String time_index = getPara("time_index");
        String org_id = "0";
        String org_type = "0";
        String office_id = "0";
        String status;
//			int  stage_type = 0;
        int stage_type = getParaToInt("stage_type");
        //index: 1,校  2，科室   3，局
        String index = getPara("index");
        org_id = getPara("org_id");
        org_type = getPara("org_type");
        office_id = getPara("office_id");

        //防止不传这两个必传的参数
        if (time_index == null || index == null) {
            time_index = "0";
            index = "0";
        }
        String end_time = "0";
        String start_time = "0";
        if (Integer.parseInt(time_index) == 1) {
            end_time = getPara("end_time");
            start_time = getPara("start_time");

        }
//           	stage_type = getParaToInt("stage_type");
        status = getPara("status");//预留的状态筛选参数，dao层目前并不会做处理
        if (status == null) {
            status = "0";
        }
//			renderText("Hello 222 World,");
        String fileName = "List";
        HttpServletResponse response = getResponse();
        HttpServletRequest request = getRequest();
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
        String filename = System.currentTimeMillis() + fileName + ".xls";
        filename = encodeFileName(request, filename);
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        OutputStream out = null;
        out = response.getOutputStream();

        HSSFWorkbook wb1 = null;

        //校级
        if (index.equals("1")) {
            ListDao listdao = new ListDao();
            ArrayList<User> userlist = listdao.getDetail(start_time, end_time, org_id, org_type, office_id, index, stage_type);
            System.out.println(userlist);
            BrandType bt = new BrandType("物品名称", "品牌及规格", "单价", "数量", "单位", "总价");
            ArrayList<BrandType> btList = new ArrayList<BrandType>();
            btList.add(bt);
            User user1 = new User("序号", "申请人", "申请日期", "项目编码", "项目名", "状态", btList, "总金额", "审核部门");
            userlist.add(0, user1);
            CreateWb wb = new CreateWb();
            wb1 = wb.getWb(userlist);
        } else {
            //总表
            ListDao listdao = new ListDao();
            ArrayList<SchoolName> schoolNames = listdao.getUserList(end_time, start_time, stage_type, status, office_id, index);
            SchoolName schoolName1 = new SchoolName("序号", "学校名称", "学校类型", "总金额");
            schoolNames.add(0, schoolName1);
            CreateTotalWb wb = new CreateTotalWb();
            wb1 = wb.getWb(schoolNames);
        }
        wb1.write(out);
        out.close();
        out.flush();
        //星期一早上干完
        //星期一早上没干完。，。。。。。
    }

    //下面的代码是前辈的。。。。应该是不同浏览器的编码？
    public String encodeFileName(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        String agent = request.getHeader("USER-AGENT");

        if (null != agent && -1 != agent.indexOf("MSIE")) {
            return URLEncoder.encode(fileName, "UTF-8");
        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
            return "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
        } else {
            return fileName;
        }
    }

}
