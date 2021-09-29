import java.net.DatagramPacket;

public class Server extends Thread {

    EndPoint serverEnd;
    String replyMessage;
    String name;
    public Server(int serverPort, String name) {
        this.name = name;
        // Create an endPoint for this program identified by serverport
        serverEnd = new EndPoint(serverPort, name);
    }

    public void setReplyMessage(String replyMessage) {

        this.replyMessage = replyMessage;

    }

    public void run() {
        do {
            // Receive a packet from client
            DatagramPacket receivedPacket = serverEnd.receivePacket();

            // Get the message within packet
            String receivedMessage = serverEnd.unmarshall(receivedPacket.getData());
            //System.out.println("Server received: " + receivedMessage);

            // Make a reply packet
            //DatagramPacket replyPacket = serverEnd.makeNewPacket(replyMessage, receivedPacket.getAddress(), receivedPacket.getPort());
            // Now send back a reply packet to client
            //serverEnd.sendPacket(replyPacket);

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
            System.out.println(receivedMessage);
        } while (true);



    }
}