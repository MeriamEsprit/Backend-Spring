package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.services.LanguageToolService;

import java.util.Map;

@RestController
@RequestMapping("/api/languagetoo")
public class LanguageToolController {

    private final LanguageToolService languageToolService;

    @Autowired
    public LanguageToolController(LanguageToolService languageToolService) {
        this.languageToolService = languageToolService;
    }

    @PostMapping("/check")
    public String checkText(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        return languageToolService.checkText(text);
    }
}
