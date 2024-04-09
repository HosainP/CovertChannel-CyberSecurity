import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class CovertChannelSender {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            OutputStream outputStream = socket.getOutputStream();

            String data = "Hello, receiver!";
            for (byte c : data.getBytes(StandardCharsets.UTF_8)) {
                sendByte(c, outputStream);
            }
            Thread.sleep(2000); // end of sending data

            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sendByte(int bbyte, OutputStream outputStream) throws IOException {
        for (int i = 7; i >= 0; i--){
            int bit = (bbyte >> i) & 1; // Extract the current bit
            if (bit == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(bit);

            outputStream.write(new Random().nextInt(255));
        }
    }
}
