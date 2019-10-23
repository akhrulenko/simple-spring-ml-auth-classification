package app.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import app.model.IPHistory;

@Repository("ipRepository")
public class IPHistoryDAOImpl implements IPHistoryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String tableName;

	public IPHistoryDAOImpl() {
		this.tableName = "ip_history";
	}

	@Override
	public IPHistory getIPByAddr(String ip) {
		String selectSQL = "SELECT * FROM " + tableName + " WHERE ip = '" + ip + "';";

		List<Map<String, Object>> result = jdbcTemplate.queryForList(selectSQL);

		if (result.size() != 0) {

			return jdbcTemplate.queryForObject(selectSQL, new Object[] {}, new RowMapper<IPHistory>() {

				@Override
				public IPHistory mapRow(ResultSet rs, int rowNum) throws SQLException {

					IPHistory ipHistory = new IPHistory();
					ipHistory.setId(rs.getInt("ip_history_id"));
					ipHistory.setIP(rs.getString("ip"));
					ipHistory.setTryNumber(rs.getInt("try_number"));
					ipHistory.setReqPerMin(rs.getInt("request_per_min"));
					ipHistory.setStartDate(rs.getLong("start_date"));
					ipHistory.setEndDate(rs.getLong("end_date"));

					return ipHistory;
				}
			});
		}
		return null;
	}

	@Override
	public void addIP(IPHistory ipHistory) {
		String createSQL = "INSERT INTO " + tableName
				+ " (ip, try_number, request_per_min, start_date, end_date) VALUES (?, ?, ?, ?, ?);";

		jdbcTemplate.update(createSQL, ipHistory.getIP(), ipHistory.getTryNumber(), ipHistory.getReqPerMin(),
				ipHistory.getStartDate(), ipHistory.getEndDate());
	}

	@Override
	public void setTryNumber(String ip, int count) {
		String updateSQL = "UPDATE " + tableName + " SET try_number = ? WHERE ip = ?;";
		jdbcTemplate.update(updateSQL, count, ip);
	}

	@Override
	public void setReqPerMin(String ip, int count) {
		String updateSQL = "UPDATE " + tableName + " SET request_per_min = ? WHERE ip = ?;";
		jdbcTemplate.update(updateSQL, count, ip);
	}

	@Override
	public void updateDates(String ip, long start, long end) {
		String updateSQL = "UPDATE " + tableName + " SET start_date = ?, end_date = ? WHERE ip = ?;";
		jdbcTemplate.update(updateSQL, start, end, ip);
	}
}