package com.atguigu.mr.mapjoin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// 选择将order.txt作为输入的数据，通过切片，读入到Mapper
// 选择将小的pd.txt作为一个文件，在Mapper进入map()前，将其中的数据读入到MapTask
public class MapJoinMapper extends Mapper<LongWritable, Text,  NullWritable,ProductBean>{
	
	private ProductBean outValue=new ProductBean();
	
	private HashMap<String, String> productMap=new HashMap<>();
	
	// 提前下载pd.txt，读取其中的内容，保存起来
	@Override
	protected void setup(Context context)
			throws IOException, InterruptedException {
		
		URI[] uris = context.getCacheFiles();
		
		for (URI uri : uris) {
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(uri)), "UTF-8"));
			
			String line=null;
			
			while(StringUtils.isNotEmpty(line=reader.readLine())){
				//01	小米
				String[] words = line.split("\t");
				
				productMap.put(words[0], words[1]);
				
			}
			
		}
		
	}
	
	//1001	01	1
	@Override
	protected void map(LongWritable key, Text value,
			Mapper<LongWritable, Text, NullWritable, ProductBean>.Context context)
			throws IOException, InterruptedException {
		
		String[] words = value.toString().split("\t");
		
		outValue.setOrderId(words[0]);
		outValue.setAmount(words[2]);
		outValue.setBrandName(productMap.get(words[1]));
		
		context.write(NullWritable.get(), outValue);
	}
	
	

}
