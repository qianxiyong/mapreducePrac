package com.atguigu.mr.flowbean;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FlowBeanReducer extends Reducer< FlowBean, Text,Text, FlowBean>{
	
	
	@Override
	protected void reduce(FlowBean FlowBean, Iterable<Text> phoneNums, Reducer<FlowBean, Text, Text, FlowBean>.Context context)
			throws IOException, InterruptedException {
		
		for (Text text : phoneNums) {
			
			context.write(text, FlowBean);
		}
		
	}

}
