package com.ihren.task2.exercise5.service.itemService;

import com.ihren.task2.exercise5.cache.GenericCache;
import com.ihren.task2.exercise5.model.ItemDocument;
import com.ihren.task2.exercise5.service.sainService.SainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ItemCacheServiceTest {

    @InjectMocks
    private ItemCacheService itemCacheService;

    @Mock
    private GenericCache<String, ItemDocument> itemCache;

    @Mock
    SainService nonCacheSainService;

    @Test
    void should_ReturnItemDocument_when_inputIsValid() {
        //given
        String key = "key";
        ItemDocument expected = mock(ItemDocument.class);
        Function<String, ItemDocument> func = nonCacheSainService::get;

        given(itemCache.of(any())).willReturn(func);
        given(func.apply(key)).willReturn(expected);

        //when
        ItemDocument actual = itemCacheService.get(key);

        //then
        assertEquals(expected, actual);
        then(itemCache).should().of(any(Function.class));
        then(nonCacheSainService).should().get(key);
    }

    @Test
    void should_NullPointerException_when_ItemCacheOfReturnNull() {
        //given
        String key = "key";

        given(itemCache.of(any(Function.class))).willReturn(null);

        //when
        //then
        assertThrows(NullPointerException.class, () -> itemCacheService.get(key));
    }

    @Test
    void should_ReturnNull_when_ApplyReturnNull() {
        //given
        String key = "key";
        Function<String, ItemDocument> func = nonCacheSainService::get;

        given(itemCache.of(any())).willReturn(func);
        given(func.apply(key)).willReturn(null);

        //when
        ItemDocument actual = itemCacheService.get(key);

        //then
        assertNull(actual);
        then(itemCache).should().of(any(Function.class));
        then(nonCacheSainService).should().get(key);
    }
}