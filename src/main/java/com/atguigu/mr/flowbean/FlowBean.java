package com.atguigu.mr.flowbean;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class FlowBean implements WritableComparable<FlowBean>{
	
	private long upFlow;
	private long downFlow;
	private long sumFlow;
	public long getUpFlow() {
		return upFlow;
	}
	public void setUpFlow(long upFlow) {
		this.upFlow = upFlow;
	}
	public long getDownFlow() {
		return downFlow;
	}
	public void setDownFlow(long downFlow) {
		this.downFlow = downFlow;
	}
	public long getSumFlow() {
		return sumFlow;
	}
	public void setSumFlow(long sumFlow) {
		this.sumFlow = sumFlow;
	}
	public FlowBean(long upFlow, long downFlow, long sumFlow) {
		super();
		this.upFlow = upFlow;
		this.downFlow = downFlow;
		this.sumFlow = sumFlow;
	}
	public FlowBean() {
		
	}
	@Override
	public String toString() {
		return  upFlow + "\t" + downFlow + "\t" + sumFlow ;
	}
	// 将各个属性，写入到流中 ，序列化
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(upFlow);
		out.writeLong(downFlow);
		out.writeLong(sumFlow);
		
	}
	// 从流中读属性，反序列化! 反序列化要和序列化顺序一致！
	@Override
	public void readFields(DataInput in) throws IOException {
		
		upFlow=in.readLong();
		downFlow=in.readLong();
		sumFlow=in.readLong();
		
	}
	
	// 实现key的比较
	@Override
	public int compareTo(FlowBean o) {
		
		return this.sumFlow > o.getSumFlow() ? -1 : (this.sumFlow == o.getSumFlow() ? 0 : 1);
	}
	
	

}
