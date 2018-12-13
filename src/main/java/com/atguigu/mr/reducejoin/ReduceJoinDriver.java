package com.atguigu.mr.reducejoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.atguigu.mr.outputformat.CustomOutPutFormatDriver;
import com.atguigu.mr.outputformat.CustomOutPutFormatMapper;
import com.atguigu.mr.outputformat.CustomOutPutFormatReducer;
import com.atguigu.mr.outputformat.MyOutPutFormat;

public class ReduceJoinDriver {
	
public static void main(String[] args) throws Exception {
		
		Job job = Job.getInstance();
		
		Configuration conf = job.getConfiguration();
		
		FileSystem fs=FileSystem.get(conf);
		
		// 输入和输出路径
		Path inputPath = new Path("E:\\mrinput\\reducejoin");
		Path outputPath = new Path("e:/mroutput/reducejoin");
		
		if (fs.exists(outputPath)) {
			fs.delete(outputPath, true);
		}
		
		
		// hadoop jar xxxx-example.jar 主类名 参数
		// 告知Job所在的Jar包的位置，通过WordCountDriver的类路径寻找Jar包
		job.setJarByClass(ReduceJoinDriver.class);
		
		job.setJobName("flowbean");
		
		// ②设置当前的Job，各种处理的组件内容  ：　告诉JobRunner，当前Job使用哪个Mapper和Reducer类运算
		
		job.setMapperClass(ReduceJoinMapper.class);
		job.setReducerClass(ReduceJoinReducer.class);
		
	//  告知JobRunnerMapper和Reduce类，输入和输出的数据格式是什么？
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(ProductBean.class);
		
		job.setOutputKeyClass(ProductBean.class);
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
