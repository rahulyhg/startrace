package org.nobibi.startrace.account.service;

import java.util.Date;

import org.nobibi.startrace.account.bean.User;
import org.nobibi.startrace.account.persistence.dao.UserDao;
import org.nobibi.startrace.common.utils.MD5Helper;
import org.nobibi.startrace.common.utils.UUIDGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public void register(User user) {
		if (user == null) return;
		String uid = UUIDGen.getUUID();
		user.setId(uid);
		user.setPassword(MD5Helper.md5DoubleSalt(user.getPassword(), uid));
		user.setStatus(1);	
		user.setRegisterTime(new Date());
		userDao.save(user);
	}
	
	public void enableUser(String userId) {
		
	}
	
	public void disableUser(String userId) {
		
	}
}
