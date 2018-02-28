package com.dsideal.space.examine.entity;

import java.util.ArrayList;
import java.util.Date;

//查看校级的类
public class User {
	private String id;
	private String person_name;
	private String date;
	private String projectCode;
	private String projectName;
	private String status;
	private ArrayList<BrandType> orderList;
	private String sum;
	private String office_name;

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", person_name='" + person_name + '\'' +
				", date='" + date + '\'' +
				", projectCode='" + projectCode + '\'' +
				", projectName='" + projectName + '\'' +
				", status='" + status + '\'' +
				", orderList=" + orderList +
				", sum='" + sum + '\'' +
				", office_name='" + office_name + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<BrandType> getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList<BrandType> orderList) {
		this.orderList = orderList;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getOffice_name() {
		return office_name;
	}

	public void setOffice_name(String office_name) {
		this.office_name = office_name;
	}

	public User(String id, String person_name, String date, String projectCode, String projectName, String status, ArrayList<BrandType> orderList, String sum, String office_name) {
		this.id = id;
		this.person_name = person_name;
		this.date = date;
		this.projectCode = projectCode;
		this.projectName = projectName;
		this.status = status;
		this.orderList = orderList;
		this.sum = sum;
		this.office_name = office_name;
	}
}
