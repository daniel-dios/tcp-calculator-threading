package server.encoder;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static java.nio.ByteOrder.BIG_ENDIAN;

public class AnswerEncoder {

    public final int CAPACITY = 8;
    public final int ANSWER_TYPE = 16;
    public final int ANSWER_TYPE_POSITION = 0;
    public final int CAPACITY_POSITION = 1;
    public final int VALUE_POSITION = 2;

    public byte[] encodeSuccess(final long value) {
        final var valueInBigEndian = ByteBuffer.allocate(CAPACITY)
                .order(BIG_ENDIAN)
                .putLong(value)
                .array();

        final var output = new byte[10];
        output[ANSWER_TYPE_POSITION] = ANSWER_TYPE;
        output[CAPACITY_POSITION] = CAPACITY;

        System.arraycopy(valueInBigEndian, ANSWER_TYPE_POSITION, output, VALUE_POSITION, CAPACITY);

        return output;
    }

    public byte[] encodeFailure(
            final long value,
            final String text
    ) {
        final var accumulator = encodeSuccess(value);
        final var reason = encodeMessage(text);
        return ByteBuffer.allocate(2 + accumulator.length + reason.length)
                .put((byte) 10)
                .put((byte) (accumulator.length + reason.length))
                .put(reason)
                .put(accumulator)
                .array();
    }

    private byte[] encodeMessage(final String reason) {
        final var bytes = reason.getBytes(StandardCharsets.UTF_8);
        return ByteBuffer.allocate(2 + bytes.length)
                .put((byte) 11)
                .put((byte) bytes.length)
                .put(bytes)
                .array();
    }
}
