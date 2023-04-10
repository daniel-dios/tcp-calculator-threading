package server;

import java.io.IOException;
import java.net.ServerSocket;
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
                                listenForever(socket);
                            } catch (IOException e) {
                                System.out.println("Problem with " + e.getMessage());
                            }
                        },
                        () -> System.out.println("Impossible to connect"));
        System.out.println("See you soon.");
    }

    private void listenForever(final ServerSocket socket) throws IOException {
        final var accumulator = new Accumulator(0);
        System.out.println("Started accumulator in " + accumulator);
        while (true) {
            System.out.println("Listening...");
            final var clientSocket = socket.accept();
            final var clientAddress = clientSocket.getInetAddress();
            System.out.println("Accepted connection with client" + clientAddress);
            try (
                    final var in = clientSocket.getInputStream();
                    final var out = clientSocket.getOutputStream()
            ) {
                final var buffer = new byte[MAX_FROM_CLIENT];
                while (in.read(buffer) != READ_EXIT_CODE) {

                    final var decode = operationDecoder.decode(buffer);
                    if (decode.isPresent()) {
                        final var operation = decode.get();

                        System.out.println("Received from client: " + operation.toReadableFormat());
                        final var solution = operation.solve();
                        System.out.println("Solved: " + operation.toReadableFormat() + " = " + solution);

                        final var before = accumulator.getValue();
                        accumulator.accumulate(solution);
                        System.out.println("Accumulator was: " + before + " and after accumulation is: " + accumulator.getValue());

                        out.write(answerEncoder.encode(accumulator.getValue()));
                        System.out.println("Answered: " + accumulator);
                    } else {
                        out.write(answerEncoder.encode(0));
                        System.out.println("Answered with a 0 due to invalid input.");
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException with client " + clientAddress + " reason:" + e.getMessage());
            } finally {
                System.out.println("Connection closed with client " + clientAddress);
            }
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
