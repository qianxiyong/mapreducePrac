package com.atguigu.mr.flowbean;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/*
 * KEY-VALUE: 使用分区组件计算的key-value,其实就是Map输出的key-value
 * 
 */
public class PhoneNumPartitioner extends Partitioner<FlowBean,Text >{

	@Override
	public int getPartition(FlowBean key, Text value, int numPartitions) {

		int partitionNum=0;
		
		String phone_Prefix = value.toString().substring(0, 3);
		
		switch (phone_Prefix) {
		case "136":
			partitionNum=numPartitions-1;
			break;
		case "137":
			partitionNum=numPartitions-2;
			break;

		case "138":
			partitionNum=numPartitions-3;
			break;

		case "139":
			partitionNum=numPartitions-4;
			break;


		default:
			partitionNum=partitionNum;
			break;
		}
		
		return partitionNum;
	}

	

}
