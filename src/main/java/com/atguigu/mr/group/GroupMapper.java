package com.atguigu.mr.group;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * 将订单号和金额这两个字段组合为一个Bean，设置为key，才能进行二次排序
 * 
 * NullWritable代表Null的序列化类型
 */
public class GroupMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable>{
	
	private OrderBean outKey=new OrderBean();
	
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, OrderBean, NullWritable>.Context context)
			throws IOException, InterruptedException {
		
		String[] words = value.toString().split("\t");
		
		long orderId = Long.parseLong(words[0]);
		double account = Double.parseDouble(words[2]);
		
		outKey.setAccount(account);
		outKey.setOrderId(orderId);
		outKey.setProductId(words[1]);
		
		context.write(outKey, NullWritable.get());
		
	}

}
