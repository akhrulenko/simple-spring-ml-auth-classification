package app.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.ExtendedUserDAO;
import app.model.ExtendedUser;

@Service("userManager")
public class ExtendedUserManager {

	@Autowired
	private ExtendedUserDAO dao;

	@Transactional
	public void createUser(ExtendedUser user) {
		dao.addUser(user);
	}

	@Transactional
	public ExtendedUser getById(int id) {
		return dao.getById(id);
	}

	@Transactional
	public ExtendedUser getByName(String username) {
		return dao.getByName(username);
	}

	@Transactional
	public void update(int id, ExtendedUser user) {
		dao.update(id, user);
	}

	@Transactional
	public void delete(int id) {
		dao.delete(id);
	}

	@Transactional
	public List<ExtendedUser> getUsers() {
		return dao.getUsers();
	}

	@Transactional
	public void setEntryTimes(String username, int entryTime[]) {
		dao.setEntryTime(username, entryTime);
	}

	@Transactional
	public void setEmail2faCode(String username, String code) {
		dao.setEmail2faCode(username, code);
	}

	@Transactional
	public void setPhone2faCode(String username, String code) {
		dao.setPhone2faCode(username, code);
	}

	@Transactional
	public void setPeriod(String username, int period) {
		dao.setPeriod(username, period);
	}
}