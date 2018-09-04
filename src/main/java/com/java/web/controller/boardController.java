package com.java.web.controller;

import java.util.HashMap;
import java.util.List;

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
import com.java.web.util.Criteria;
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
			
			int sesId = (int) userData.get("userNo");
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
	
	@RequestMapping("/boardList")
	public ModelAndView boardList(HttpSession ses) {
		logger.info("게시글 리스트 불러오기 시작");
		HashMap<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		param.put("sqlType", "sql.boardList");
		param.put("sql", "selectList");
		List<HashMap<String, Object>> result = (List<HashMap<String, Object>>) pdi.callDB(param);
		map.put("result", result);
		param.put("sqlType", "sql.boardCount");
		param.put("sql", "selectOne");
		int resultC = (int) pdi.callDB(param);
		map.put("resultc", resultC);
		
		
		Criteria Criteria = new Criteria();
		map.put("Criteria", Criteria.parserJs());
		
		
		
		
		logger.info("게시글 리스트 리절트 : "+map);
		return HttpUtil.makeJsonView(map);
	}
	
	//게시글 클릭했을때 내용보여주기
	@RequestMapping("/boardOne")
	public ModelAndView boardList(HttpServletRequest req, HttpSession ses) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> logged = (HashMap<String, Object>) ses.getAttribute("user");
		String boardNo = req.getParameter("boardNo");
		if(logged != null){
			String loggedNum = logged.get("userNo").toString();
			logger.info("로그인된 유저 정보 : "+loggedNum);
			logger.info("가져온 게시글 번호 :" + boardNo);
			param.put("sqlType", "sql.boardOne");
			param.put("sql", "selectOne");
			param.put("boardNo", boardNo);
			map.put("getOne", pdi.callDB(param));
			map.put("loggedNum",loggedNum);
			logger.info("가져온 boardOne : "+map);
		}else{
			logger.info("가져온 게시글 번호 :" + boardNo);
			param.put("sqlType", "sql.boardOne");
			param.put("sql", "selectOne");
			param.put("boardNo", boardNo);
			map.put("getOne", pdi.callDB(param));
			logger.info("가져온 boardOne : "+map);
		}
		return HttpUtil.makeJsonView(map);
	}
	
	//게시글 삭제
	@RequestMapping("/boardDel")
	public ModelAndView boardDel(HttpServletRequest req, HttpSession ses) {
		logger.info("게시글 삭제 시작");
		String boardNo = req.getParameter("boardNo");
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("sqlType", "sql.boardDel");
		param.put("sql", "update");
		param.put("boardNo", boardNo);
		pdi.callDB(param);
		logger.info("게시글 삭제 완료");
		return HttpUtil.makeJsonView(param);
	}
}
