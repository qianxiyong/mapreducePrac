package com.atguigu.mr.flowbean;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 *  1. 需求：
 *  			统计每个手机号 耗费的 上行和下行及总流量
 *  	KEYOUT： 手机号--->Text
 *  	VALUEOUT： 一个统计对象，这个对象中有上行，下行，总流量
 *  				FlowBean
 *  
 *  	key 必须实现WriteableCompareable
 *      value 只需要实现Writeable
 *      
 */
public class FlowBeanMapper extends Mapper<LongWritable, Text,FlowBean , Text>{
	
	private Text outValue=new Text();
	
	private FlowBean outKey=new FlowBean();
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		// 原始文件默认的分隔符是tab，使用\t切割
		//13470253144	180	180	360
		String[] words = value.toString().split("\t");
		
		outValue.set(words[0]);
		
		long upFlow=Long.parseLong(words[1]);
		long downFlow=Long.parseLong(words[2]);
		long sumFlow=upFlow+downFlow;
		
		outKey.setUpFlow(upFlow);
		outKey.setDownFlow(downFlow);
		outKey.setSumFlow(sumFlow);
		
		context.write(outKey,outValue);
		
	}

}
