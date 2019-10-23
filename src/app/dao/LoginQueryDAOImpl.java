package app.dao;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import app.model.LoginQuery;

@Repository("queriesRepository")
public class LoginQueryDAOImpl implements LoginQueryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String tableName;

	public LoginQueryDAOImpl() {
		this.tableName = "login_queries";
	}

	@Override
	public void addQuery(LoginQuery query) {
		String createSQL = "INSERT INTO " + tableName
				+ " (query_ip, username, query_time, query_class) VALUES (?, ?, ?, ?);";

		jdbcTemplate.update(createSQL, query.getIp(), query.getUsername(), query.getTime(), query.getQueryClass());
	}

	@Override
	public List<LoginQuery> getLoginQueries() {
		String selectAllSQL = "SELECT * FROM " + tableName + ";";

		List<LoginQuery> queries = new ArrayList<>();

		List<Map<String, Object>> resList = jdbcTemplate.queryForList(selectAllSQL);
		for (Map<?, ?> m : resList) {
			LoginQuery query = new LoginQuery();
			query.setId(Integer.parseInt(String.valueOf(m.get("query_id"))));
			query.setUsername(String.valueOf(m.get("username")));
			query.setIp(String.valueOf(m.get("query_IP")));
			query.setTime(String.valueOf(m.get("query_time")));
			query.setQueryClass(String.valueOf(m.get("query_class")));
			queries.add(query);
		}

		Collections.reverse(queries);
		return queries;
	}
}