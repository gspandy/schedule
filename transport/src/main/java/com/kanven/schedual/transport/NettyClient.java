package com.kanven.schedual.transport;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class NettyClient implements Client {

	private int minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

	private int maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;

	private int maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;

	private long maxWaitMillis = GenericObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS;

	private long minEvictableIdleTimeMillis = GenericObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

	private int numTestsPerEvictionRun = GenericObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

	private boolean testOnCreate = GenericObjectPoolConfig.DEFAULT_TEST_ON_CREATE;

	private boolean testOnBorrow = GenericObjectPoolConfig.DEFAULT_TEST_ON_BORROW;

	private boolean testOnReturn = GenericObjectPoolConfig.DEFAULT_TEST_ON_RETURN;

	private boolean testWhileIdle = GenericObjectPoolConfig.DEFAULT_TEST_WHILE_IDLE;

	private long connectTimeout = 3000;

	private int threads = -1;

	private String ip;

	private int port;

	private GenericObjectPool<NettyChannel> pool;

	private NettyBootstrap bootstrap = null;

	private boolean closed = false;

	public NettyClient() {
	}

	public NettyClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
		check();
	}

	private synchronized GenericObjectPool<NettyChannel> createPool() {
		if (isClosed()) {
			throw new RuntimeException("已经关闭，不可用！");
		}
		if (pool == null) {
			check();
			bootstrap = new NettyBootstrap(ip, port, threads, connectTimeout);
			pool = new GenericObjectPool<NettyChannel>(new NettyChannelFactory(bootstrap), initConfigs());
			for (int i = 0; i < minIdle; i++) {
				try {
					pool.addObject();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return pool;
	}

	private void check() {
		if (StringUtils.isEmpty(ip)) {
			throw new IllegalArgumentException("ip地址不能为空！");
		}
		if (port <= 0) {
			throw new IllegalArgumentException("端口不合法！");
		}
	}

	private GenericObjectPoolConfig initConfigs() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxTotal);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		config.setMinIdle(minIdle);
		config.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnCreate(testOnCreate);
		config.setTestOnReturn(testOnReturn);
		config.setTestWhileIdle(testWhileIdle);
		return config;
	}

	public synchronized NettyChannel getChannel() throws Exception {
		return createPool().borrowObject();
	}

	public  boolean isClosed() {
		return closed;
	}

	public synchronized void close() {
		if (isClosed()) {
			return;
		}
		bootstrap.close();
		pool.close();
		closed = true;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public long getConnectTimeout() {
		return connectTimeout;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public int getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
		this.numTestsPerEvictionRun = numTestsPerEvictionRun;
	}

	public boolean isTestOnCreate() {
		return testOnCreate;
	}

	public void setTestOnCreate(boolean testOnCreate) {
		this.testOnCreate = testOnCreate;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setConnectTimeout(long connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	@Override
	public String toString() {
		return "NettyClient [minIdle=" + minIdle + ", maxIdle=" + maxIdle + ", maxTotal=" + maxTotal
				+ ", maxWaitMillis=" + maxWaitMillis + ", minEvictableIdleTimeMillis=" + minEvictableIdleTimeMillis
				+ ", numTestsPerEvictionRun=" + numTestsPerEvictionRun + ", testOnCreate=" + testOnCreate
				+ ", testOnBorrow=" + testOnBorrow + ", testOnReturn=" + testOnReturn + ", testWhileIdle="
				+ testWhileIdle + ", connectTimeout=" + connectTimeout + ", threads=" + threads + ", ip=" + ip
				+ ", port=" + port + ", pool=" + pool + "]";
	}

}