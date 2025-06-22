package chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteChat {

    public static void main(String[] args) {
        String servidorIP = "localhost";
        int porta = 12345;

        try {
            Socket socket = new Socket(servidorIP, porta);
            System.out.println("Conectado ao servidor " + servidorIP + ":" + porta);

            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader entradaServidor = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            String mensagem;
            String respostaServidor;

            while (true) {
                System.out.print("Digite uma mensagem (ou 'sair' para encerrar): ");
                mensagem = teclado.readLine();

                if (mensagem.equalsIgnoreCase("sair")) {
                    saida.println("sair");
                    break;
                }

                saida.println(mensagem); // envia para o servidor

                // Aguarda e lê resposta do servidor
                respostaServidor = entradaServidor.readLine();
                System.out.println("Resposta do servidor: " + respostaServidor);
            }

            saida.close();
            teclado.close();
            entradaServidor.close();
            socket.close();
            System.out.println("Conexão encerrada.");

        } catch (IOException e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }
}