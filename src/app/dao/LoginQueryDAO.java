package app.dao;


import java.util.List;

import app.model.LoginQuery;

public interface LoginQueryDAO {
	
	public void addQuery(LoginQuery query) ;

	public List<LoginQuery> getLoginQueries();
}