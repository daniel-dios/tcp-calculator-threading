package server.operation.impl;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class SubsTest {

    public static Stream<Arguments> getValues() {
        return Stream.of(
                Arguments.of(1, 100, -99),
                Arguments.of(0, 100, -100),
                Arguments.of(-100, 100, -200),
                Arguments.of(10, 10, 0),
                Arguments.of(10, -100, 110),
                Arguments.of(-1, 0, -1),
                Arguments.of(-1, -10, 9),
                Arguments.of(0, 0, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("getValues")
    void shouldDiv(int a, int b, long expected) {
        final var subs = new Subs((byte) a, (byte) b);
        assertThat(subs.solve())
                .isEqualTo(expected);
    }
}