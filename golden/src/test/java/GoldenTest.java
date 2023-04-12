import client.answer.AnswerDecoder;
import org.junit.jupiter.api.Test;
import server.encoder.AnswerEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class GoldenTest {

    private final AnswerEncoder encoder = new AnswerEncoder();
    private final AnswerDecoder decoder = new AnswerDecoder();

    @Test
    void shouldEncodeDecodeSuccess() {
        final var bytes = encoder.encodeNumber16(10);
        final var actual = decoder.decodeVariable(bytes);

        assertThat(actual)
                .isEqualTo("Type 16 with accumulator [10]");
    }

    @Test
    void shouldEncodeDecodeFailure() {
        final var bytes = encoder.encode(10, "Can't divide by 0.");
        final var actual = decoder.decodeVariable(bytes);

        assertThat(actual)
                .isEqualTo("Type 10 with message [\n\t" +
                        "Type 11 with message [Can't divide by 0.],\n\t" +
                        "Type 16 with accumulator [10]\n" +
                        "].");
    }
}
