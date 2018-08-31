package com.java.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.java.web.dao.projectDaoInterface;
import com.java.web.util.HttpUtil;

@Controller
public class fileController {
	private static final Logger logger = LoggerFactory.getLogger(fileController.class);
	
	@Autowired
	projectDaoInterface pdi;
	
	@RequestMapping("/profileImg")
	public ModelAndView profileImg(@RequestParam("profile") MultipartFile[] files, HttpServletResponse res, HttpServletRequest req, HttpSession ses) {
		logger.info("이미지 추가 시작");
		HashMap<String, Object> param = HttpUtil.getParamMap(req);
		
		System.out.println("현섭 파람 : "+param);
		HashMap<String, Object> map = new HashMap<String, Object>();
		Object session = ses.getAttribute("user");
		System.out.println("file ; " + files);
		if(session == null || session == "") {
			logger.info("이미지 추가 실패 세션정보 없음");
			map.put("session","0");
		}else {
			logger.info("이미지파일 서버 정상 도착");
			HashMap<String, Object> userData = (HashMap<String, Object>) session;
			logger.info("userData : " + userData);
			int userNo = (int) userData.get("userNo");
			System.out.println("1");
			
			for(int i = 0; i < files.length; i++) {	
				String fileNm = files[i].getOriginalFilename();
				System.out.println("2");
				try {
					byte[] bytes = files[i].getBytes();
					String path = "F:/eclipse/workspace/FinalProject/src/main/webapp/resources/upload/" + fileNm;
					String dns = "/resources/upload/" + fileNm;
					URLEncoder.encode(dns, "UTF-8");
					File f = new File(path);
					OutputStream out = new FileOutputStream(f);
					out.write(bytes);
					out.close();
					
					logger.info("바이트 : " + bytes);
					logger.info("userNo : " + userNo);
					param.put("userNo", userNo);
					param.put("img", path);
					param.put("dns", dns);
					param.put("sqlType", "sql.profileImg");
					param.put("sql", "update");
					int status = (int) pdi.callDB(param);
					map.put("status", status);
					map.put("userNo", userNo);
					logger.info("프로필 사진 파람 값 : " + param);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}	
		return HttpUtil.makeJsonView(map);
	}
	
	@RequestMapping("/newData")
	public ModelAndView newData(HttpServletResponse res, HttpServletRequest req, HttpSession session) {
		String newUserNo = req.getParameter("newData");
		System.out.println("뉴데이터 : "+newUserNo);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userNo", newUserNo);
		param.put("sqlType", "sql.joinSes");
		param.put("sql", "selectOne");
		logger.info("사진 업로드 후  파라미터 : " + param);
		
		HashMap<String, Object> resultMap = (HashMap<String, Object>) pdi.callDB(param);
		
		logger.info("로그인 resultMap : " + resultMap);
		session.setAttribute("user", resultMap);
		return HttpUtil.makeJsonView(resultMap);
	}
	
}










