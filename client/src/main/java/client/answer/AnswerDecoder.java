package client.answer;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class AnswerDecoder {
    public String decode(final byte[] buffer) {
        return String.valueOf(
                ByteBuffer
                        .wrap(Arrays.copyOfRange(buffer, 2, buffer.length))
                        .getLong()
        );
    }
}
