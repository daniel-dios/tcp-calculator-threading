package server.operation.impl;

import server.operation.Operation;

public class Subs implements Operation {
    private final byte a;
    private final byte b;

    public Subs(final byte a, final byte b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public long solve() {
        return a - b;
    }

    @Override
    public String toReadableFormat() {
        return a + "-" + b;
    }
}
