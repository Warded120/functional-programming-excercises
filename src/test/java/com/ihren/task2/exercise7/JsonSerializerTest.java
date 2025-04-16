package com.ihren.task2.exercise7;

import com.ihren.task2.exercise7.exception.SerializingException;
import com.ihren.task2.exercise7.mapper.ObjectMapper;
import com.ihren.task2.exercise7.util.JacksonConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonSerializerTest {

    @InjectMocks
    private JsonSerializer jsonSerializer;

    @Mock
    private ObjectMapper objectMapper;

    @Nested
    class ConfigureTests {
        private MockedStatic<JacksonConfig> jacksonConfigMockedStatic;

        @BeforeEach
        void setUp() {
            jacksonConfigMockedStatic = mockStatic(JacksonConfig.class);
        }

        @AfterEach
        void tearDown() {
            jacksonConfigMockedStatic.close();
        }

        @Test
        void should_BeOK_when_everythingIsOK() {
            //given
            Map<String, ?> configs = new HashMap<>();
            boolean isKey = true;

            try (MockedConstruction<ObjectMapper> mocked = mockConstruction(ObjectMapper.class)) {
                //when
                jsonSerializer.configure(configs, isKey);

                //then
                assertEquals(1, mocked.constructed().size());

                ObjectMapper mockedObjectMapper = mocked.constructed().get(0);
                jacksonConfigMockedStatic.verify(() -> JacksonConfig.initConfigs(mockedObjectMapper));
            }
        }
    }

    @Test
    void should_ReturnByteArray_when_InputIsValid() {
        //given
        String data = "data";
        byte[] expected = "Data".getBytes();

        String topic = "topic";
        given(objectMapper.writeValueAsBytes(data)).willReturn(expected);

        //when
        byte[] actual = jsonSerializer.serialize(topic, data);

        //then
        assertArrayEquals(expected, actual);
    }

    @Test
    void should_ReturnNull_when_InputIsNull() {
        //when
        //then
        assertNull(jsonSerializer.serialize(null, null));
    }

    @Test
    void should_ReturnNull_whenObjectMapperThrowsRuntimeException() {
        //given
        String topic = "topic";
        Object data = mock(Object.class);

        given(objectMapper.writeValueAsBytes(data)).willThrow(RuntimeException.class);

        //when
        //then
        assertThrows(SerializingException.class, () -> jsonSerializer.serialize(topic, data));
    }
}