package chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorChat {

    public static void main(String[] args) {
        int porta = 12345; // Porta do servidor

        try {
            ServerSocket servidorSocket = new ServerSocket(porta);
            System.out.println("Servidor iniciado na porta " + porta);
            System.out.println("Aguardando conexões...");

            Socket clienteSocket = servidorSocket.accept();
            System.out.println("Cliente conectado: " + clienteSocket.getInetAddress().getHostAddress());

            // Para ler mensagens do cliente
            BufferedReader entradaCliente = new BufferedReader(
                    new InputStreamReader(clienteSocket.getInputStream())
            );

            // Para enviar mensagens para o cliente
            PrintWriter saidaCliente = new PrintWriter(clienteSocket.getOutputStream(), true);

            // Para ler mensagens do teclado do servidor
            BufferedReader tecladoServidor = new BufferedReader(new InputStreamReader(System.in));

            String mensagemCliente;
            String mensagemServidor;

            while (true) {
                // Lê mensagem do cliente
                mensagemCliente = entradaCliente.readLine();

                if (mensagemCliente == null || mensagemCliente.equalsIgnoreCase("sair")) {
                    System.out.println("Cliente desconectou.");
                    break;
                }

                System.out.println("Mensagem do cliente: " + mensagemCliente);

                // Servidor digita uma resposta
                System.out.print("Digite uma resposta: ");
                mensagemServidor = tecladoServidor.readLine();

                saidaCliente.println(mensagemServidor); // Envia resposta para o cliente
            }

            entradaCliente.close();
            saidaCliente.close();
            clienteSocket.close();
            servidorSocket.close();

        } catch (IOException e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}