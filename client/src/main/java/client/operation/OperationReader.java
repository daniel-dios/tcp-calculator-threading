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

public class OperationReader {

    public static final int MIN_VALUE = -128;
    public static final int MAX_VALUE = 127;

    public Optional<Operation> parse(final String s) {
        final var split = s.split(" ");
        if (split.length == 2 && Symbol.FACT.toSymbol().equals(split[1])) {
            return checkNumber(split[0]).map(Fact::new);
        }
        if (split.length == 3 && Symbol.FACT.toSymbol().equals(split[1])) {
            System.out.println();
            System.out.println("Factorial must be only one number.");
            return Optional.empty();
        }
        if (split.length == 3) {
            final var maybeSymbol = Arrays
                    .stream(Symbol.values())
                    .filter(symbol -> symbol.toSymbol().equals(split[1]))
                    .findFirst();
            if (maybeSymbol.isEmpty()) {
                System.out.println("There is no operation simbol in infix with spaces.");
                return Optional.empty();
            }
            final var maybeFirstNumber = checkNumber(split[0]);
            final var maybeSecondNumber = checkNumber(split[2]);
            if (maybeFirstNumber.isPresent() && maybeSecondNumber.isPresent()) {
                switch (maybeSymbol.get()) {
                    case SUM:
                        return getOperationTwoNumbers(maybeFirstNumber.get(), maybeSecondNumber.get(), Sum::new);
                    case SUBS:
                        return getOperationTwoNumbers(maybeFirstNumber.get(), maybeSecondNumber.get(), Subs::new);
                    case DIV:
                        return getOperationTwoNumbers(maybeFirstNumber.get(), maybeSecondNumber.get(), Div::new);
                    case DIV_REST:
                        return getOperationTwoNumbers(maybeFirstNumber.get(), maybeSecondNumber.get(), DivRest::new);
                    case MULT:
                        return getOperationTwoNumbers(maybeFirstNumber.get(), maybeSecondNumber.get(), Mult::new);
                    case FACT:
                        System.out.println("Unreachable code.");
                        return Optional.empty();
                }
            } else {
                System.out.println("They are not numbers in the valid range");
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Optional<Operation> getOperationTwoNumbers(
            final Integer first,
            final Integer second,
            final BiFunction<Integer, Integer, Operation> consumer
    ) {
        return Optional.of(consumer.apply(first, second));
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
            if (value > MAX_VALUE || value < MIN_VALUE) {
                return Optional.empty();
            }
            return Optional.of(value);
        } catch (NumberFormatException ex) {
            System.out.println("Is not a valid number");
            return Optional.empty();
        }
    }
}
