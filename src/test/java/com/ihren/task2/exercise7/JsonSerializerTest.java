package com.ihren.task2.exercise7;

import com.ihren.task2.exercise7.exception.SerializingException;
import com.ihren.task2.exercise7.mapper.ObjectMapper;
import com.ihren.task2.exercise7.util.JacksonConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonSerializerTest {

    @InjectMocks
    private JsonSerializer jsonSerializer;

    @Mock
    private ObjectMapper objectMapper;

    private MockedStatic<JacksonConfig> jacksonConfigMockedStatic;

    @Nested
    class ConfigureTests {

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

            //when
            jsonSerializer.configure(configs, isKey);

            //then
            jacksonConfigMockedStatic.verify(() -> JacksonConfig.initConfigs(any(ObjectMapper.class)));
        }

        @Test
        void should_ThrowRuntimeException_when_jacksonConfigThrowsRuntimeException() {
            //given
            Map<String, ?> configs = new HashMap<>();
            boolean isKey = true;

            jacksonConfigMockedStatic.when(() -> JacksonConfig.initConfigs(any(ObjectMapper.class))).thenThrow(RuntimeException.class);

            //when
            assertThrows(RuntimeException.class, () -> jsonSerializer.configure(configs, isKey));

            //then
            jacksonConfigMockedStatic.verify(() -> JacksonConfig.initConfigs(any(ObjectMapper.class)));
        }
    }

    @Nested
    class SerializeTests {

        private static Stream<Arguments> dataProvider() {
            return Stream.of(
                    Arguments.of("Data", "Data".getBytes()),
                    Arguments.of(12, String.valueOf(12).getBytes()),
                    Arguments.of(120000000000L, String.valueOf(120000000000L).getBytes()),
                    Arguments.of(12.0123456789d, String.valueOf(12.0123456789d).getBytes())
            );
        }

        @ParameterizedTest
        @MethodSource("dataProvider")
        <T> void should_ReturnByteArray_when_InputIsValid(T data, byte[] expected) {
            //given
            String topic = "topic";
            given(objectMapper.writeValueAsBytes(data)).willReturn(expected);

            //when
            byte[] actual = jsonSerializer.serialize(topic, data);

            //then
            assertArrayEquals(expected, actual);
            then(objectMapper).should().writeValueAsBytes(data);
        }

        @Test
        void should_ReturnNull_when_InputIsNull() {
            //when
            //then
            assertNull(jsonSerializer.serialize(null, null));
        }

        @Test
        void should_ReturnNull_whenObjectMapperReturnsNull() {
            //given
            String topic = "topic";
            Object data = mock(Object.class);

            given(objectMapper.writeValueAsBytes(data)).willReturn(null);

            //when
            //then
            assertNull(jsonSerializer.serialize(topic, data));
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
}