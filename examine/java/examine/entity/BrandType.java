package com.dsideal.space.examine.entity;

//细节类
public class BrandType {
	private String thingName;
	private String brandAndType;
	private String price;
	private String mount;
	private String unitType;
	private String totalPrice;

	public BrandType(String thingName, String brandAndType, String price, String mount, String unitType, String totalPrice) {
		this.thingName = thingName;
		this.brandAndType = brandAndType;
		this.price = price;
		this.mount = mount;
		this.unitType = unitType;
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "BrandType{" +
				"thingName='" + thingName + '\'' +
				", brandAndType='" + brandAndType + '\'' +
				", price='" + price + '\'' +
				", mount='" + mount + '\'' +
				", unitType='" + unitType + '\'' +
				", totalPrice='" + totalPrice + '\'' +
				'}';
	}

	public String getThingName() {
		return thingName;
	}

	public void setThingName(String thingName) {
		this.thingName = thingName;
	}

	public String getBrandAndType() {
		return brandAndType;
	}

	public void setBrandAndType(String brandAndType) {
		this.brandAndType = brandAndType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getMount() {
		return mount;
	}

	public void setMount(String mount) {
		this.mount = mount;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
}
