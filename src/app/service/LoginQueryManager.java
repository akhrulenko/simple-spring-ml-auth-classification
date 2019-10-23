package app.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.LoginQueryDAO;
import app.model.LoginQuery;

@Service("queriesManager")
public class LoginQueryManager {
	@Autowired
	private LoginQueryDAO dao;

	@Transactional
	public void addQuery(LoginQuery query) {
		dao.addQuery(query);
	}
	
	@Transactional
	public List<LoginQuery> getLoginQueries(){
		return dao.getLoginQueries();
	}
}