package com.kanven.schedual.core.clustor.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.kanven.schedual.core.Job;
import com.kanven.schedual.core.clustor.Clustor;
import com.kanven.schedual.core.clustor.LoadBalance;
import com.kanven.schedual.network.protoc.MessageTypeProto.MessageType;
import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.network.protoc.RequestProto.Task;
import com.kanven.schedual.network.protoc.ResponseProto.Response;
import com.kanven.schedual.transport.client.Client;
import com.kanven.schedual.transport.client.NettyChannel;

public class DefaultClustorImpl implements Clustor {

	private LoadBalance loadBalance;

	public void refresh(List<Client> clients) {
		loadBalance.refresh(clients);
	}

	public void setLoadBalance(LoadBalance loadBalance) {
		this.loadBalance = loadBalance;
	}

	public void alloc(Job job) {
		Request.Builder rb = Request.newBuilder();
		rb.setRequestId(getRequestId());
		rb.setType(MessageType.TASK);
		Task.Builder tb = Task.newBuilder();
		tb.setId(job.getId());
		tb.setGroup(job.getGroup());
		tb.setName(job.getName());
		tb.setUrl(job.getUrl());
		tb.setCron(job.getCron());
		Date startTime = job.getStartTime();
		if(startTime != null) {
			tb.setStartTime(startTime.getTime());
		}
		
		rb.setTask(tb.build());
		Client client = loadBalance.select();
		NettyChannel channel = null;
		try {
			channel = client.getChannel();
			Response response = channel.request(rb.build());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				client.returnChannel(channel);
			}
		}
	}
	
	public static String getRequestId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

}
