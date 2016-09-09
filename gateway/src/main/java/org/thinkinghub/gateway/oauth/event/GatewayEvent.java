package org.thinkinghub.gateway.oauth.event;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public abstract class GatewayEvent {
    private ZonedDateTime occuredOn = ZonedDateTime.now();
}
