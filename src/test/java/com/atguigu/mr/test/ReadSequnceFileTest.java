package com.atguigu.mr.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;

public class ReadSequnceFileTest {

	@Test
	public void test() throws Exception {
		
		Configuration conf = new Configuration();
		
		FileSystem fs=FileSystem.get(conf);
		
		Path path=new Path("E:\\mroutput\\custom\\part-r-00000");
		
		SequenceFile.Reader reader=new SequenceFile.Reader(fs, path, conf);
		
		//Text key = (Text) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
		//BytesWritable value = (BytesWritable) ReflectionUtils.newInstance(reader.getValueClass(), conf);
		
		Text key=new Text();
		BytesWritable value=new BytesWritable();
		
		// next是每次读取一组键值对
		while(reader.next(key, value)) {
			
			if (key.equals(new Text("file:/E:/mrinput/custom/b.txt"))) {
				
				System.out.println(key);
				
				System.out.println(value);
				
				byte[] bytes = value.copyBytes();
				
				Text result = new Text(bytes);
				
				System.out.println(result);
			}
			
			
		}
	}
	
	@Test
	public void testName() throws Exception {
		
		String result="hello\r\t";
		
		byte[] bytes = result.getBytes();
		
		System.out.println(bytes.length);
		
		Text text=new Text(result);
		
		byte[] bytes2 = text.getBytes();
		
		System.out.println(bytes2.length);
	}

}
