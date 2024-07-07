package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.services.OpenAIService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/correct-text")
    public String correctText(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        try {
            return openAIService.correctText(prompt);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}