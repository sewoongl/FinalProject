package com.java.web.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.web.dao.projectDaoInterface;
import com.java.web.util.HttpUtil;

@Controller
public class indexController {
	
	@Autowired
	projectDaoInterface pdi;
	
	@RequestMapping("/like")
	public void like(HttpServletRequest req, HttpServletResponse res) {
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("sqlType", "sql.likePlus");
		param.put("sql", "update");
		pdi.callDB(param);
	}
	
	@RequestMapping("/likeOne")
	public ModelAndView likeOne(HttpServletRequest req, HttpServletResponse res) {
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		param.put("sqlType", "sql.likeOne");
		param.put("sql", "selectOne");
		resultMap.put("likeOne", pdi.callDB(param));
		return HttpUtil.makeJsonView(resultMap);
	}
}
