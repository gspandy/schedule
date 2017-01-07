package com.kanven.schedual.transport.client;

public class PoolConfig {

	public final static long DEFAULT_CONNECT_TIMEOUT = 5000;

	public final static long DEFAULT_REQUEST_TIMEOUT = 3000;

	private String ip;

	private int port;

	private Long connectTimeout;

	private Long requestTimeout;

	private Integer threads;

	private Integer minIdle;

	private Integer maxIdle;

	private Integer maxTotal;

	private Long maxWaitMillis;

	private Long minEvictableIdleTimeMillis;

	private Integer numTestsPerEvictionRun;

	private Boolean testOnCreate;

	private Boolean testOnBorrow;

	private Boolean testOnReturn;

	private Boolean testWhileIdle;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Long getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Long connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Long getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(Long requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public Integer getThreads() {
		return threads;
	}

	public void setThreads(Integer threads) {
		this.threads = threads;
	}

	public Integer getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}

	public Integer getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	public Long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(Long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public Long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public Integer getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public Boolean getTestOnCreate() {
		return testOnCreate;
	}

	public void setTestOnCreate(Boolean testOnCreate) {
		this.testOnCreate = testOnCreate;
	}

	public Boolean getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(Boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public Boolean getTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(Boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public Boolean getTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(Boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	@Override
	public String toString() {
		return "PoolConfig [ip=" + ip + ", port=" + port + ", connectTimeout=" + connectTimeout + ", threads=" + threads
				+ ", minIdle=" + minIdle + ", maxIdle=" + maxIdle + ", maxTotal=" + maxTotal + ", maxWaitMillis="
				+ maxWaitMillis + ", minEvictableIdleTimeMillis=" + minEvictableIdleTimeMillis
				+ ", numTestsPerEvictionRun=" + numTestsPerEvictionRun + ", testOnCreate=" + testOnCreate
				+ ", testOnBorrow=" + testOnBorrow + ", testOnReturn=" + testOnReturn + ", testWhileIdle="
				+ testWhileIdle + "]";
	}

}
