package com.java.web.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.web.dao.projectDaoInterface;
import com.java.web.util.HttpUtil;

@Controller
public class boardController {
	private static final Logger logger = LoggerFactory.getLogger(boardController.class);
	
	@Autowired
	projectDaoInterface pdi;
	
	@RequestMapping("/boardWrite")
	public ModelAndView boardWrite(HttpServletRequest req, HttpServletResponse res, HttpSession ses) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Object> map = HttpUtil.getParamMap(req);
		logger.info("입력받은 내용 : " + map);
		Object session = ses.getAttribute("user");
		if(session == null || session == "") {
			logger.info("글쓰기 실패 세션정보 없음");
		}else {
			HashMap<String, Object> userData = (HashMap<String, Object>) session;
			logger.info("userData : " + userData);
			
			int userNo = (int) userData.get("userNo");
			logger.info("userNo : " + userNo);
			param.put("userNo", userNo);
			param.put("contents", map.get("contents") );
			param.put("title", map.get("title") );
			param.put("sqlType", "sql.boardWrite");
			param.put("sql", "insert");
			logger.info("글쓰기 정보 파람 값 : " + param);
			pdi.callDB(param);
			
			int sesId = (int) userData.get("status");
			param.put("sqlType", "sql.joinSes");
			param.put("sql", "selectOne");
			param.put("sesId", sesId);
			System.out.println("세션체크 파람 값 : " + param);
			if (sesId == 1) {
				HashMap<String, Object> resultMap = (HashMap<String, Object>) pdi.callDB(param);
				logger.info("글쓰기용 select문 결과 : " + resultMap);
				ses.setAttribute("user", resultMap);
			}else {
				logger.info("글쓰기 실패");
			}
		}
		return HttpUtil.makeJsonView(map);
	}
	
	
}
