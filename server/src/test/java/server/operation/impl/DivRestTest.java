package server.operation.impl;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class DivRestTest {

    public static Stream<Arguments> getValues() {
        return Stream.of(
                Arguments.of(1, 100, 1),
                Arguments.of(-1, 100, -1),
                Arguments.of(-100, 100, 0),
                Arguments.of(10, 10, 0),
                Arguments.of(127, 10, 7),
                Arguments.of(-1, 0, 0),
                Arguments.of(0, 0, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getValues")
    void shouldDiv(int a, int b, long expected) {
        final var divRest = new DivRest((byte) a, (byte) b);
        assertThat(divRest.solve())
                .isEqualTo(expected);
    }
}