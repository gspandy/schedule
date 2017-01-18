package com.kanven.schedual.core.clustor.loadbalance;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kanven.schedual.core.clustor.unitls.IPUtil;
import com.kanven.schedual.transport.client.api.Client;

public class IPHashLoadBalance<C> extends AbstractLoadBalance<C> {

	@Override
	protected Client<C> onSelect() {
		List<Client<C>> clients = getClients();
		int pos = pos();
		if (pos < 0) {
			return null;
		}
		int index = pos % clients.size();
		return clients.get(index);
	}

	public int pos() {
		String ip = IPUtil.getIp();
		if (StringUtils.isEmpty(ip)) {
			return -1;
		}
		return Math.abs(ip.hashCode());
	}

}
