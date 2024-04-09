import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class CovertChannelReceiver {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);

            System.out.println("Waiting for sender to connect...");
            Socket socket = serverSocket.accept();
            System.out.println("Sender connected.");

            InputStream inputStream = socket.getInputStream();
            StringBuilder message = new StringBuilder();
            while (true) {
                int bit = receiveBit(inputStream);
                if (bit == -1) break;
                message.append(bit);
                System.out.print(bit);
            }

            System.out.println("\nReceived message: " + message.toString());

            byte[] bytes = BitListToBytes(message.toString());
            String extractedString = new String(bytes);
            System.out.println("Extracted string: " + extractedString);
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int receiveBit(InputStream inputStream) throws IOException {
        long startTime = System.currentTimeMillis();
        int bit = inputStream.read();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if (duration < 500) {
            return 0;
        } else if (duration < 1000) {
            return 1;
        } else {
            return -1;
        }
    }

    private static byte[] BitListToBytes(String bitListString) {
        int numBytes = bitListString.length() / 8;
        byte[] bytes = new byte[numBytes];

        for (int i = 0; i < numBytes; i++) {
            String byteString = bitListString.substring(i * 8, (i + 1) * 8);
            bytes[i] = (byte) Integer.parseInt(byteString, 2);
        }

        return bytes;
    }
}
