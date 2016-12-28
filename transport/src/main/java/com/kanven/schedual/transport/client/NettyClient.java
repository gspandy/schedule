package com.kanven.schedual.transport.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class NettyClient implements Client {

	private Integer minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

	private Integer maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;

	private Integer maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;

	private Long maxWaitMillis = GenericObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS;

	private Long minEvictableIdleTimeMillis = GenericObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

	private Integer numTestsPerEvictionRun = GenericObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

	private Boolean testOnCreate = GenericObjectPoolConfig.DEFAULT_TEST_ON_CREATE;

	private Boolean testOnBorrow = GenericObjectPoolConfig.DEFAULT_TEST_ON_BORROW;

	private Boolean testOnReturn = GenericObjectPoolConfig.DEFAULT_TEST_ON_RETURN;

	private Boolean testWhileIdle = GenericObjectPoolConfig.DEFAULT_TEST_WHILE_IDLE;

	private Long connectTimeout = 3000L;

	private Integer threads = -1;

	private String ip;

	private Integer port;

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

	public boolean isClosed() {
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

	public void setThreads(Integer threads) {
		if (threads != null) {
			this.threads = threads;
		}
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(Integer minIdle) {
		if (minIdle != null) {
			this.minIdle = minIdle;
		}
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(Integer maxIdle) {
		if (maxIdle != null) {
			this.maxIdle = maxIdle;
		}
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		if (maxTotal != null) {
			this.maxTotal = maxTotal;
		}
	}

	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(Long maxWaitMillis) {
		if (maxWaitMillis != null) {
			this.maxWaitMillis = maxWaitMillis;
		}
	}

	public long getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
		if (minEvictableIdleTimeMillis != null) {
			this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		}
	}

	public int getNumTestsPerEvictionRun() {
		return numTestsPerEvictionRun;
	}

	public void setNumTestsPerEvictionRun(Integer numTestsPerEvictionRun) {
		if (numTestsPerEvictionRun != null) {
			this.numTestsPerEvictionRun = numTestsPerEvictionRun;
		}
	}

	public boolean isTestOnCreate() {
		return testOnCreate;
	}

	public void setTestOnCreate(Boolean testOnCreate) {
		if (testOnCreate != null) {
			this.testOnCreate = testOnCreate;
		}
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(Boolean testOnBorrow) {
		if (testOnBorrow != null) {
			this.testOnBorrow = testOnBorrow;
		}
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(Boolean testOnReturn) {
		if (testOnReturn != null) {
			this.testOnReturn = testOnReturn;
		}
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(Boolean testWhileIdle) {
		if (testWhileIdle != null) {
			this.testWhileIdle = testWhileIdle;
		}
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(Integer port) {
		if (port != null) {
			this.port = port;
		}
	}

	public void setConnectTimeout(Long connectTimeout) {
		if (connectTimeout != null) {
			this.connectTimeout = connectTimeout;
		}
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

	public void returnChannel(NettyChannel nettyChannel) {
		pool.returnObject(nettyChannel);
	}

}