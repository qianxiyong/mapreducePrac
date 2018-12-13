package com.atguigu.mr.mapjoin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

//1001	01	1

public class ProductBean implements Writable{
	
	private String orderId;
	private String amount;
	private String brandName;
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	

	@Override
	public void write(DataOutput out) throws IOException {
		
		out.writeUTF(amount);
		out.writeUTF(brandName);
		out.writeUTF(orderId);
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		amount=in.readUTF();
		brandName=in.readUTF();
		orderId=in.readUTF();
	}
	
	public ProductBean() {
	}

	@Override
	public String toString() {
		return orderId + "\t" + brandName+ "\t" + amount  ;
	}
	
	

}
