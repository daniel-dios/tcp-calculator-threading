package client.operation;

import client.operation.impl.Div;
import client.operation.impl.DivRest;
import client.operation.impl.Fact;
import client.operation.impl.Mult;
import client.operation.impl.Subs;
import client.operation.impl.Sum;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;

public class OperationReader {

    public Optional<Operation> parse(final String s) {
        final var maybeSymbol = Arrays
                .stream(Symbol.values())
                .filter(symbol -> s.contains(symbol.toSymbol()))
                .findFirst();
        if (maybeSymbol.isEmpty()) {
            System.out.println("There is no operation symbol");
            return Optional.empty();
        }

        final var symbol = maybeSymbol.get();
        final var split = s.split(Pattern.quote(symbol.toSymbol()));
        switch (symbol) {
            case SUM:
                return getOperationTwoNumbers(split, Sum::new);
            case SUBS:
                return getOperationTwoNumbers(split, Subs::new);
            case DIV:
                return getOperationTwoNumbers(split, Div::new);
            case DIV_REST:
                return getOperationTwoNumbers(split, DivRest::new);
            case MULT:
                return getOperationTwoNumbers(split, Mult::new);
            case FACT:
                return getOperation(split, Fact::new);
        }

        return Optional.empty();
    }

    private Optional<Operation> getOperationTwoNumbers(
            final String[] split,
            final BiFunction<Integer, Integer, Operation> consumer
    ) {
        if (split.length != 2) {
            return Optional.empty();
        }
        final var first = checkNumber(split[0]);
        final var second = checkNumber(split[1]);
        if (first.isPresent() && second.isPresent()) {
            return Optional.of(consumer.apply(first.get(), second.get()));
        }
        return Optional.empty();
    }

    private Optional<Operation> getOperation(
            final String[] split,
            final Function<Integer, Operation> consumer
    ) {
        if (split.length != 1) {
            return Optional.empty();
        }
        final var first = checkNumber(split[0]);
        return first.map(consumer);
    }

    private Optional<Integer> checkNumber(final String input) {
        try {
            final int value = Integer.parseInt(input.trim());
            if (value > 127 || value < -128) {
                return Optional.empty();
            }
            return Optional.of(value);
        } catch (NumberFormatException ex) {
            System.out.println("Is not a valid number");
            return Optional.empty();
        }
    }
}
