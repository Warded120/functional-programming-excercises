package com.ihren.task2.exercise5;

import com.ihren.task2.exercise5.cache.GenericCache;
import com.ihren.task2.exercise5.model.ItemDocument;
import com.ihren.task2.exercise5.service.SainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ItemCacheServiceTest {

    @InjectMocks
    private ItemCacheService itemCacheService;

    @Mock
    private GenericCache<String, ItemDocument> itemCache;

    @Mock
    private SainService nonCacheSainService;

    @Captor
    private ArgumentCaptor<Function<String, ItemDocument>> captor;

    @Test
    void should_ReturnItemDocument_when_inputIsValid() {
        // given
        String key = "key";
        ItemDocument expected = mock(ItemDocument.class);
        Function<String, ItemDocument> func = someKey -> expected;

        given(nonCacheSainService.get(key)).willReturn(expected);
        given(itemCache.of(captor.capture())).willReturn(func);

        // when
        ItemDocument actual = itemCacheService.get(key);

        // then
        assertEquals(expected, actual);

        Function<String, ItemDocument> capturedFunction = captor.getValue();
        assertNotNull(capturedFunction);

        ItemDocument resultFromCapturedFunc = capturedFunction.apply(key);
        assertEquals(expected, resultFromCapturedFunc);
    }
}