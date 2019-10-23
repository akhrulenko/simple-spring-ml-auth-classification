package app.dao;

import app.model.IPHistory;

public interface IPHistoryDAO {

	public IPHistory getIPByAddr(String ip);

	public void addIP(IPHistory ipHistory);

	public void setTryNumber(String ip, int count);

	public void setReqPerMin(String ip, int count);

	public void updateDates(String ip, long start, long end);
}