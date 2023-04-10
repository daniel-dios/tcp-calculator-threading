package server.operation.impl;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class DivTest {

    public static Stream<Arguments> getValues() {
        return Stream.of(
                Arguments.of(1, 100, 0),
                Arguments.of(-1, 100, 0),
                Arguments.of(-100, 100, -1),
                Arguments.of(10, 2, 5),
                Arguments.of(127, 10, 12),
                Arguments.of(-1, 0, Long.MAX_VALUE),
                Arguments.of(0, 0, Long.MAX_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource("getValues")
    void shouldDiv(int a, int b, long expected) {
        final var div = new Div((byte) a, (byte) b);
        assertThat(div.solve())
                .isEqualTo(expected);
    }
}