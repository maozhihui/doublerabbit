package com.comba.server.dao.model.device;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/25 11:31
 **/
@Data
@Entity
@Table(name = "latest_data")
public class LatestDataEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="ID")
    private String id;

    @Column(name="ATTRIBUTE_ID")
    private String attributeId;

    @Column(name="INT_VALUE")
    private int intValue;

    @Column(name="FLOAT_VALUE")
    private float floatValue;

    @Column(name="STRING_VALUE")
    private String stringValue;

    @Column(name="VALUE")
    private String value;

    @Column(name="UPDATE_TIME")
    private LocalDateTime updateTime;

    @Column(name="DEV_ID")
    private String devId;

    @Column(name="ATTRIBUTE_NAME")
    private String attributeName;

    public LatestDataEntity(){}

    public LatestDataEntity(HistoryDataEntity historyDataEntity){
        this.attributeId = historyDataEntity.getAttributeId();
        this.intValue = historyDataEntity.getIntValue();
        this.floatValue = historyDataEntity.getFloatValue();
        this.stringValue = historyDataEntity.getStringValue();
        this.value = historyDataEntity.getValue();
        this.updateTime = historyDataEntity.getUpdateTime();
        this.devId = historyDataEntity.getDevId();
        this.attributeName = historyDataEntity.getTelemetryAttributeName();
    }
}
