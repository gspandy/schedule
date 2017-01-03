package com.kanven.schedual.transport.client.api;

import com.kanven.schedual.command.Command;
import com.kanven.schedual.network.protoc.RequestProto.Request;
import com.kanven.schedual.network.protoc.ResponseProto.Response;

public interface Transform<C> {

	Request transformRequest(Command<C> command);

	<T> T transformResponse(Response response);

}
