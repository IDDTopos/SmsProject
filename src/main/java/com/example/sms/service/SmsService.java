package com.example.sms.service;
//import com.telnyx.sdk.api.MessageResponse;
//import com.telnyx.sdk.api.Messages;
import com.example.sms.dto.Datos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {


    private final CloseableHttpClient httpClient;
    private final String telnyxApiKey = "KEY018942BF557A1731FAC01E5D63DBF62D_5yWQzWV1bVg4DZadcvGNiB";

    @Autowired
    public SmsService(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String enviarSms(Datos datos)  {
        String url = "https://api.telnyx.com/v2/messages";
        String respuesta ="";
        System.out.println("entro al enviar sms");
    try {
        HttpPost httpPost = new HttpPost(url);
        //se mandan los encabezados
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.addHeader("Authorization", "Bearer " + telnyxApiKey);
        //Aqui se crea el json para enviar en el requesBody
        String requestBody = crearjson(datos.getFrom(),datos.getTo(),datos.getText());
        System.out.println("datos: "+requestBody.toString());

        httpPost.setEntity(new StringEntity(requestBody));

        HttpResponse response = httpClient.execute(httpPost);
        System.out.println("SMS enviado con éxito");
        String responseBody = EntityUtils.toString(response.getEntity());
        System.err.println("Error en la respuesta de Telnyx: " + responseBody);

        if (response.getStatusLine().getStatusCode() == 200) {
            // El SMS se envió correctamente
            respuesta ="SMS enviado con éxito";


        } else {
            // Maneja el error de acuerdo a tus necesidades
            respuesta ="Error al enviar el SMS. Código de estado:"+ response.getStatusLine().getStatusCode();
            System.err.println("Error al enviar el SMS. Código de estado: " + response.getStatusLine().getStatusCode());
        }

    }catch (Exception exception){
        System.out.println("exception"+exception.getMessage());
    }

        return respuesta;
    }

    public String crearjson(String from,String to,String text )  {
        String jsonString = null;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode json = JsonNodeFactory.instance.objectNode();

            json.put("from", from);
            json.put("messaging_profile_id", "abc85f64-5717-4562-b3fc-2c9600000000");
            json.put("to", to);
            json.put("text", text);
            json.put("subject", "Prueba isabel Telnyx!");

            ArrayNode mediaUrls = JsonNodeFactory.instance.arrayNode();
            mediaUrls.add("http://turbored.com");
            json.set("media_urls", mediaUrls);

            json.put("webhook_url", "http://example.com/webhooks");
            json.put("webhook_failover_url", "https://backup.example.com/hooks");
            json.put("use_profile_webhooks", true);
            json.put("type", "MMS");

            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            //System.out.println(jsonString);

        }catch (Exception e){
            System.out.println("error al generar json"+e.getMessage());
        }

        return jsonString;
    }
}
