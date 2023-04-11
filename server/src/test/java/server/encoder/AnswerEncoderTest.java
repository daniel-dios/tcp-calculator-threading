package server.encoder;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerEncoderTest {

    private final AnswerEncoder answerEncoder = new AnswerEncoder();

    public static Stream<Arguments> inputExpected() {
        return Stream.of(
                Arguments.of(-525, new byte[]{16, 8, -1, -1, -1, -1, -1, -1, -3, -13}),
                Arguments.of(260, new byte[]{16, 8, 0, 0, 0, 0, 0, 0, 1, 4})
        );
    }

    @ParameterizedTest
    @MethodSource("inputExpected")
    void shouldReturnExpected(
            final long input,
            final byte[] expected
    ) {

        assertThat(answerEncoder.encodeSuccess(input))
                .isEqualTo(expected);
    }
}