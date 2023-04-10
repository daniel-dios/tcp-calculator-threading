package server.operation.impl;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class SumTest {

    public static Stream<Arguments> getValues() {
        return Stream.of(
                Arguments.of(1, 100, 101),
                Arguments.of(-1, 100, 99),
                Arguments.of(-100, 100, 0),
                Arguments.of(10, 2, 12),
                Arguments.of(127, 10, 137),
                Arguments.of(-1, 0, -1),
                Arguments.of(-1, -10, -11),
                Arguments.of(0, 0, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getValues")
    void shouldDiv(int a, int b, long expected) {
        final var sum = new Sum((byte) a, (byte) b);
        assertThat(sum.solve())
                .isEqualTo(expected);
    }
}