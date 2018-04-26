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
package com.comba.server.dao.service;

import java.util.List;
import java.util.UUID;

import com.comba.server.common.data.page.TextPageLink;

import com.comba.server.dao.model.BaseEntity;

public abstract class PaginatedRemover<I, E extends BaseEntity<?>> {

    public void removeEntitites(I id) {
        TextPageLink pageLink = new TextPageLink(100);
        boolean hasNext = true;
        while (hasNext) {
            List<E> entities = findEntities(id, pageLink);
            for (E entity : entities) {
                removeEntity(entity);
            }
            hasNext = entities.size() == pageLink.getLimit();
            if (hasNext) {
                int index = entities.size()-1;
                UUID idOffset = entities.get(index).getId();
                pageLink.setIdOffset(idOffset);
            }
        } 
    }
    
    protected abstract List<E> findEntities(I id, TextPageLink pageLink);
    
    protected abstract void removeEntity(E entity);
    
}
