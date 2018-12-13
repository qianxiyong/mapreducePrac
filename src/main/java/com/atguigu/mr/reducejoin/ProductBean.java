package com.atguigu.mr.reducejoin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

//1001	01	1
//01	小米
public class ProductBean implements Writable{
	
	private String orderId;
	private String brandId;
	private String amount;
	private String brandName;
	
	// 标记
	private String fileName;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		
		out.writeUTF(amount);
		out.writeUTF(brandId);
		out.writeUTF(brandName);
		out.writeUTF(fileName);
		out.writeUTF(orderId);
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		amount=in.readUTF();
		brandId=in.readUTF();
		brandName=in.readUTF();
		fileName=in.readUTF();
		orderId=in.readUTF();
	}
	
	public ProductBean() {
	}

	@Override
	public String toString() {
		return orderId + "\t" + brandName+ "\t" + amount  ;
	}
	
	

}
