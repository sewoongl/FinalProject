package com.java.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java.web.dao.projectDaoInterface;
import com.java.web.util.FinallUtil;
import com.java.web.util.HttpUtil;

@Controller
public class userController {
	private static final Logger logger = LoggerFactory.getLogger(userController.class);
	
	@Autowired
	projectDaoInterface pdi;
	
	@RequestMapping("/join")
	public ModelAndView join(HttpServletRequest req, HttpServletResponse res, HttpSession ses) {

		HashMap<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Object> map = HttpUtil.getParamMap(req);
		String id = map.get("id").toString();
		logger.info("1. 입력한 아이디값 : " + id);
		param.put("param", map);
		param.put("sqlType", "sql.joinUser");
		param.put("sql", "insert");
		int status = (int) pdi.callDB(param);
		logger.info("2. insert 성공유무 : " + status);
		param.put("sqlType", "sql.userSession");
		param.put("sql", "selectOne");
		param.put("sesId", id);
		logger.info("현재 param 내용 : " + param);
		
		if (status == 1) {
			HashMap<String, Object> resultMap = (HashMap<String, Object>) pdi.callDB(param);
			logger.info("3. Login용 select문 결과 : " + resultMap);
			ses.setAttribute("user", resultMap);
		}else {
			logger.info("회원가입 실패");
		}
		
		return HttpUtil.makeJsonView(map);
	}
	
	@RequestMapping("/idcheck")
    public void idcheck(HttpServletRequest req, HttpServletResponse res) {
        String id = req.getParameter("id");
        logger.info("중복 확인한 아이디 : " + id);
        HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("sqlType", "sql.idcheck");
		param.put("sql", "selectOne");
		param.put("id",id);
		HashMap<String, Object> result = (HashMap<String, Object>) pdi.callDB(param);
		map.put("result", result);
		if(result == null) {
			map.put("status","1");
		}else {
			map.put("status","0");
		}
        
        HttpUtil.makeJsonWriter(res, map);
    }
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		param.put("sqlType", "sql.login");
		param.put("sql", "selectOne");
		logger.info("로그인 파라미터 : " + param);
		
		HashMap<String, Object> resultMap = (HashMap<String, Object>) pdi.callDB(param);
		
		if(resultMap == null) {
			resultMap = new HashMap<String, Object>();
			resultMap.put("status", FinallUtil.NO);
		}else {
			resultMap.put("status", FinallUtil.OK);
		}
		
		logger.info("로그인 resultMap : " + resultMap);
		session.setAttribute("user", resultMap);
		return HttpUtil.makeJsonView(resultMap);
	}
	
	@RequestMapping("/userCheck")	
	public ModelAndView userCheck(HttpSession session) {
		logger.info("유저 체크 시작");
		HashMap<String, Object> map = new HashMap<String, Object>();
		Object list = session.getAttribute("user");
		map.put("list", list);
		logger.info("이것은 유저체크 맵이다 : " + map);
		return HttpUtil.makeJsonView(map);
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession ses) {
		ses.invalidate();
		logger.info("로그아웃 성공");
		return "redirect:/resources/index.html";
	}
	
	@RequestMapping("/infoData")
	public ModelAndView infoData(HttpSession ses) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Object user = ses.getAttribute("user");
		map.put("user", user);
		
		return HttpUtil.makeJsonView(map);
	}
	
	@RequestMapping("/changePw")
	public ModelAndView changePw(HttpServletRequest req, HttpServletResponse res, HttpSession ses) {
		logger.info("비번변경 시작!");
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("비번 변경 파라미터 : " + param);
		
		param.put("sqlType", "sql.newPw");
		param.put("sql", "update");
		
		int status = (int) pdi.callDB(param);
		if(status == 1) {
			logger.info("비밀번호 변경 성공.");
			ses.invalidate();
			
		}else if (status == 0) {
			logger.info("비밀번호 변경 실패.");
		}
		map.put("status", status);

		return HttpUtil.makeJsonView(map);
	}
	
	@RequestMapping("/leaveMember")
	public ModelAndView leaveMember(HttpServletRequest req, HttpServletResponse res, HttpSession ses) {
		logger.info("회원탈퇴 시작!");
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("회원탈퇴 파라미터 : " + param);
		
		param.put("sqlType", "sql.leaveMember");
		param.put("sql", "update");
		
		int status = (int) pdi.callDB(param);
		if(status == 1) {
			logger.info("회원탈퇴 성공.");
			ses.invalidate();
			
		}else if (status == 0) {
			logger.info("회원탈퇴 실패.");
		}
		map.put("status", status);

		return HttpUtil.makeJsonView(map);
	}
}









