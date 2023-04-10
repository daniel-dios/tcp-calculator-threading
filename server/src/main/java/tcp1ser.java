import server.operation.OperationDecoder;
import server.Server;
import server.ServerParameters;
import server.encoder.AnswerEncoder;

public class tcp1ser {
    public static void main(String[] args) {
        ServerParameters
                .parse(args)
                .ifPresentOrElse(
                        tcp1ser::listen,
                        tcp1ser::printInstructions
                );
    }

    private static void listen(final ServerParameters params) {
        final var server = new Server(params, new AnswerEncoder(), new OperationDecoder());
        server.startListeningBlocking();
    }

    private static void printInstructions() {
        System.out.println();
        System.out.println("Correct format is:");
        System.out.println("tcp1ser <server_port>");
        System.out.println();
        System.out.println("\t<server_port> must be an unused TCP port.");
        System.out.println();
        System.out.println("Example:");
        System.out.println("tcp1ser 8081");
        System.out.println("java tcp1ser 8081");
    }
}
