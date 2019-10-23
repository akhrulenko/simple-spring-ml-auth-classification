package app.dao;


import java.util.List;

import app.model.ExtendedUser;

public interface ExtendedUserDAO {

	public void addUser(ExtendedUser user);

	public ExtendedUser getById(int id);

	public ExtendedUser getByName(String userName);

	public void update(int id, ExtendedUser user);

	public void delete(int id);

	public List<ExtendedUser> getUsers();

	public void setEmail2faCode(String username, String code);

	public void setPhone2faCode(String username, String code);

	public void setEntryTime(String username, int entryTime[]);

	public void setPeriod(String username, int period);
}