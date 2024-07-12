package tn.esprit.spring.services;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenAIService {

//    @Value("${openai.api.key}")
//    private String apiKey;
//
//    @Value("${openai.organization.id}")
//    private String organizationId;
//
//    public String correctText(String prompt) throws IOException {
//        String url = "https://api.openai.com/v1/completions";
//        String model = "gpt-3.5-turbo"; // Updated model
//        String requestBody = String.format(
//                "{\"model\":\"%s\",\"prompt\":\"Corrigez ce texte : %s\",\"temperature\":0.95,\"max_tokens\":150,\"top_p\":1.0,\"frequency_penalty\":0.0,\"presence_penalty\":0.0,\"stream\":false}",
//                model, prompt);
//
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            HttpPost request = new HttpPost(url);
//            request.setHeader("Authorization", "Bearer " + apiKey);
//            request.setHeader("Content-Type", "application/json");
//            request.setHeader("OpenAI-Organization", organizationId);
//            request.setEntity(new StringEntity(requestBody));
//
//            try (CloseableHttpResponse response = client.execute(request)) {
//                int statusCode = response.getStatusLine().getStatusCode();
//                if (statusCode == 429) {
//                    return "Quota exceeded: Please check your OpenAI plan and billing details.";
//                }
//                return EntityUtils.toString(response.getEntity());
//            }
//        }
//    }
}