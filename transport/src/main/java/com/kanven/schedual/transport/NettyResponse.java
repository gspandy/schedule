package com.kanven.schedual.transport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.kanven.schedual.network.protoc.ResponseProto.Response;

final class NettyResponse {

	static enum Status {
		DOING, DONE, CANCELED;

		public boolean isDoing() {
			return this == DOING;
		}

		public boolean isDone() {
			return this == DONE;
		}

		public boolean isCanceled() {
			return this == Status.CANCELED;
		}

	}

	private Status status = Status.DOING;

	private String requestId;

	private long createTime = System.currentTimeMillis();

	private long timeout;

	private Response response;

	private Lock lock = new ReentrantLock();

	private Condition condition = lock.newCondition();

	public NettyResponse(String requestId, long timeout) {
		this.requestId = requestId;
		if (timeout <= 0) {
			this.timeout = Constants.DEFAULT_TIME_OUT;
		} else {
			this.timeout = timeout;
		}
	}

	void callback(Response response) {
		lock.lock();
		try {
			this.status = Status.DONE;
			this.response = response;
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	public Response getValue() {
		lock.lock();
		try {
			if (response == null) {
				long times = timeout - (System.currentTimeMillis() - createTime);
				if (times > 0) {
					while (true) {
						try {
							condition.await(times, TimeUnit.MILLISECONDS);
						} catch (InterruptedException e) {
						}
						if (!status.isDoing()) {
							break;
						}
						times = timeout - (System.currentTimeMillis() - createTime);
						if (times <= 0) {
							break;
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
			this.status = Status.CANCELED;
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

}
