//Simple server that takes in some ints. Simple because it can be fleshed out
//further once the client is written and made to work with it.
//
//After much frustration I ended up just ripping off much of the code from the Chat
//program
import java.io.*;
import java.net.*;
import java.util.ArrayList;
 

    public class Server
    {
        private static int clientId;
        private ArrayList<ClientThread> al;
        //private ServerGUI serverGui;
 
        private int port;
        private boolean keepRunning;

        public Server(int port)
        {            
            this.port = port; 
            al = new ArrayList<ClientThread>();
        }

        public void start() 
        {
            keepRunning = true;
            try
            {
                ServerSocket ss = new ServerSocket(port);
                while(keepRunning)
                {
                    Socket socket = ss.accept();
                    if (!keepRunning)
                    {
                        break;
                    }
                    ClientThread t = new ClientThread(socket);
                    al.add(t);
                    t.start();
                }
            try
            {
                ss.close();
                for(int i = 0; i < al.size(); i++)
                {
                    ClientThread ct = al.get(i);
                    try
                    {
                        ct.ois.close();
                        ct.oos.close();
                        ct.sock.close();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e)
            {         
                e.printStackTrace();
            }
        } catch (Exception e)
        {            
            e.printStackTrace();
        }
        
    } // end of start


    public static void main(String[] args)
    {
        int portNum = 3484;
        switch(args.length)
        {
            case 1:
                try
                {
                    portNum = Integer.parseInt(args[0]);
                } catch (Exception e)
                {
                    System.out.println("Invalid port num");
                    return;
                }
            case 0:
                break;
            default:
                System.out.println("Invalid usage");
                return;
        }
        Server server = new Server(portNum);
        server.start();
    }

    private void stop()
    {
        keepRunning = false;
    }

    
    // Used in case of logout button
    
    class ClientThread extends Thread
    {
        Socket sock;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        int id;
        String userName;
        MyMessage myMessage;
        String date;

        ClientThread(Socket sock)
        {
            clientId++;
            id = clientId;
            this.sock = sock;
            try
            {
                oos = new ObjectOutputStream(sock.getOutputStream());
                ois = new ObjectInputStream(sock.getInputStream());
                int inInt = ois.readInt();
                //int outInt = oos.writeInt();
            } catch (Exception e)
            {
                e.printStackTrace();
                return;
            }            
        }

        public void run()
        {
            boolean keepRunning = true;            
        }

        private void close()
        {
            try
            {
                if (oos != null) oos.close();
                if (ois != null) ois.close();
                if (sock != null) sock.close();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

    
