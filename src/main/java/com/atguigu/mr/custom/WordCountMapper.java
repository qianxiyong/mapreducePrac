package com.atguigu.mr.custom;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.MapContext;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * Mapper类
 * 
 * 1. 自己的编写的Mapper必须实现Mapper!
 * 		 		导包时：org.apache.hadoop.mapreduce.mapper（使用）  2.x版本提供固定实现
 * 					  org.apache.hadoop.mapred.mapper     1.x版本提供的实现
 * 
 * 2. 需要指定Map阶段输入和输出的key-value类型
 * 		 ①Map阶段，MapTask处理的输入数据，输出数据，必须都是KEY-VALUE！
 * 		 ②Key-value都需要在不同的节点间进行传输，在Map-Reduce中所有的key和value的数据类型，都使用序列化类型！
 * 				Hadoop提供了自己的序列化机制，这套机制比原生的Java的序列化机制，更省带宽！
 * 			要求所有的数据类型，必须实现Writable
 * 				Long---LongWritable
 * 				String---Text
 * 			0-hello hi how are you
 * KEYIN： 输入数据的key类型   LongWritable
 * VALUEIN： 输入数据的value类型   Text
 * 		KEYIN，VALUEIN，是由系统默认的RecordReader(记录读取器)来决定！
 * 			参考系统默认提供一个LineRecordReader，这个记录读取器
 * 				Treats keys as offset in file and value as line. 
 * 				将偏移量作为key，一行内容作为value
 * -------------------------------
 * 			输出类型，根据业务要求，自定义！  hadoop-1
 * KEYOUT：  输出数据的Key类型
 * 				输出的是单词
 * VALUEOUT：输出数据的value类型
 * 
 * 3. Mapper类的组成
 * 			①public abstract class Context implements MapContext<KEYIN,VALUEIN,KEYOUT,VALUEOUT> 
 * 					代表整个MapTask的上下文，负责获取当前Job的配置信息！
 * 
 * 			②run()，负责在多线程中执行！
 * 				setUp(); 先执行，只调用一次！
 * 				while(context.nextKeyValue){
 * 					map(context.nextKey,context.nextValue,context); //有多少组key-value，就执行多少次！
 * 				}
 * 				cleanup(); 最后执行一次！
 */
public class WordCountMapper extends Mapper<Text, BytesWritable, Text, BytesWritable>{
	
	@Override
	protected void map(Text key, BytesWritable value, Mapper<Text, BytesWritable, Text, BytesWritable>.Context context)
			throws IOException, InterruptedException {
		context.write(key, value);
	}
}
