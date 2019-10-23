package app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.dao.IPHistoryDAO;
import app.model.IPHistory;

@Service("ipManager")
public class IPHistoryManager {

	@Autowired
	private IPHistoryDAO dao;

	@Transactional
	public IPHistory getIPByAddr(String ip) {
		return dao.getIPByAddr(ip);
	}

	@Transactional
	public void addIP(IPHistory ipHistory) {
		dao.addIP(ipHistory);
	}

	@Transactional
	public void setTryNumber(String ip, int count) {
		dao.setTryNumber(ip, count);
	}

	@Transactional
	public void setReqPerMin(String ip, int count) {
		dao.setReqPerMin(ip, count);
	}
	
	@Transactional
	public void updateDates(String ip, long start, long end) {
		dao.updateDates(ip, start, end);
	}
}