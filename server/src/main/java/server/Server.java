package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import server.encoder.AnswerEncoder;
import server.operation.OperationDecoder;

public class Server {

    public static final int READ_EXIT_CODE = -1;
    public static final int MAX_FROM_CLIENT = 4;
    private final ServerParameters params;
    private final AnswerEncoder answerEncoder;
    private final OperationDecoder operationDecoder;

    public Server(
            final ServerParameters params,
            final AnswerEncoder answerEncoder,
            final OperationDecoder operationDecoder
    ) {
        this.params = params;
        this.answerEncoder = answerEncoder;
        this.operationDecoder = operationDecoder;
    }

    public void startListeningBlocking() {
        tryToOpenSocket(params.getPort())
                .ifPresentOrElse(
                        socket -> {
                            try {
                                acceptingConnections(socket);
                            } catch (IOException e) {
                                System.out.println("Problem with " + e.getMessage());
                            }
                        },
                        () -> System.out.println("Impossible to connect"));
        System.out.println("See you soon.");
    }

    private void acceptingConnections(final ServerSocket socket) throws IOException {
        while (true) {
            System.out.println("Listening...");
            final var clientSocket = socket.accept();
            new Thread(() -> handleNewConnection(clientSocket)).start();
        }
    }

    private void handleNewConnection(
            final Socket clientSocket
    ) {
        final var accumulator = new Accumulator(0);
        final var info = "[IP '" + clientSocket.getInetAddress() + "', Port '" + clientSocket.getPort() + "'] ";
        System.out.println(info + "connection accepted!");
        try (
                final var in = clientSocket.getInputStream();
                final var out = clientSocket.getOutputStream()
        ) {
            final var buffer = new byte[MAX_FROM_CLIENT];
            while (in.read(buffer) != READ_EXIT_CODE) {

                final var decode = operationDecoder.decode(buffer);
                if (decode.isPresent()) {
                    final var operation = decode.get();

                    System.out.println(info + "Received from client the operation: " + operation.toReadableFormat());
                    final var solution = operation.solve();
                    if (solution.success) {
                        System.out.println(info + "Solved: " + operation.toReadableFormat() + " = " + solution);

                        final var before = accumulator.getValue();
                        accumulator.accumulate(solution.result);
                        System.out.println(info + "Accumulator was: " + before + " and after accumulation is: " + accumulator.getValue());

                        out.write(answerEncoder.encodeSuccess(accumulator.getValue()));
                        System.out.println(info + "Answered: " + accumulator);
                    } else {
                        System.out.println(info + "Problem solving: " + operation.toReadableFormat() + ", error: " + solution.reason);

                        accumulator.accumulate(solution.result);
                        System.out.println(info + "Accumulator was (didn't change): " + accumulator.getValue());

                        out.write(answerEncoder.encodeFailure(accumulator.getValue(), solution.reason));
                        System.out.println(info + "Answer sent: type 10 (type 16, value" + accumulator + "; type 11, reason:" + solution.reason);
                    }
                } else {
                    out.write(answerEncoder.encodeFailure(accumulator.getValue(), "invalid input"));
                    System.out.println(info + "Answered with a 0 due to invalid input.");
                }
            }
        } catch (IOException e) {
            System.out.println(info + "IOException with client reason:" + e.getMessage());
        } finally {
            System.out.println(info + "Connection closed with client.");
        }
    }

    private Optional<ServerSocket> tryToOpenSocket(final int port) {
        try {
            return Optional.of(new ServerSocket(port));
        } catch (IOException e) {
            System.out.println("Could not open the socket on port: " + port + ", reason: " + e.getMessage());
            return Optional.empty();
        }
    }
}
