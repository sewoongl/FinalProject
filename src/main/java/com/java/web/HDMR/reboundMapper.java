package com.java.web.HDMR;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class reboundMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	Text outputKey = new Text();							
	final static IntWritable outputValue = new IntWritable(1);
	
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		swBean swbean = new swBean(value);
//		if(swbean.getGoals() > 0) {
//			outputValue.set(swbean.getGoals());
//			outputKey.set(swbean.getYear());
//			context.write(outputKey, outputValue);
//		}
		if(swbean.getRebounds() > 0){
			outputValue.set(swbean.getRebounds());
			outputKey.set(swbean.getYear());
			context.write(outputKey, outputValue);
		}
//		if(swbean.getAssist()> 0){
//			outputValue.set(swbean.getAssist());
//			outputKey.set(swbean.getYear());
//			context.write(outputKey, outputValue);
//		}
//		if(swbean.getSteal() > 0){
//			outputValue.set(swbean.getSteal());
//			outputKey.set(swbean.getYear());
//			context.write(outputKey, outputValue);
//		}
	}
	
}