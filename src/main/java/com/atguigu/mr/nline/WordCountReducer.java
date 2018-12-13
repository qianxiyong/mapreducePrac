package com.atguigu.mr.nline;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * Reducer类
 * 
 * 1. Reducer处理的是Map阶段的输出
 * KEYIN-VALUEIN 是Map输出的类型！
 * 
 * KEYOUT-VALUEOUT: 根据业务需求确定！
 * 
 * 2. 核心参数：
 * 			 key： Map输出的一个key
 * 			values：  在shuffle阶段，会将多个MapTask输出的结果进行，分区，排序，分组！
 * 						会将key相同的key-value，分到一个组：
 * 								key-(value,value,value)
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	
	private IntWritable outValue=new IntWritable();
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
		
		int sum=0;
		
		for (IntWritable value : values) {
			
			sum+=value.get();
		}
		
		outValue.set(sum);
		
		context.write(key, outValue);
		
		
	}

}
