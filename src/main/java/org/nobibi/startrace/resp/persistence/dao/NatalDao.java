package org.nobibi.startrace.resp.persistence.dao;

import java.io.Serializable;

import org.nobibi.startrace.resp.bean.Natal;
import org.springframework.data.repository.CrudRepository;

public interface NatalDao extends CrudRepository<Natal, Serializable> {
	
}
