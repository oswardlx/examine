package com.dsideal.space.examine.util;

import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import com.dsideal.space.examine.dao.Title;
import com.dsideal.space.examine.entity.BrandType;
import com.dsideal.space.examine.entity.User;

public class CreateWb {
	private ArrayList<String> headTitle;
	Title title =new Title();

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


	public HSSFWorkbook getWb(ArrayList<User> userlist){

		HSSFWorkbook wb = new HSSFWorkbook();//创建
		HSSFSheet sheet = wb.createSheet("学生表");//命名
		HSSFCellStyle style = wb.createCellStyle();//声明样式变量
		sheet.setColumnWidth(1, 3800);//设置单元格宽度    0开始
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 4500);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 3500);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);
		sheet.setColumnWidth(8, 5000);
		sheet.setColumnWidth(9, 5000);
		sheet.setColumnWidth(10, 3800);
		sheet.setColumnWidth(11, 5000);
		sheet.setColumnWidth(12, 5000);
		sheet.setColumnWidth(13, 5000);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		sheet.autoSizeColumn(1, true);//自动适应宽度。合并单元格似乎没有用

		//创建row
		ArrayList<Row> rowlist = createAllRows(userlist, sheet);
		headTitle = title.getHeadTitle();
//		循环创建格子，写数据
		for(int i =0;i<14;i++){
//		for(int i =0;i<11;i++){
			int index = 1;
			for(int j =0;j<userlist.size();j++){
				User user = userlist.get(j);//获取当前行对象
				ArrayList<String> obj1 = Obj1(user);//把行对象里的属性转为List
				int size = user.getOrderList().size();//获取格子高度
				//画格子
				if((i>=0&&i<6)||(i>=12&&i<14)){
					CellRangeAddress cra=new CellRangeAddress(index-1,index+size-2 , i, i);//合并单元格
					sheet.addMergedRegion(cra);
				}
				String str = obj1.get(i);
				//写数据
				//写每个对象的第一行数据
				Cell cell = rowlist.get(index-1).createCell(i);
				cell.setCellStyle(style);//添加剧中样式
				cell.setCellValue(str); //写入格子
				//写每个对象其他行数据
				if(i>=6&&i<12){
					int m = 1;
					for(int k=index;k<index+size-1;k++){
						ArrayList<String> obj2 = Obj2(user.getOrderList().get(m));
						m++;
						String str1 =obj2.get(i-6);
						System.out.println(str1);
						Cell cell1 = rowlist.get(k).createCell(i);
						cell1.setCellStyle(style);//添加居中样式
						cell1.setCellValue(str1);
					}
				}
				index = index+size;
			}
		}
		return wb;

	}
	//创捷所有的Row
	private ArrayList<Row> createAllRows(ArrayList<User> userlist,HSSFSheet sheet){
		int sum = 0;
		ArrayList<Row> rowlist = new ArrayList<>();
		for (int i =0;i<userlist.size();i++){
			sum = sum +userlist.get(i).getOrderList().size();
		}
		for(int i = 0;i<sum;i++){
			Row row = sheet.createRow(i);
			rowlist.add(row);
		}

		return rowlist;

	}
	//对象的属性转为ArrayList
	private ArrayList<String> Obj1(User userx){
		ArrayList<String> objx = new ArrayList<>();
		ArrayList<String> obj2 = Obj2(userx.getOrderList().get(0));
//		objx.add(String.valueOf(userx.getOrderList()));
		objx.add(userx.getId());
		objx.add(userx.getPerson_name());
		objx.add(userx.getDate());
		objx.add(userx.getProjectCode());
		objx.add(userx.getProjectName());
		objx.add(userx.getStatus());
		objx.add(String.valueOf(obj2.get(0)));
		objx.add(String.valueOf(obj2.get(1)));
		objx.add(String.valueOf(obj2.get(2)));
		objx.add(String.valueOf(obj2.get(3)));
		objx.add(String.valueOf(obj2.get(4)));
		objx.add(String.valueOf(obj2.get(5)));
		objx.add(String.valueOf(userx.getSum()));
		objx.add(userx.getOffice_name());
		return objx;
	}
	private ArrayList<String> Obj2(BrandType bt){
		ArrayList<String> objx = new ArrayList<>();
		objx.add(String.valueOf(bt.getThingName()));
		objx.add(String.valueOf(bt.getBrandAndType()));
		objx.add(String.valueOf(bt.getPrice()));
		objx.add(String.valueOf(bt.getMount()));
		objx.add(String.valueOf(bt.getUnitType()));
		objx.add(String.valueOf(bt.getTotalPrice()));
		return objx;
	}

}
