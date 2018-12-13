package com.atguigu.mr.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;

public class CompressionTest {
	
	@Test
	public void testCompression() throws Exception {
		
		String classStr="org.apache.hadoop.io.compress.DefaultCodec";
		
		String gzipStr="org.apache.hadoop.io.compress.GzipCodec";
		
		Class<?> codecClass = Class.forName(gzipStr);
		
		Configuration conf = new Configuration();
		
		CompressionCodec codec  = (CompressionCodec) ReflectionUtils.newInstance(codecClass, conf);
		
		FileInputStream is = new FileInputStream(new File("e:/hello.txt"));
		
		// 可以支持压缩的输出流
		CompressionOutputStream os = codec.createOutputStream(new FileOutputStream("e:/hello"+codec.getDefaultExtension()));
		
		IOUtils.copyBytes(is, os, 4096, true);
		
	}
	
	@SuppressWarnings("resource")
	@Test
	public void testDeCompression() throws Exception {
		
		String filePath="e:/part-r-00000.deflate";
		
		Path file = new Path(filePath);
		
		 final CompressionCodec codec =
			      new CompressionCodecFactory(new Configuration()).getCodec(file);
		 
		 CompressionInputStream is = codec.createInputStream(new FileInputStream(filePath));
		 
		 FileOutputStream os = new FileOutputStream("e:/part-r-00000");
		 
		 IOUtils.copyBytes(is, os, 4096, true);
		
	}

}
