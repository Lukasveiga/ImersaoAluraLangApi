package com.br.imersaojava.langsapi.controller;

import com.br.imersaojava.langsapi.DTO.LangDTO;
import com.br.imersaojava.langsapi.exceptions.LangAlreadyExistsException;
import com.br.imersaojava.langsapi.exceptions.LangNotFoundException;
import com.br.imersaojava.langsapi.model.Lang;
import com.br.imersaojava.langsapi.service.LangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/langs")
public class LangController {

    @Autowired
    private LangService service;

    @PostMapping
    public ResponseEntity<Lang> createLang(@Valid @RequestBody LangDTO langDTO) throws LangAlreadyExistsException {
        Lang lang = langDTO.transformToObject();
        service.addLang(lang);
        return ResponseEntity.status(HttpStatus.CREATED).body(lang);
    }

    @GetMapping
    public ResponseEntity<List<Lang>> getLangs() {

        List<Lang> langs = service.findAllLangs();

        if (langs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(langs);
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getLang(@PathVariable String title) throws LangNotFoundException {
        Lang lang;
        lang = service.findLangByTitle(title);
        return ResponseEntity.ok(lang);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLang(@PathVariable String id, @RequestBody @Valid LangDTO langDTO) throws LangNotFoundException {
        Lang lang = langDTO.transformToObject();
        lang.setId(id);
        Lang updateLang = service.updateLang(lang);
        return ResponseEntity.ok(updateLang);
    }

    @PatchMapping("/vote/{title}")
    public ResponseEntity<String> voteLang(@PathVariable String title) throws LangNotFoundException {
        String result;
        result = service.updateVoteLang(title);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<?> deleteLang(@PathVariable String title) throws LangNotFoundException {
        String result;
        result = service.deleteLang(title);
        return ResponseEntity.ok(result);
    }

}
