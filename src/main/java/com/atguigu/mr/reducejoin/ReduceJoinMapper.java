package com.atguigu.mr.reducejoin;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

// 共有字段作为key
public class ReduceJoinMapper extends Mapper<LongWritable, Text,Text , ProductBean>{
	
	private Text outKey=new Text();
	
	private ProductBean outValue=new ProductBean();
	
	private String fileName;
	
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		
		FileSplit split=(FileSplit) context.getInputSplit();
		
		fileName=split.getPath().getName();
	}
	
	//1001	01	1
	//01	小米
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		String[] words = value.toString().split("\t");
		
		//根据切片的来源判断当前读的key-value是哪个文件
		outValue.setFileName(fileName);
		
		if (fileName.contains("order")) {
			
			outValue.setOrderId(words[0]);
			outValue.setBrandId(words[1]);
			outValue.setAmount(words[2]);
			outValue.setBrandName("novalue");
			
			outKey.set(words[1]);
			
			context.getCounter("MyCount", "OrderCounter").increment(1);
			
		}else {
			
			outValue.setBrandId(words[0]);
			outValue.setBrandName(words[1]);
			outValue.setAmount("novalue");
			outValue.setOrderId("novalue");
			
			outKey.set(words[0]);
			
			context.getCounter("MyCount", "PdCounter").increment(1);
			
		}
		
		context.write(outKey, outValue);
		
		
	}

}
