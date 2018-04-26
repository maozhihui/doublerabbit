package com.comba.server.extensions.api.plugins.msg;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Andrew Shvayka
 */
@RequiredArgsConstructor
@ToString
public class FromDeviceRpcResponse {
    @Getter
    private final UUID id;
    private final String response;
    private final RpcError error;

    public Optional<String> getResponse() {
        return Optional.ofNullable(response);
    }

    public Optional<RpcError> getError() {
        return Optional.ofNullable(error);
    }

}
