package server;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccumulatorTest {

    @Test
    void shouldAccumulate() {
        final var accumulator = new Accumulator(0);

        assertThat(accumulator.getValue())
                .isEqualTo(0);

        accumulator.accumulate(100000);

        assertThat(accumulator.getValue())
                .isEqualTo(100000);

        accumulator.accumulate(-1000);

        assertThat(accumulator.getValue())
                .isEqualTo(99000);

        accumulator.accumulate(Long.MAX_VALUE);

        assertThat(accumulator.getValue())
                .isEqualTo(Long.MAX_VALUE);

        accumulator.accumulate(Long.MIN_VALUE);

        assertThat(accumulator.getValue())
                .isEqualTo(-1);

        accumulator.accumulate(Long.MIN_VALUE);

        assertThat(accumulator.getValue())
                .isEqualTo(Long.MIN_VALUE);
    }
}