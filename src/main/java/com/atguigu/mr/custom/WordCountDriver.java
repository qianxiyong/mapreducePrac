package com.atguigu.mr.custom;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

/*
 * 1. 编程：
 * 			①Driver类，在这个Driver类的main方法中，提交整个Job
 * 			②一个Job，必须有N个MapTask,MapTask是执行Map阶段的逻辑
 * 					Map阶段的逻辑，写在Mapper中，编写Mapper类
 * 
 * 			③一个Job，必须有N个ReduceTask,ReduceTask是执行Reduce阶段的逻辑
 * 					Reduce阶段的逻辑，写在Reducer中，编写Reducer类
 * 
 * 2. 通过Job job = Job.getInstance();
 * 			来获取的Job默认是在本地运行的！使用LoaclJobRunner运行！
 * 
 * 3. Job不支持跨虚拟机提交！ 
 * 			解决： ①在Linux运行eclipse,是可以提交！
 * 				 ②将程序打包，上传后，使用hadoop jar执行！
 * 4. 程序执行的输入和输出目录可以是HDFS的路径，也可以是本地的路径！
 * 			可以通过fs.default.name，来指定默认的文件系统，还明确使用schame来指定
 * 					HDFS： hdfs://xxxx:9000/xxx
 * 					本地：  file:///xxx
 * 
 * 			不指定schame，默认参考fs.default.name，为路径加上schame!
 */
public class WordCountDriver {
	
	// 输出目录必须不存在！
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		// 为jvm虚拟机运行期间，设置一个参数，即hadoop运行的用户名！
		//  只有在输出目录使用的是hdfs的路径，需要设置用户名，因为需要有写权限！
		//System.setProperty("HADOOP_USER_NAME", "atguigu");
		// ①创建Job对象
		
		Job job = Job.getInstance();
		
		Configuration conf = job.getConfiguration();
		
		// 设置combine的片最大值
		
		FileSystem fs=FileSystem.get(conf);
		
		// 输入和输出路径
		Path inputPath = new Path("E:/mrinput/custom");
		Path outputPath = new Path("e:/mroutput/custom");
		
		if (fs.exists(outputPath)) {
			fs.delete(outputPath, true);
		}
		
		// hadoop jar xxxx-example.jar 主类名 参数
		// 告知Job所在的Jar包的位置，通过WordCountDriver的类路径寻找Jar包
		job.setJarByClass(WordCountDriver.class);
		
		job.setJobName("wc");
		
		// ②设置当前的Job，各种处理的组件内容  ：　告诉JobRunner，当前Job使用哪个Mapper和Reducer类运算
		
		job.setMapperClass(WordCountMapper.class);
		job.setReducerClass(WordCountReducer.class);
		
	//  告知JobRunnerMapper和Reduce类，输入和输出的数据格式是什么？
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(BytesWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(BytesWritable.class);
		
		// 告诉JobRunner，处理的输入文件的目录是什么，输出的目的地是什么？
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		

		job.setInputFormatClass(WholeFileReadInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		
		boolean if_success = job.waitForCompletion(true);
		
		if (if_success) {
			System.out.println("程序运行结束！");
		}
		
		
	}

}
