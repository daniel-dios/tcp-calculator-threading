package server.encoder;

import java.nio.ByteBuffer;

import static java.nio.ByteOrder.BIG_ENDIAN;

public class AnswerEncoder {

    public final int CAPACITY = 8;
    public final int ANSWER_TYPE = 16;
    public final int ANSWER_TYPE_POSITION = 0;
    public final int CAPACITY_POSITION = 1;
    public final int VALUE_POSITION = 2;

    public byte[] encode(final long value) {
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
}
