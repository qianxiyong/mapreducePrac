package com.atguigu.mr.reducejoin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReduceJoinReducer extends Reducer<Text, ProductBean, ProductBean, NullWritable>{
	
	@Override
	protected void reduce(Text brandId, Iterable<ProductBean> values,Context context)
			throws IOException, InterruptedException {
		
		String brandName=null;
		
		// 只需要处理来自order.txt的produceBean
		List<ProductBean> result=new ArrayList<>();
		
		for (ProductBean productBean : values) {
			
			if (productBean.getFileName().contains("pd")) {
				
				brandName=productBean.getBrandName();
				
			}else {
				// 将形参Bean的属性复制到新的对象
				ProductBean bean = new ProductBean();
				
				try {
					BeanUtils.copyProperties(bean, productBean);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				result.add(bean);
				
			}
			
		}
		
		// 开始替换字段
		for (ProductBean productBean : result) {
			
			productBean.setBrandName(brandName);
			
			context.write(productBean, NullWritable.get());
		}
		
	}

}
