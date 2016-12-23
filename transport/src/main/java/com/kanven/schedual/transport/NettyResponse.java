package com.kanven.schedual.transport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.kanven.schedual.network.protoc.ResponseProto.Response;

final class NettyResponse {

	private String requestId;

	private long createTime = System.currentTimeMillis();

	private long timeout;

	private Response response;

	private Lock lock = new ReentrantLock();

	private Condition condition = lock.newCondition();

	public NettyResponse(String requestId, long timeout) {
		this.requestId = requestId;
		this.timeout = timeout;
	}

	void setResult(Response response) {
		lock.lock();
		try {
			this.response = response;
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public Response getValue() {
		lock.lock();
		System.out.println(Thread.currentThread().getName());
		try {
			if (response == null) {
				if (timeout <= 0) {
					try {
						condition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					long times = timeout - (System.currentTimeMillis() - createTime);
					if (times > 0) {
						try {
							condition.await(times, TimeUnit.MILLISECONDS);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return response;
		} finally {
			lock.unlock();
		}
	}

	public String getRequestId() {
		return requestId;
	}

	void cancel() {
		lock.lock();
		try {
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

}
