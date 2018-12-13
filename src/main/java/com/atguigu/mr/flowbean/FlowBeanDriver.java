package com.atguigu.mr.flowbean;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.atguigu.mr.wordcount.WordCountDriver;
import com.atguigu.mr.wordcount.WordCountMapper;
import com.atguigu.mr.wordcount.WordCountReducer;

/*
 * 1. 在此基础上，将结果按照手机号分到不同的结果集！
 * 
 * 		136,137,138,139分别在四个独立的区！
 * 		其余的在一个区
 * 
 * 2.思路
 * 			①将总的分区数设置为>1 的数， ReduceTask的个数大于1
 *          ② 自定义分区组件，继承Partitioner类
 *          ③ 重写getPartition，实现自己的分区逻辑
 */
public class FlowBeanDriver {
	
	public static void main(String[] args) throws Exception {
		
		Job job = Job.getInstance();
		
		Configuration conf = job.getConfiguration();
		
		// 设置自定义的分区组件
		job.setPartitionerClass(PhoneNumPartitioner.class);
		
		job.setNumReduceTasks(1);
		
		
		FileSystem fs=FileSystem.get(conf);
		
		// 输入和输出路径
		Path inputPath = new Path("E:/mroutput\\flowbean");
		Path outputPath = new Path("e:/mroutput/part_sort_flowbean");
		
		if (fs.exists(outputPath)) {
			fs.delete(outputPath, true);
		}
		
		// hadoop jar xxxx-example.jar 主类名 参数
		// 告知Job所在的Jar包的位置，通过WordCountDriver的类路径寻找Jar包
		job.setJarByClass(FlowBeanDriver.class);
		
		job.setJobName("flowbean");
		
		// ②设置当前的Job，各种处理的组件内容  ：　告诉JobRunner，当前Job使用哪个Mapper和Reducer类运算
		
		job.setMapperClass(FlowBeanMapper.class);
		job.setReducerClass(FlowBeanReducer.class);
		
	//  告知JobRunnerMapper和Reduce类，输入和输出的数据格式是什么？
		job.setMapOutputKeyClass(FlowBean.class);
		job.setMapOutputValueClass(Text.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FlowBean.class);
		
		// 告诉JobRunner，处理的输入文件的目录是什么，输出的目的地是什么？
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
			
		boolean if_success = job.waitForCompletion(true);
		
		if (if_success) {
			System.out.println("程序运行结束！");
		}
		
	}

}
