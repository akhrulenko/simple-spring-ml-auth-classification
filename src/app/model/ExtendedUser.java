package app.model;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ExtendedUser extends User {

	private int userID;
	private String userPhone;
	private String userEmail;
	private String userIP;
	private int userEnterTime[];
	private int userSuccPeriod;
	private boolean is2faEnable;
	private String email2faCode;
	private String phone2faCode;

	public ExtendedUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public ExtendedUser(int userID, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			String userPhone, String userEmail, String userIP, int userEnterTime[], int userSuccPeriod,
			boolean is2faEnable, String email2faCode, String phone2faCode) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userPhone = userPhone;
		this.userEmail = userEmail;
		this.userIP = userIP;
		this.userEnterTime = userEnterTime;
		this.userSuccPeriod = userSuccPeriod;
		this.is2faEnable = is2faEnable;
		this.email2faCode = email2faCode;
		this.phone2faCode = phone2faCode;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public int[] getUserEnterTime() {
		return userEnterTime;
	}

	public void setUserEntryTime(int[] userEntryTime) {
		this.userEnterTime = userEntryTime;
	}

	public int getUserSuccPeriod() {
		return userSuccPeriod;
	}

	public void setUserSuccPeriod(int userSuccPeriod) {
		this.userSuccPeriod = userSuccPeriod;
	}

	public boolean isIs2faEnable() {
		return is2faEnable;
	}

	public void setIs2faEnable(boolean is2faEnable) {
		this.is2faEnable = is2faEnable;
	}

	public String getEmail2faCode() {
		return email2faCode;
	}

	public void setEmail2faCode(String email2faCode) {
		this.email2faCode = email2faCode;
	}

	public String getPhone2faCode() {
		return phone2faCode;
	}

	public void setPhone2faCode(String phone2faCode) {
		this.phone2faCode = phone2faCode;
	}
}