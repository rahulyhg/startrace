package org.nobibi.startrace.account.persistence.dao;

import java.io.Serializable;

import org.nobibi.startrace.account.bean.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Serializable>{

}
