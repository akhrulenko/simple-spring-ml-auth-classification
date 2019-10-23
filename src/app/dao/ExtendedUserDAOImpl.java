package app.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import app.model.ExtendedUser;

@Repository("userRepository")
public class ExtendedUserDAOImpl implements ExtendedUserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String tableName;

	public ExtendedUserDAOImpl() {
		this.tableName = "users";
	}

	@Override
	public void addUser(ExtendedUser user) {
		String createSQL = "INSERT INTO " + tableName
				+ " (user_login, user_password, user_phone, user_email, user_ip, user_enter_time, user_roles, is_2fa_enable, email_2fa_code, user_succ_period, phone_2fa_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		jdbcTemplate.update(createSQL, user.getUsername(), user.getPassword(), user.getUserPhone(), user.getUserEmail(),
				user.getUserEnterTime(), user.getAuthorities().toArray(new String[0]), user.isIs2faEnable(),
				user.getEmail2faCode(), user.getUserSuccPeriod(), user.getPhone2faCode());
	}

	@Override
	public ExtendedUser getById(int id) {
		String selectSQL = "SELECT * FROM " + tableName + " WHERE user_id = ?;";

		return jdbcTemplate.queryForObject(selectSQL, new Object[] { id }, new RowMapper<ExtendedUser>() {

			@Override
			public ExtendedUser mapRow(ResultSet rs, int rowNum) throws SQLException {
				Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
				String roles[] = (String[]) rs.getArray("user_roles").getArray();

				for (String role : roles) {
					authorities.add(new SimpleGrantedAuthority(role));
				}

				ExtendedUser user = new ExtendedUser(rs.getString("user_login"), rs.getString("user_password"),
						authorities);
				user.setUserID(id);
				user.setUserPhone(rs.getString("user_phone"));
				user.setUserEmail(rs.getString("user_email"));
				user.setUserIP(rs.getString("user_ip"));

				Number nums[] = (Number[]) rs.getArray("user_enter_time").getArray();

				int entryTimes[] = new int[nums.length];
				for (int i = 0; i < nums.length; i++) {
					entryTimes[i] = (int) nums[i];
				}
				user.setUserEntryTime(entryTimes);

				user.setUserSuccPeriod(rs.getInt("user_succ_period"));
				user.setIs2faEnable(rs.getBoolean("is_2fa_enable"));
				user.setEmail2faCode(rs.getString("email_2fa_code"));
				user.setPhone2faCode(rs.getString("phone_2fa_code"));

				return user;
			}
		});
	}

	@Override
	public ExtendedUser getByName(String userName) {
		String selectSQL = "SELECT * FROM " + tableName + " WHERE user_login = '" + userName + "';";

		List<Map<String, Object>> result = jdbcTemplate.queryForList(selectSQL);

		if (result.size() != 0) {

			return jdbcTemplate.queryForObject(selectSQL, new Object[] {}, new RowMapper<ExtendedUser>() {

				@Override
				public ExtendedUser mapRow(ResultSet rs, int rowNum) throws SQLException {
					Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
					String roles[] = (String[]) rs.getArray("user_roles").getArray();

					for (String role : roles) {
						authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
					}

					int test[] = { 12, 24 };

					ExtendedUser user = new ExtendedUser(userName, rs.getString("user_password"), authorities);
					user.setUserID(rs.getInt("user_id"));
					user.setUserPhone(rs.getString("user_phone"));
					user.setUserEmail(rs.getString("user_email"));
					user.setUserIP(rs.getString("user_ip"));
					user.setUserEntryTime(test);
					user.setUserSuccPeriod(rs.getInt("user_succ_period"));
					user.setIs2faEnable(rs.getBoolean("is_2fa_enable"));
					user.setEmail2faCode(rs.getString("email_2fa_code"));
					user.setPhone2faCode(rs.getString("phone_2fa_code"));

					return user;
				}
			});
		}
		return null;
	}

	@Override
	public void update(int id, ExtendedUser user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ExtendedUser> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEntryTime(String username, int[] entryTime) {
		String timesString = "";
		for (int i = 0; i < entryTime.length; i++) {
			if (i == entryTime.length - 1) {
				timesString += entryTime[i] + "";
			} else {
				timesString += entryTime[i] + ", ";
			}
		}
		String updateSQL = "UPDATE " + tableName + " SET user_enter_time = '{" + timesString + "}' WHERE user_login = ?;";
		jdbcTemplate.update(updateSQL, username);
	}

	@Override
	public void setEmail2faCode(String username, String code) {
		String updateSQL = "UPDATE " + tableName + " SET email_2fa_code = ? WHERE user_login = ?;";
		jdbcTemplate.update(updateSQL, code, username);
	}

	@Override
	public void setPhone2faCode(String username, String code) {
		String updateSQL = "UPDATE " + tableName + " SET phone_2fa_code = ? WHERE user_login = ?;";
		jdbcTemplate.update(updateSQL, code, username);
	}

	@Override
	public void setPeriod(String username, int period) {
		String updateSQL = "UPDATE " + tableName + " SET user_succ_period = ? WHERE user_login = ?;";
		jdbcTemplate.update(updateSQL, period, username);
	}
}