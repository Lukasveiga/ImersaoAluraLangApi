package com.br.imersaojava.langsapi.controller;

import com.br.imersaojava.langsapi.DTO.LangDTO;
import com.br.imersaojava.langsapi.exceptions.ListLangsEmptyException;
import com.br.imersaojava.langsapi.model.Lang;
import com.br.imersaojava.langsapi.service.LangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/langs")
@Validated
public class LangController {

    private final LangService service;

    @Autowired
    public LangController(LangService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Lang> createLang(@Valid @RequestBody LangDTO langDTO) {
        Lang lang = service.addLang(langDTO.transformToObject());
        return ResponseEntity.status(HttpStatus.CREATED).body(lang);
    }

    @GetMapping
    public ResponseEntity<List<Lang>> getLangs() {
        List<Lang> langs = service.findAllLangs();

        if (langs.isEmpty()) {
            throw new ListLangsEmptyException("List of langs is empty.");
        }

        return ResponseEntity.ok(langs);
    }

    @GetMapping("/{title}")
    public ResponseEntity<Lang> getLang(@PathVariable String title) {
        Lang lang = service.findLangByTitle(title);
        return ResponseEntity.ok(lang);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lang> updateLang(@PathVariable String id, @RequestBody @Valid LangDTO langDTO) {
        Lang updateLang = service.updateLang(id, langDTO);
        return ResponseEntity.ok(updateLang);
    }

    @PatchMapping("/vote/{title}")
    public ResponseEntity<String> voteLang(@PathVariable String title) {
        String result = service.updateVoteLang(title);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<String> deleteLang(@PathVariable String title) {
        String result = service.deleteLang(title);
        return ResponseEntity.ok(result);
    }

}
