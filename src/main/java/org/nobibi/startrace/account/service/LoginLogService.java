package org.nobibi.startrace.account.service;

import org.nobibi.startrace.account.bean.LoginLog;
import org.nobibi.startrace.account.persistence.dao.LoginLogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogService {
	
	@Autowired
	private LoginLogDao loginLogDao;
	
	
	public void save(LoginLog loginLog) {
		loginLogDao.save(loginLog);
	}
}
