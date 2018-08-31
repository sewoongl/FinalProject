package com.java.web.HDMR;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class goalsMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	Text outputKey = new Text();							
	final static IntWritable outputValue = new IntWritable(1);
	
	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		swBean swbean = new swBean(value);
		if(swbean.getGoals() > 0) {
			outputValue.set(swbean.getGoals());
			outputKey.set(swbean.getYear());
			context.write(outputKey, outputValue);
		}
	}
	
}