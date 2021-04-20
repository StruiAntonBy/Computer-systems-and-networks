import java.net.*;
import java.io.*;
import java.lang.*;
import java.io.*;
import java.util.*;

class TelnetServer
{
    public static void main(String args[]) throws Exception
    {
        Runtime rt=Runtime.getRuntime();
      //Process p=rt.exec("notepad.exe");
        ServerSocket Soc=new ServerSocket(23);
        while(true)
        {
            Socket CSoc=Soc.accept();
            AcceptTelnetClient ob=new AcceptTelnetClient(CSoc);
        }
    }
}

class AcceptTelnetClient extends Thread
{
    Socket ClientSocket;
    DataInputStream din;
    DataOutputStream dout;
    String LoginName;
    String Password;

    AcceptTelnetClient(Socket CSoc) throws Exception
    {
        ClientSocket=CSoc;
        System.out.println("Client connected...");
        DataInputStream din=new DataInputStream(ClientSocket.getInputStream());
        DataOutputStream dout=new DataOutputStream(ClientSocket.getOutputStream());

        System.out.println("Waiting for a username and password...");

        LoginName=din.readLine();
        Password=din.readLine();
        start(); 
    }
    public void run()
    {
    	System.out.println("Started");
        try
        {    
        DataInputStream din=new DataInputStream(ClientSocket.getInputStream());
        DataOutputStream dout=new DataOutputStream(ClientSocket.getOutputStream());

        BufferedReader brFin=new BufferedReader(new FileReader("Passwords.txt"));

        String LoginInfo=new String("");
        boolean allow=false;

        //System.out.println("File read");
        while((LoginInfo=brFin.readLine())!=null)
        {
        	
            StringTokenizer st=new StringTokenizer(LoginInfo);
            if(LoginName.equals(st.nextToken()) && Password.equals(st.nextToken()))
            {
                dout.writeUTF("Successfully\n\r");
                allow=true;
                break;
            }
        }

        brFin.close();
        
        if (allow==false)
        {
            dout.writeUTF("Invalid username and password\n\r");            
        }

        while(allow)
        {
            String strCommand;
            strCommand=din.readLine();
            if(strCommand.equals("quit"))
            {
                allow=false;
            }
            else
            {
            	try {
                Runtime rt=Runtime.getRuntime();

                Process p=rt.exec(strCommand);

                String stdout=new String("");
                String st;
                DataInputStream dstdin=new DataInputStream(p.getInputStream());
                String result = "";
                while((st=dstdin.readLine())!=null)
                {
                    result += st + "\n\r";
                }
                dstdin.close();
                dout.writeUTF(result);   
            	} catch (Exception e) {
            		dout.writeUTF("Incorrect command\n\r");
            	}
            }                        
        }
        ClientSocket.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    } 
}