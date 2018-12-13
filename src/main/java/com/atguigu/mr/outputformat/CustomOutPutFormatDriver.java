package com.atguigu.mr.outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.atguigu.mr.group.GroupDriver;
import com.atguigu.mr.group.GroupMapper;
import com.atguigu.mr.group.GroupReducer;
import com.atguigu.mr.group.MyGroupComparator;
import com.atguigu.mr.group.OrderBean;

public class CustomOutPutFormatDriver {
	
public static void main(String[] args) throws Exception {
		
		Job job = Job.getInstance();
		
		Configuration conf = job.getConfiguration();
		
		FileSystem fs=FileSystem.get(conf);
		
		// 输入和输出路径
		Path inputPath = new Path("E:\\mrinput\\outputformat");
		Path outputPath = new Path("e:/mroutput/outputformat");
		
		if (fs.exists(outputPath)) {
			fs.delete(outputPath, true);
		}
		
		job.setOutputFormatClass(MyOutPutFormat.class);
		
		// hadoop jar xxxx-example.jar 主类名 参数
		// 告知Job所在的Jar包的位置，通过WordCountDriver的类路径寻找Jar包
		job.setJarByClass(CustomOutPutFormatDriver.class);
		
		job.setJobName("flowbean");
		
		// ②设置当前的Job，各种处理的组件内容  ：　告诉JobRunner，当前Job使用哪个Mapper和Reducer类运算
		
		job.setMapperClass(CustomOutPutFormatMapper.class);
		job.setReducerClass(CustomOutPutFormatReducer.class);
		
	//  告知JobRunnerMapper和Reduce类，输入和输出的数据格式是什么？
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		// 告诉JobRunner，处理的输入文件的目录是什么，输出的目的地是什么？
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
			
		boolean if_success = job.waitForCompletion(true);
		
		if (if_success) {
			System.out.println("程序运行结束！");
		}
		
	}
	

}
