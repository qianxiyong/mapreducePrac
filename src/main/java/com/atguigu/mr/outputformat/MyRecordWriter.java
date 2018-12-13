package com.atguigu.mr.outputformat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

// 将reduce端输出的key-value，写到两个文件中
//  创建两个到指定输出文件的输出流！
//    流由文件系统创建，文件系统由Job.getConfiguration()
public class MyRecordWriter extends RecordWriter<Text, NullWritable> {
	
	private FileSystem fs;
	
	private Path parentPath;
	
	private FSDataOutputStream atguiguOs;
	
	private FSDataOutputStream otherOs;

	public MyRecordWriter(TaskAttemptContext job) throws IOException {
		
		Configuration conf = job.getConfiguration();
		
		fs=FileSystem.get(conf);
		
		parentPath=FileOutputFormat.getOutputPath(job);
		
		Path atguiguPath=new Path(parentPath, "atguigu.log");
		Path otherPath=new Path(parentPath, "other.log");
		
		 atguiguOs = fs.create(atguiguPath);
		 otherOs = fs.create(otherPath);
	}

	// 写出key-value
	@Override
	public void write(Text key, NullWritable value) throws IOException, InterruptedException {
	
		if (key.toString().contains("atguigu")) {
			
			atguiguOs.write(key.toString().getBytes());
			
		}else {
			
			otherOs.write(key.toString().getBytes());
		}
	
	}

	// 最后执行关闭资源
	@Override
	public void close(TaskAttemptContext context) throws IOException, InterruptedException {
			
		if (atguiguOs !=null) {
			atguiguOs.close();
		}
		
		if (otherOs !=null) {
			otherOs.close();
		}
		
		if (fs !=null) {
			fs.close();
		}
	}

}
