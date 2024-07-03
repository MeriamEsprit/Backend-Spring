package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class LanguageToolService {

    @Value("${languagetool.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public LanguageToolService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String checkText(String text) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("language", "fr")
                .queryParam("text", text);

        Map<String, Object> response = restTemplate.postForObject(builder.toUriString(), null, HashMap.class);
        return response.toString(); // Vous pouvez adapter cette partie pour traiter la réponse comme vous le souhaitez
    }
}
