package app.model;


import java.util.Date;

public class LoginQuery {
	private int id;
	private String ip;
	private String username;
	private String time;
	private String queryClass;

	public LoginQuery() {
		Date date = new Date();
		this.time = date.toString();
	}

	public LoginQuery(String ip, String username, String queryClass) {
		Date date = new Date();
		this.time = date.toString();

		this.ip = ip;
		this.username = username;
		this.queryClass = queryClass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getQueryClass() {
		return queryClass;
	}

	public void setQueryClass(String queryClass) {
		this.queryClass = queryClass;
	}
}