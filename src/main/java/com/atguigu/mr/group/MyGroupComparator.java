package com.atguigu.mr.group;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class MyGroupComparator extends WritableComparator{
	
	// 构造方法特殊
	// 第一个参数是比较的key的类型，第二个必须传true
	public MyGroupComparator() {
		super(OrderBean.class,true);
	}
	
	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		
		OrderBean o1=(OrderBean) a;
		OrderBean o2=(OrderBean) b;
		
		// 只要这两个对象的orderId属性相同，就认为是同一个对象，只比较orderId
		return o1.getOrderId() > o2.getOrderId() ? 1 : (o1.getOrderId() == o2.getOrderId() ? 0 : -1);
		
	}

}
