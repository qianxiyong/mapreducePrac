package com.atguigu.mr.custom;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class WholeFileRecordReader extends RecordReader<Text, BytesWritable> {
	
	private Text key;  // 文件的唯一标识，可以使用文件名
	private BytesWritable value; // 文件的内容转为字节数组
	
	private FileSplit fileSplit;
	
	private FileSystem fs;
	
	private boolean flag=true;

	// 可以使用InputSplit split 来获取当前切片文件！
	// 可以使用TaskAttemptContext context来获取当前的Configuration
	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		// 初始化key和value
		key=new Text();
		value=new BytesWritable();
		
		//获取切片
		 fileSplit = (FileSplit) split;
		 // 从当前Job中来获取当前使用的FileSystem
		 Configuration conf = context.getConfiguration();
		 
		 fs=FileSystem.get(conf);
	}

	// 读取一组键值对，并且返回true和false!
	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		
		if (flag) {
			
			// 文件名从切片中获取！
			String filePath = fileSplit.getPath().toString();
			
			key.set(filePath);
			
			// 准备value，需要当前Job的文件系统，来创建输入流
			FSDataInputStream is = fs.open(fileSplit.getPath());
			
			int length = (int)fileSplit.getLength();
			
			byte [] result=new byte[length];
			
			IOUtils.readFully(is, result, 0, length);
			
			IOUtils.closeStream(is);
			
			value.set(result, 0, length);
			
			flag=false;
			
			return true;
			
		}
		
		return false;
	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
		return key;
	}

	@Override
	public BytesWritable getCurrentValue() throws IOException, InterruptedException {
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		return 0;
	}

	// 最终关闭一些资源
	@Override
	public void close() throws IOException {

	}

}
