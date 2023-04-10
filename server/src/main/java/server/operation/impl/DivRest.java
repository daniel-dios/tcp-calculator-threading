package server.operation.impl;

import server.operation.Operation;

public class DivRest implements Operation {
    private final byte a;
    private final byte b;

    public DivRest(final byte a, final byte b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public long solve() {
        if (b == 0) {
            return 0;
        }
        return a % b;
    }

    @Override
    public String toReadableFormat() {
        return a + "%" + b;
    }
}
