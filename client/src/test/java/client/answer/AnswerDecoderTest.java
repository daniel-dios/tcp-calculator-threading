package client.answer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.common.ArgumentUtils.toByteArray;

class AnswerDecoderTest {

    private final AnswerDecoder answerDecoder = new AnswerDecoder();

    @Test
    void shouldDecodeType16() {
        final byte[] answer = toByteArray( 255, 255, 255, 255, 255, 255, 253, 243);

        assertThat(answerDecoder.decodeNumber16(answer))
                .isEqualTo("-525");
    }

    @Test
    void shouldDecodeType10WithValue() {
        byte[] answer = toByteArray( 16, 8, 255, 255, 255, 255, 255, 255, 253, 243);

        assertThat(answerDecoder.decodeVariable(answer))
                .isEqualTo("-525");
    }

    @Test
    void shouldDecodeType10WithContent() {
        byte[] answer = toByteArray(
                10, 31, 11, 19, 67, 97, 110, 32, 110, 111, 116, 32, 100, 105, 118, 105, 100,
                101, 32, 98, 121, 32, 48, 16, 8, 0, 0, 0, 0, 0, 0, 0, 5
        );

        assertThat(answerDecoder.decodeVariable(answer))
                .isEqualTo("Can not divide by 05");
    }

    @Test
    void shouldDecodeType10WithContentSecondOption() {
        byte[] answer = toByteArray(
                10, 31, 16, 8, 0, 0, 0, 0, 0, 0, 0, 5, 11, 19, 67, 97, 110, 32, 110, 111, 116,
                32, 100, 105, 118, 105, 100, 101, 32, 98, 121, 32, 48
        );

        assertThat(answerDecoder.decodeVariable(answer))
                .isEqualTo("5Can not divide by 0");
    }
}