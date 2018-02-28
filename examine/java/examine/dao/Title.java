package com.dsideal.space.examine.dao;

import java.util.ArrayList;

//预留实现通用的，目前没有使用
public class Title {
	public ArrayList<String> getHeadTitle(){
		ArrayList<String> head = new ArrayList<String>();
		head.add("序号");
		head.add("申请人");
		head.add("申请日期");
		head.add("项目编码");
		head.add("项目名称");
		head.add("品牌及规格");
		head.add("单价（元）");
		head.add("数量");
		head.add("单位");
		head.add("总价（元）");
		head.add("合计金额（元）");
		return head;
	}
}
