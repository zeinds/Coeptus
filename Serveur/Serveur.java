import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Serveur {
	public static void main(String[] arg) {
		int portEcoute = 9001;
		ServerSocket server;
		Socket socket;
		Handler h;
		try {
			server = new ServerSocket(portEcoute);
			while(true) {
				socket = server.accept();
	 			 h = new Handler(socket);
			}
		}
		catch(IOException exc) {
	 		System.out.println("probleme de connexion");
		}
	}
}