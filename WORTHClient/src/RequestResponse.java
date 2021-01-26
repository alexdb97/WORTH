import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class RequestResponse {

    public static int requestresponse(SocketChannel client, String request, InitialView view, Model model) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(request.getBytes());
        buffer.flip();

        try
        {
        // RICHIESTA
        while (buffer.hasRemaining())
                client.write(buffer);
           

        buffer.clear();

        
        // RISPOSTA
        ByteBuffer bufferrisposta = ByteBuffer.allocate(1024);
        String response = "";           
        int code = 0;
        int len;
        while (( len=client.read(bufferrisposta))>=0) {
                
                if(len!=0)
                {
                response = new String(bufferrisposta.array()).trim();
                code = GestioneRisposta.ResponseHandler(response, view,model,client);
                if(code!=0)
                    break;
                // se il codice restituito è -1
                // significa che si è chiusa una connessione e quindi si esce dal thread
                // Mi devo anche deregistrare dalla RMI callback
                }
        }
        return code;
    }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return -1;
        }
    
    
}
}
