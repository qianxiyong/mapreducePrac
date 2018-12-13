package com.atguigu.mr.mapjoin;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.atguigu.mr.reducejoin.ProductBean;
import com.atguigu.mr.reducejoin.ReduceJoinDriver;
import com.atguigu.mr.reducejoin.ReduceJoinMapper;
import com.atguigu.mr.reducejoin.ReduceJoinReducer;

// 在Job提交期间，提前，将小的，用于Join的文件，设置到一个分布式缓存中！
public class MapJoinDriver {
	
public static void main(String[] args) throws Exception {
		
		Job job = Job.getInstance();
		
		Configuration conf = job.getConfiguration();
		
		FileSystem fs=FileSystem.get(conf);
		
		// 缓存要Join的文件
		job.addCacheFile(new URI("file:///e:/pd.txt"));
		
		// 输入和输出路径
		Path inputPath = new Path("E:\\mrinput\\mapjoin");
		Path outputPath = new Path("e:/mroutput/mapjoin");
		
		if (fs.exists(outputPath)) {
			fs.delete(outputPath, true);
		}
		
		// 设置ReduceTask个数为0
		job.setNumReduceTasks(0);
		
		// hadoop jar xxxx-example.jar 主类名 参数
		// 告知Job所在的Jar包的位置，通过WordCountDriver的类路径寻找Jar包
		job.setJarByClass(MapJoinDriver.class);
		
		job.setJobName("flowbean");
		
		// ②设置当前的Job，各种处理的组件内容  ：　告诉JobRunner，当前Job使用哪个Mapper和Reducer类运算
		
		job.setMapperClass(MapJoinMapper.class);
		
	//  告知JobRunnerMapper和Reduce类，输入和输出的数据格式是什么？
		job.setMapOutputKeyClass(NullWritable.class);
		job.setMapOutputValueClass(ProductBean.class);
		
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(ProductBean.class);
		
		// 告诉JobRunner，处理的输入文件的目录是什么，输出的目的地是什么？
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
			
		boolean if_success = job.waitForCompletion(true);
		
		if (if_success) {
			System.out.println("程序运行结束！");
		}
		
	}
	

}
