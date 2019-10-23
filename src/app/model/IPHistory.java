package app.model;

public class IPHistory {

	private int id;
	private String IP;
	private int tryNumber;
	private int reqPerMin;
	private long startDate;
	private long endDate;

	public IPHistory() {

	}

	public IPHistory(String IP, int tryNumber, int reqPerMin, long startDate, long endDate) {
		this.IP = IP;
		this.tryNumber = tryNumber;
		this.reqPerMin = reqPerMin;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getTryNumber() {
		return tryNumber;
	}

	public void setTryNumber(int tryNumber) {
		this.tryNumber = tryNumber;
	}

	public int getReqPerMin() {
		return reqPerMin;
	}

	public void setReqPerMin(int reqPerMin) {
		this.reqPerMin = reqPerMin;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
}