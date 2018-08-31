package com.java.web.HDMR;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


@Controller
public class HDRMController {
	
	private static final Logger logger = LoggerFactory.getLogger(HDRMController.class);
	
	@Resource(name="hdConf")
	Configuration conf;
	
	String inputFile = "/input/csv";
	HashMap<String, Object> resultMap;
	List<HashMap<String, Object>> resultList;
	
	//팀리스트 보여주기
	@RequestMapping("/fileList")
	public void dir(HttpServletResponse resp) throws IOException {
		resultList = new ArrayList<HashMap<String, Object>>();
		getDir(inputFile);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("result", resultList);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/json;charset=utf-8");
		JSONObject json = JSONObject.fromObject(JSONSerializer.toJSON(result));
		try {
			resp.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getDir(String newPath) throws IOException {
		FileStatus[] dirList = getStatus(newPath);
		for(int i = 0; i < dirList.length; i++) {
        	String name = dirList[i].getPath().getName();
        	if(dirList[i].isDirectory()) {
        		getDir(newPath + "/" + name);
        	}else {
        		resultMap = new HashMap<String, Object>();
        		resultMap.put(name, newPath + "/" + name);
        		resultList.add(resultMap);
        	}
        }
	}
	public FileStatus[] getStatus(String newPath) throws IOException {
		URI uri = URI.create(newPath);
        Path path = new Path(uri);
		FileSystem file = FileSystem.get(uri, conf);
		return file.listStatus(path);
	}
	
	//csv리스트 클릭 했을경우 
	@RequestMapping("/click")
	public void click(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String inputDir = "/input/csv/"+req.getParameter("click")+".csv";
		logger.info("csv리스트 경로 : "+inputDir);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("inputDir", inputDir);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/json;charset=utf-8");
		JSONObject json = JSONObject.fromObject(JSONSerializer.toJSON(resultMap));
		res.getWriter().write(json.toString());
	}
	
	//골 맵 리듀스 시작
	@RequestMapping("/MRgoals")
	public void MRgoals(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String MRstart = req.getParameter("MRgoals");
		logger.info("MRgoals!");
		long curr = System.currentTimeMillis();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String datetime2 = sdf2.format(new Date(curr));
		String saveDir = "/output/"+datetime2+"goals";
		Job job = Job.getInstance(conf, "MRgoals"); 
		FileInputFormat.addInputPath(job, new Path(MRstart)); 				
		FileOutputFormat.setOutputPath(job, new Path(saveDir)); 		
		job.setJarByClass(this.getClass());											
		job.setMapperClass(goalsMapper.class);								
		job.setReducerClass(swReducer.class);									
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.waitForCompletion(true);
		resultMap.put("saveDir", saveDir);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/json;charset=utf-8");
		JSONObject json = JSONObject.fromObject(JSONSerializer.toJSON(resultMap));
		res.getWriter().write(json.toString());
		logger.info("End!");
	}
	//골 차트 그려주기
	@RequestMapping("/goals")
	public void goals(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("골 차트 그리기 시작!");
		String chart = req.getParameter("chart").toString()+"/part-r-00000";
		URI uri = URI.create(chart);
        Path path = new Path(uri);
        FileSystem file = FileSystem.get(uri, conf);
        FSDataInputStream fsIs = file.open(path);
		byte[] buffer = new byte[5000];
		int byteRead = 0;
		String result = "";
		while((byteRead = fsIs.read(buffer)) > 0) { 
			result = new String(buffer, 0, byteRead);
		}
		String[] rows = result.split("\n");
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for(int j = 0; j < rows.length; j++) {
			String row = rows[j];
			String[] cols = row.split("\t");
			HashMap<String, Object> map = new HashMap<String, Object>();
			for(int c = 0; c < cols.length; c++) {
				map.put(c + "", cols[c]);
			}
			list.add(map);
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultList", list);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/json;charset=utf-8");
		JSONObject json = JSONObject.fromObject(JSONSerializer.toJSON(resultMap));
		res.getWriter().write(json.toString());
	}
	
	//리바운드 맵 리듀스 시작
	@RequestMapping("/MRrebound")
	public void MRrebound(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String MRstart = req.getParameter("MRrebound");
		logger.info("MRrebound!");
		long curr = System.currentTimeMillis();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String datetime2 = sdf2.format(new Date(curr));
		String saveDir = "/output/"+datetime2+"rebound";
		Job job = Job.getInstance(conf, "MRrebound"); 
		FileInputFormat.addInputPath(job, new Path(MRstart)); 				
		FileOutputFormat.setOutputPath(job, new Path(saveDir)); 		
		job.setJarByClass(this.getClass());											
		job.setMapperClass(reboundMapper.class);								
		job.setReducerClass(swReducer.class);									
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.waitForCompletion(true);
		resultMap.put("saveDir", saveDir);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/json;charset=utf-8");
		JSONObject json = JSONObject.fromObject(JSONSerializer.toJSON(resultMap));
		res.getWriter().write(json.toString());
		logger.info("End!");
	}
	//리바운드 차트 그려주기
	@RequestMapping("/rebound")
	public void rebound(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("리바운드 차트 그리기 시작!");
		String chart = req.getParameter("chart").toString()+"/part-r-00000";
		URI uri = URI.create(chart);
        Path path = new Path(uri);
        FileSystem file = FileSystem.get(uri, conf);
        FSDataInputStream fsIs = file.open(path);
		byte[] buffer = new byte[5000];
		int byteRead = 0;
		String result = "";
		while((byteRead = fsIs.read(buffer)) > 0) { 
			result = new String(buffer, 0, byteRead);
		}
		String[] rows = result.split("\n");
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for(int j = 0; j < rows.length; j++) {
			String row = rows[j];
			String[] cols = row.split("\t");
			HashMap<String, Object> map = new HashMap<String, Object>();
			for(int c = 0; c < cols.length; c++) {
				map.put(c + "", cols[c]);
			}
			list.add(map);
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultList", list);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/json;charset=utf-8");
		JSONObject json = JSONObject.fromObject(JSONSerializer.toJSON(resultMap));
		res.getWriter().write(json.toString());
	}
	
	
	//어시스트 맵 리듀스 시작
	@RequestMapping("/MRassist")
	public void MRassist(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String MRstart = req.getParameter("MRassist");
		logger.info("MRassist!");
		long curr = System.currentTimeMillis();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String datetime2 = sdf2.format(new Date(curr));
		String saveDir = "/output/"+datetime2+"assist";
		Job job = Job.getInstance(conf, "MRassist"); 
		FileInputFormat.addInputPath(job, new Path(MRstart)); 				
		FileOutputFormat.setOutputPath(job, new Path(saveDir)); 		
		job.setJarByClass(this.getClass());											
		job.setMapperClass(assistMapper.class);								
		job.setReducerClass(swReducer.class);									
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.waitForCompletion(true);
		resultMap.put("saveDir", saveDir);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/json;charset=utf-8");
		JSONObject json = JSONObject.fromObject(JSONSerializer.toJSON(resultMap));
		res.getWriter().write(json.toString());
		logger.info("End!");
	}
	//어시스트 차트 그려주기
	@RequestMapping("/assist")
	public void assist(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("어시스트 차트 그리기 시작!");
		String chart = req.getParameter("chart").toString()+"/part-r-00000";
		URI uri = URI.create(chart);
        Path path = new Path(uri);
        FileSystem file = FileSystem.get(uri, conf);
        FSDataInputStream fsIs = file.open(path);
		byte[] buffer = new byte[5000];
		int byteRead = 0;
		String result = "";
		while((byteRead = fsIs.read(buffer)) > 0) { 
			result = new String(buffer, 0, byteRead);
		}
		String[] rows = result.split("\n");
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for(int j = 0; j < rows.length; j++) {
			String row = rows[j];
			String[] cols = row.split("\t");
			HashMap<String, Object> map = new HashMap<String, Object>();
			for(int c = 0; c < cols.length; c++) {
				map.put(c + "", cols[c]);
			}
			list.add(map);
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultList", list);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/json;charset=utf-8");
		JSONObject json = JSONObject.fromObject(JSONSerializer.toJSON(resultMap));
		res.getWriter().write(json.toString());
	}
	
	//스틸 맵 리듀스 시작
	@RequestMapping("/MRsteal")
	public void MRsteal(HttpServletRequest req, HttpServletResponse res) throws Exception{
		String MRstart = req.getParameter("MRsteal");
		logger.info("MRsteal!");
		long curr = System.currentTimeMillis();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String datetime2 = sdf2.format(new Date(curr));
		String saveDir = "/output/"+datetime2+"steal";
		Job job = Job.getInstance(conf, "MRsteal"); 
		FileInputFormat.addInputPath(job, new Path(MRstart)); 				
		FileOutputFormat.setOutputPath(job, new Path(saveDir)); 		
		job.setJarByClass(this.getClass());											
		job.setMapperClass(stealMapper.class);								
		job.setReducerClass(swReducer.class);									
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.waitForCompletion(true);
		resultMap.put("saveDir", saveDir);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/json;charset=utf-8");
		JSONObject json = JSONObject.fromObject(JSONSerializer.toJSON(resultMap));
		res.getWriter().write(json.toString());
		logger.info("End!");
	}
	//스틸 차트 그려주기
	@RequestMapping("/steal")
	public void steal(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("스틸 차트 그리기 시작!");
		String chart = req.getParameter("chart").toString()+"/part-r-00000";
		URI uri = URI.create(chart);
        Path path = new Path(uri);
        FileSystem file = FileSystem.get(uri, conf);
        FSDataInputStream fsIs = file.open(path);
		byte[] buffer = new byte[5000];
		int byteRead = 0;
		String result = "";
		while((byteRead = fsIs.read(buffer)) > 0) { 
			result = new String(buffer, 0, byteRead);
		}
		String[] rows = result.split("\n");
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for(int j = 0; j < rows.length; j++) {
			String row = rows[j];
			String[] cols = row.split("\t");
			HashMap<String, Object> map = new HashMap<String, Object>();
			for(int c = 0; c < cols.length; c++) {
				map.put(c + "", cols[c]);
			}
			list.add(map);
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultList", list);
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/json;charset=utf-8");
		JSONObject json = JSONObject.fromObject(JSONSerializer.toJSON(resultMap));
		res.getWriter().write(json.toString());
	}
	
	
}
