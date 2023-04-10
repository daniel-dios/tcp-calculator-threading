package client.operation;

import client.operation.impl.Div;
import client.operation.impl.DivRest;
import client.operation.impl.Fact;
import client.operation.impl.Mult;
import client.operation.impl.Subs;
import client.operation.impl.Sum;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class OperationReaderTest {

    private final OperationReader operationReader = new OperationReader();

    public static Stream<Arguments> getExpected() {
        return Stream.of(
                Arguments.of("1 x 2", new Mult(1, 2)),
                Arguments.of("0 + 2", new Sum(0, 2)),
                Arguments.of("2 - 1", new Subs(2, 1)),
                Arguments.of("3 / 1", new Div(3, 1)),
                Arguments.of("6 % 3", new DivRest(6, 3)),
                Arguments.of("3 !", new Fact(3)),
                Arguments.of("1x2", new Mult(1, 2)),
                Arguments.of("0+2", new Sum(0, 2)),
                Arguments.of("2-1", new Subs(2, 1)),
                Arguments.of("3/1", new Div(3, 1)),
                Arguments.of("6%3", new DivRest(6, 3)),
                Arguments.of("3!", new Fact(3))
        );
    }

    public static Stream<Arguments> getWrongOverRange() {
        return Stream.of(
                Arguments.of("1 x 128"),
                Arguments.of("128 + 2"),
                Arguments.of("2 - 128"),
                Arguments.of("128 / 1"),
                Arguments.of("6 % -3"),
                Arguments.of("128 !"),
                Arguments.of("128x2"),
                Arguments.of("0+211"),
                Arguments.of("-2-1"),
                Arguments.of("200/1"),
                Arguments.of("6%300"),
                Arguments.of("128!"),
                Arguments.of("-1 x 2"),
                Arguments.of("0 + -2"),
                Arguments.of("-2 - 1"),
                Arguments.of("-03 / 1"),
                Arguments.of("-6 % 3"),
                Arguments.of("-3 !"),
                Arguments.of("-1x2"),
                Arguments.of("0+-2"),
                Arguments.of("-2-1"),
                Arguments.of("-3/1"),
                Arguments.of("6%-3"),
                Arguments.of("-3!")
        );
    }

    public static Stream<Arguments> getWrong() {
        return Stream.of(
                Arguments.of("1 x x2"),
                Arguments.of(" + 2"),
                Arguments.of("2 -- 1"),
                Arguments.of("3 / 1 $"),
                Arguments.of("%6 % 3"),
                Arguments.of("3 !! 32"),
                Arguments.of("1x+2"),
                Arguments.of("0+2 3"),
                Arguments.of("2-1+"),
                Arguments.of("3/1x"),
                Arguments.of("6%%%%%3"),
                Arguments.of("-"),
                Arguments.of("3 1 x"),
                Arguments.of("x 6 2"),
                Arguments.of("3 2")
        );
    }

    @ParameterizedTest
    @MethodSource("getExpected")
    void shouldReturnExpected(final String input, final Operation expected) {
        final var actual = operationReader.parse(input);

        assertThat(actual)
                .isPresent();
        assertThat(actual.get())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getWrong")
    void shouldReturnEmpty(final String input) {
        final var actual = operationReader.parse(input);

        assertThat(actual)
                .isEmpty();
    }

    @ParameterizedTest
    @MethodSource("getWrongOverRange")
    void shouldReturnEmptyWhenOutOfRange(final String input) {
        final var actual = operationReader.parse(input);

        assertThat(actual)
                .isEmpty();
    }
}