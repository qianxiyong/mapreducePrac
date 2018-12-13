package com.atguigu.mr.group;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

//10000001	Pdt_01	222.8
public class OrderBean implements WritableComparable<OrderBean>{
	
	private long orderId;
	private String productId;
	private double account;
	
	public OrderBean() {
		
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public double getAccount() {
		return account;
	}

	public void setAccount(double account) {
		this.account = account;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(orderId);
		out.writeUTF(productId);
		out.writeDouble(account);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		orderId=in.readLong();
		productId=in.readUTF();
		account=in.readDouble();
				
	}

	// 二次排序： 先按照orderId排序，相同的继续按照account排序
	@Override
	public int compareTo(OrderBean o) {
		
		int result=this.orderId > o.getOrderId() ? 1 : (this.orderId == o.getOrderId() ? 0 : -1);
		
		if ( result == 0) {
			result=this.account > o.getAccount() ? -1 : (this.account == o.getAccount() ? 0 : 1);
		}
		
		return result;
	}

	@Override
	public String toString() {
		return  orderId + "\t" + productId + "\t" + account ;
	}
	
	

}
