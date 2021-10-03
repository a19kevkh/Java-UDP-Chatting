import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Server extends Thread {

    EndPoint serverEnd;
    String replyMessage;
    String name;
    InetAddress client2Address = null;
    int client2Port;
    ArrayList<String> connectedMembers = new ArrayList<String>();

    public Server(int serverPort, String name) {
        this.name = name;
        // Create an endPoint for this program identified by serverport
        serverEnd = new EndPoint(serverPort, name);
    }

    public void updateArray(String arrayName, InetAddress arrayAddress, int arrayPort){
        String newUser = arrayName + "&" + arrayAddress.toString() + "-" + String.valueOf(arrayPort);
        //System.out.println(newUser);
        connectedMembers.add(newUser);
        /*
        for(int i = 0; i < connectedMembers.size(); i++){
            System.err.println(connectedMembers.get(i));
        }
        */
    }

    public void setReplyMessage(String replyMessage) {

        this.replyMessage = replyMessage;

    }

    public void setClient2Address(String address, int client2Port) {
        try {
            client2Address = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("No server address found on " + address);
        }
        this.client2Port = client2Port;
    }

    public String getSender(DatagramPacket tempPacket){
        String sender = "Unknown";
        String tempReceivedMessage = serverEnd.unmarshall(tempPacket.getData());
        int index = tempReceivedMessage.indexOf("-"); //finds the location of - in the array

        if (index != -1)
        {
            sender = tempReceivedMessage.substring(0,index); //copies the start of the message until we reach & (& is not included)
        }
        return sender;
    }

    public void broadcast(String message, String sender){

    }

    public void run() {
        do {
            DatagramPacket receivedPacket = serverEnd.receivePacket();

            // Get the message within packet
            String receivedMessage = serverEnd.unmarshall(receivedPacket.getData());
            String receivedMessageTrim = receivedMessage.trim();
            //System.out.println("Server received: " + receivedMessage);
            //byta ut getAdress och port till hårdkodad client2
            // Make a reply packet
            //DatagramPacket replyPacket = serverEnd.makeNewPacket(replyMessage, receivedPacket.getAddress(), receivedPacket.getPort());
            DatagramPacket replyPacket = serverEnd.makeNewPacket(receivedMessageTrim, client2Address, client2Port);
            System.out.println(getSender(receivedPacket));
            // Now send back a reply packet to client
            serverEnd.sendPacket(replyPacket);
            // Receive a packet from client
            updateArray("Client2",client2Address, client2Port);

            // Check whether it is a “handshake” message
            if (false) {
                // Get client name (it is a new chat-room member!)
                continue;
            }

            // Check whether it is a “tell” message
            if (false) {
                // cut away "/tell" from the message
                // trim any leading spaces from the resulting message
                // split message into “recipient” name and the message
                continue;
            }

            // Check whether it is a “list” message
            if (false) {
                // Get connected member names list
                // sendPrivateMessage(namesList, "Server", getSender(receivedPacket));
                continue;
            }

            // Check whether it is a “leave” message
            if (false) {
                // (Server) broadcasts notification to members
                // broadcast(senderName + " left the chat", "Server");
                // remove sender name from chat members
                continue;
            }

            // if nothing of the above applies, it is a broadcast message
            // if senderName is a member then ...
            // broadcast(receivedMessage, getSender(receivedPacket));
            //(receivedMessage);
        } while (true);


    }
}