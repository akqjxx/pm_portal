package cn.etcom.service.log.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.etcom.dao.log.SystemLogDao;
import cn.etcom.entity.SystemLog;
import cn.etcom.service.log.SystemLogService;

@Service
public class SystemLogServiceImpl implements SystemLogService {

	@Autowired
	private SystemLogDao logDao;
	@Override
	public List<SystemLog> findAll() {
		return null;
	}

	@Override
	public void saveLog(SystemLog log) {
		logDao.addLog(log);
	}

	@Override
	public SystemLog findOne(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

}
