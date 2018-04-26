/**
 * Copyright © 2016-2017 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.comba.server.dao.attributes;

import com.comba.server.dao.model.ModelConstants;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.comba.server.common.data.id.EntityId;
import com.comba.server.common.data.kv.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.comba.server.dao.model.ModelConstants.*;

/**
 * @author Andrew Shvayka
 */
@Component
@Slf4j
public class BaseAttributesDao implements AttributesDao {

    //private PreparedStatement saveStmt;

    @PostConstruct
    public void init() {
        //super.startExecutor();
    }

    @PreDestroy
    public void stop() {
        //super.stopExecutor();
    }

    @Override
    public ListenableFuture<Optional<AttributeKvEntry>> find(EntityId entityId, String attributeType, String attributeKey) {
        /*Select.Where select = select().from(ATTRIBUTES_KV_CF)
                .where(eq(ENTITY_TYPE_COLUMN, entityId.getEntityType()))
                .and(eq(ENTITY_ID_COLUMN, entityId.getId()))
                .and(eq(ATTRIBUTE_TYPE_COLUMN, attributeType))
                .and(eq(ATTRIBUTE_KEY_COLUMN, attributeKey));
        log.trace("Generated query [{}] for entityId {} and key {}", select, entityId, attributeKey);
        return Futures.transform(executeAsyncRead(select), (Function<? super ResultSet, ? extends Optional<AttributeKvEntry>>) input ->
                        Optional.ofNullable(convertResultToAttributesKvEntry(attributeKey, input.one()))
                , readResultsProcessingExecutor);*/
    	return null;
    }

    @Override
    public ListenableFuture<List<AttributeKvEntry>> find(EntityId entityId, String attributeType, Collection<String> attributeKeys) {
        /*List<ListenableFuture<Optional<AttributeKvEntry>>> entries = new ArrayList<>();
        attributeKeys.forEach(attributeKey -> entries.add(find(entityId, attributeType, attributeKey)));
        return Futures.transform(Futures.allAsList(entries), (Function<List<Optional<AttributeKvEntry>>, ? extends List<AttributeKvEntry>>) input -> {
            List<AttributeKvEntry> result = new ArrayList<>();
            input.stream().filter(opt -> opt.isPresent()).forEach(opt -> result.add(opt.get()));
            return result;
        }, readResultsProcessingExecutor);*/
    	return null;
    }


    @Override
    public ListenableFuture<List<AttributeKvEntry>> findAll(EntityId entityId, String attributeType) {
        /*Select.Where select = select().from(ATTRIBUTES_KV_CF)
                .where(eq(ENTITY_TYPE_COLUMN, entityId.getEntityType()))
                .and(eq(ENTITY_ID_COLUMN, entityId.getId()))
                .and(eq(ATTRIBUTE_TYPE_COLUMN, attributeType));
        log.trace("Generated query [{}] for entityId {} and attributeType {}", select, entityId, attributeType);
        return Futures.transform(executeAsyncRead(select), (Function<? super ResultSet, ? extends List<AttributeKvEntry>>) input ->
                        convertResultToAttributesKvEntryList(input)
                , readResultsProcessingExecutor);*/
    	return null;
    }

    /*@Override
    public ResultSetFuture save(EntityId entityId, String attributeType, AttributeKvEntry attribute) {
        BoundStatement stmt = getSaveStmt().bind();
        stmt.setString(0, entityId.getEntityType().name());
        stmt.setUUID(1, entityId.getId());
        stmt.setString(2, attributeType);
        stmt.setString(3, attribute.getKey());
        stmt.setLong(4, attribute.getLastUpdateTs());
        stmt.setString(5, attribute.getStrValue().orElse(null));
        if (attribute.getBooleanValue().isPresent()) {
            stmt.setBool(6, attribute.getBooleanValue().get());
        } else {
            stmt.setToNull(6);
        }
        if (attribute.getLongValue().isPresent()) {
            stmt.setLong(7, attribute.getLongValue().get());
        } else {
            stmt.setToNull(7);
        }
        if (attribute.getDoubleValue().isPresent()) {
            stmt.setDouble(8, attribute.getDoubleValue().get());
        } else {
            stmt.setToNull(8);
        }
        return executeAsyncWrite(stmt);
    }*/

    /*@Override
    public ListenableFuture<List<ResultSet>> removeAll(EntityId entityId, String attributeType, List<String> keys) {
        List<ResultSetFuture> futures = keys.stream().map(key -> delete(entityId, attributeType, key)).collect(Collectors.toList());
        return Futures.allAsList(futures);
    }*/

    /*private ResultSetFuture delete(EntityId entityId, String attributeType, String key) {
        Statement delete = QueryBuilder.delete().all().from(ModelConstants.ATTRIBUTES_KV_CF)
                .where(eq(ENTITY_TYPE_COLUMN, entityId.getEntityType()))
                .and(eq(ENTITY_ID_COLUMN, entityId.getId()))
                .and(eq(ATTRIBUTE_TYPE_COLUMN, attributeType))
                .and(eq(ATTRIBUTE_KEY_COLUMN, key));
        log.debug("Remove request: {}", delete.toString());
        return getSession().executeAsync(delete);
    }*/

    /*private PreparedStatement getSaveStmt() {
        if (saveStmt == null) {
            saveStmt = getSession().prepare("INSERT INTO " + ModelConstants.ATTRIBUTES_KV_CF +
                    "(" + ENTITY_TYPE_COLUMN +
                    "," + ENTITY_ID_COLUMN +
                    "," + ATTRIBUTE_TYPE_COLUMN +
                    "," + ATTRIBUTE_KEY_COLUMN +
                    "," + LAST_UPDATE_TS_COLUMN +
                    "," + ModelConstants.STRING_VALUE_COLUMN +
                    "," + ModelConstants.BOOLEAN_VALUE_COLUMN +
                    "," + ModelConstants.LONG_VALUE_COLUMN +
                    "," + ModelConstants.DOUBLE_VALUE_COLUMN +
                    ")" +
                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        return saveStmt;
    }*/

    /*private AttributeKvEntry convertResultToAttributesKvEntry(String key, Row row) {
        AttributeKvEntry attributeEntry = null;
        if (row != null) {
            long lastUpdateTs = row.get(LAST_UPDATE_TS_COLUMN, Long.class);
            attributeEntry = new BaseAttributeKvEntry(BaseTimeseriesDao.toKvEntry(row, key), lastUpdateTs);
        }
        return attributeEntry;
    }*/

    /*private List<AttributeKvEntry> convertResultToAttributesKvEntryList(ResultSet resultSet) {
        List<Row> rows = resultSet.all();
        List<AttributeKvEntry> entries = new ArrayList<>(rows.size());
        if (!rows.isEmpty()) {
            rows.forEach(row -> {
                String key = row.getString(ModelConstants.ATTRIBUTE_KEY_COLUMN);
                AttributeKvEntry kvEntry = convertResultToAttributesKvEntry(key, row);
                if (kvEntry != null) {
                    entries.add(kvEntry);
                }
            });
        }
        return entries;
    }*/
}
