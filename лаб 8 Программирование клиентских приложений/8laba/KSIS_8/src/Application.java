import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.IOException;

public class Application {

	public static void main(String[] args)throws IOException{
		System.out.print("Введите домен: ");
		String domain;
		Scanner sc = new Scanner(System.in);
		domain = sc.next();
		
		Socket ClientSocket = null;
		ClientSocket = new Socket(domain, 26); // НЕ ЗАБЫТЬ ЗАМЕНИТЬ ПОРТ НА 25!!!!

		BufferedReader br = null;
		PrintWriter pw = null;
		br = new BufferedReader (new InputStreamReader (ClientSocket.getInputStream()));
		pw = new PrintWriter (new BufferedWriter (new OutputStreamWriter(ClientSocket.getOutputStream())), true);

		pw.println("HELO " + domain);
		String response = br.readLine();
		System.out.println(response);
		
		System.out.print("Почта отправителя: ");
		String from = new String();
		from = sc.next();
		pw.println("MAIL FROM: <" + from + ">");
		response = br.readLine();
		System.out.println(response);
		
		System.out.print("Почта получателя: ");
		String to = new String();
		to = sc.next();
		pw.println("RCPT TO: <" + to + ">");
		response = br.readLine();
		System.out.println(response);
		
		pw.println("DATA");
		pw.println();
		
		System.out.print("Заголовок: ");
		
		String subject = new String();
		subject = sc.nextLine();
		subject = sc.nextLine();
		pw.println("Subject: " + subject);
		
		pw.println();
		
		System.out.print("Сообщение: ");
		String msg = new String();
		msg = sc.nextLine();
	//	System.out.print(msg);
	//	pw.println(msg + "\n\r.\n\r");
	    pw.println(msg);
		pw.println(".");
		System.out.print("Сообщение успешно отправлено");


		ClientSocket.close();
		
	}
}

