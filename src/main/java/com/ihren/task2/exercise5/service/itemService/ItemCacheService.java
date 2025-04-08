package com.ihren.task2.exercise5.service.itemService;

import com.ihren.task2.exercise5.annotation.Service;
import com.ihren.task2.exercise5.cache.GenericCache;
import com.ihren.task2.exercise5.model.ItemDocument;
import com.ihren.task2.exercise5.service.sainService.SainService;
import lombok.RequiredArgsConstructor;

@Service("ItemService")
@RequiredArgsConstructor
public class ItemCacheService implements ItemService {

    private final GenericCache<String, ItemDocument> itemCache;
    private final SainService nonCacheSainService;

    @Override
    public ItemDocument get(String key) {
        return itemCache.of(nonCacheSainService::get)
                .apply(key);
    }

}