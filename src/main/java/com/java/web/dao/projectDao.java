package com.java.web.dao;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.java.web.controller.userController;

@Repository
public class projectDao implements projectDaoInterface {
	private static final Logger logger = LoggerFactory.getLogger(projectDao.class);
	
	@Resource(name="sqlSession")
	SqlSession session;
	
	@Override
	public Object callDB(HashMap<String, Object> param) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		int resultInt = 0;
		String sql = param.get("sql").toString();
		String sqlType = param.get("sqlType").toString();
		
		logger.info("sql : " + sql);
		logger.info("sqlType : " + sqlType);
		
		if("selectOne".equals(sql)) {
			return session.selectOne(sqlType, param);
		}else if("selectList".equals(sql)) {
			return session.selectList(sqlType, param);
		}else if("update".equals(sql)) {
			return session.update(sqlType, param);
		}else if("insert".equals(sql)) {
			return session.insert(sqlType, param);
		}else if("userSession".equals(sql)) {
			return session.selectOne(sqlType, param);
		}
		return null;
	}

}
