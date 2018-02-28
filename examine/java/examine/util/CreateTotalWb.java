package com.dsideal.space.examine.util;
import com.dsideal.space.examine.entity.SchoolName;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.dsideal.space.examine.dao.Title;
import com.dsideal.space.examine.entity.BrandType;
import com.dsideal.space.examine.entity.User;

import java.util.ArrayList;

//创建局级，科室级的查看的Wb
public class CreateTotalWb {
//    private ArrayList<String> headTitle;//做通用留下的，现在还没做 2017-12-5
//    Title title =new Title();//同上

//	private void title(HSSFSheet sheet){
//		 Row row = sheet.createRow(0);
//         Cell cell = row.createCell(0);
//	     cell.setCellValue("序号");
//	     cell.setCellValue("申请人");
//	     cell.setCellValue("申请日期");
//	     cell.setCellValue("项目编码");
//	     cell.setCellValue("项目名称");
//	     cell.setCellValue("");
//	     cell.setCellValue("");
//	     cell.setCellValue("");
//	     cell.setCellValue("");
//	     cell.setCellValue("");
//	     cell.setCellValue("");
//
//	}


    public HSSFWorkbook getWb(ArrayList<SchoolName> schoolNames){

        HSSFWorkbook wb = new HSSFWorkbook();//创建
        HSSFSheet sheet = wb.createSheet("学生表");//命名
        HSSFCellStyle style = wb.createCellStyle();//声明样式变量
        sheet.setColumnWidth(1, 5000);  //设置列的宽度  从0开始
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 5500);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 3500);
        sheet.setColumnWidth(6, 3000);
        sheet.setColumnWidth(10, 3800);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        sheet.autoSizeColumn(1, true);//自动调整宽度，但是对于合并的并没有用


        //创建row
        ArrayList<Row> rowlist = createAllRows(schoolNames, sheet);
//        headTitle = title.getHeadTitle();
//		循环创建格子，写数据
        for(int i =0;i<4;i++){ //总共4列，现在还没去做成通用，2017-12-05
//		for(int i =0;i<11;i++){
            int index = 1;
            for(int j =0;j<schoolNames.size();j++){
                SchoolName schoolName = schoolNames.get(j);//获取当前行对象
                ArrayList<String> obj1 = Obj1(schoolName);//把行对象里的属性转为List
                System.out.println("obj1.size:"+obj1.size());
                int size = 1;//获取格子高度，主要是些要合并，但是这个不用合并所以都是1
                //画格子
//                if((i>=0&&i<5)||i==10){
//                    CellRangeAddress cra=new CellRangeAddress(index,index+size-1 , i, i);//合并单元格
//                    sheet.addMergedRegion(cra);
//                }
                String str = obj1.get(i);
                //写数据
                //写每个对象的第一行数据
                Cell cell = rowlist.get(index-1).createCell(i);
                cell.setCellStyle(style);//添加居中样式
                cell.setCellValue(str); //写入格子
                //写每个对象其他行数据
//                if(i>=5&&i<10){
//                    int m = 1;
//                    for(int k=index;k<index+size-1;k++){
//                        ArrayList<String> obj2 = Obj2(user.getOrderList().get(m));
//                        m++;
//                        String str1 =obj2.get(i-5);
//                        System.out.println(str1);
//                        Cell cell1 = rowlist.get(k).createCell(i);
//                        cell1.setCellStyle(style);//添加居中样式
//                        cell1.setCellValue(str1);
//                    }
//                }
                index = index+size;
            }
        }
        return wb;

    }
    //创捷所有的Row
    private ArrayList<Row> createAllRows(ArrayList<SchoolName> schoolNames,HSSFSheet sheet){
        int sum = 0;
        ArrayList<Row> rowlist = new ArrayList<>();
        sum = schoolNames.size();
        Row row = sheet.createRow(0);
        rowlist.add(row);
        for(int i = 0;i<sum;i++){
            row = sheet.createRow(i+1);
            rowlist.add(row);
        }

        return rowlist;

    }
    //对象的属性转为ArrayList
    private ArrayList<String> Obj1(SchoolName schoolName){
        ArrayList<String> objx = new ArrayList<>();
        objx.add(String.valueOf((schoolName.getId())));
        objx.add(String.valueOf(schoolName.getSchoolName()));
        objx.add(schoolName.getStage_type());
        objx.add(String.valueOf(schoolName.getMoney()));
        return objx;
    }

}
