package com.kanven.schedual.transport.client.api;

import com.kanven.schedual.network.protoc.ResponseProto.Response;

public interface Transform {

	<T> T transformResponse(Response response);

}
