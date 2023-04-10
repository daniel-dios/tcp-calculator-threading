package server.operation.impl;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static java.lang.Long.MAX_VALUE;
import static org.assertj.core.api.Assertions.assertThat;


class FactTest {

    public static Stream<Arguments> getValues() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(1, 1),
                Arguments.of(5, 120),
                Arguments.of(10, 3628800),
                Arguments.of(20, 9223372036854775807L),
                Arguments.of(21, MAX_VALUE),
                Arguments.of(127, MAX_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource("getValues")
    void shouldDiv(int a, long expected) {
        final var fact = new Fact((byte) a);
        assertThat(fact.solve())
                .isEqualTo(expected);
    }
}