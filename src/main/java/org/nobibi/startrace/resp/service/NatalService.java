package org.nobibi.startrace.resp.service;

import org.nobibi.startrace.resp.bean.Natal;
import org.nobibi.startrace.resp.persistence.dao.NatalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NatalService {
	
	@Autowired
	private NatalDao natalDao;
	
	
	public void save(Natal natal) {
		natalDao.save(natal);
	}
	
	public void delete(String id) {
		natalDao.delete(id);
	}
	
	public void update(Natal natal) {
		
	}
}
