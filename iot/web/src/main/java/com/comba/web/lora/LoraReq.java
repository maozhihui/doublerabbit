package com.comba.web.lora;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoraReq {

    private int altitude;

    private String channelConfigurationID;

    private String description;

    private int latitude;

    private int longitude;

    private String mac;

    private String name;

    private String networkServerID;

    private String organizationID;

    private Boolean ping;
}
