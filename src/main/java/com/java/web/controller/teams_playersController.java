package com.java.web.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.web.dao.projectDaoInterface;
import com.java.web.util.HttpUtil;

@Controller
public class teams_playersController {
	private static final Logger logger = LoggerFactory.getLogger(teams_playersController.class);
	
	@Autowired
	projectDaoInterface pdi;
	
	@RequestMapping("/teams")
	public ModelAndView teams() {
		logger.info("팀목록 데이터 확인 시작");
		HashMap<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		param.put("sqlType", "sql.teams");
		param.put("sql", "selectList");
		List result = (List) pdi.callDB(param);
		map.put("result", result);
		logger.info("팀목록 리절트 : "+map);
		return HttpUtil.makeJsonView(map);
	}
	
	@RequestMapping("/players")
	public ModelAndView players(HttpServletRequest req) {
		logger.info("선수목록 데이터 확인 시작");
		String team_id = req.getParameter("team_id");
		HashMap<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		param.put("sqlType", "sql.players");
		param.put("sql", "selectList");
		param.put("team_id", team_id);
		List result = (List) pdi.callDB(param);
		map.put("result", result);
		logger.info("선수목록 리절트 : "+map);
		return HttpUtil.makeJsonView(map);
	}
	
}
