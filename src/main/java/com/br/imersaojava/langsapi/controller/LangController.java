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
    public ResponseEntity<?> createLang(@Valid @RequestBody LangDTO langDTO) {
        Lang lang = langDTO.transformToObject();
        try {
            service.addLang(lang);
            return ResponseEntity.status(HttpStatus.CREATED).body(lang);
        }
        catch (LangAlreadyExistsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body("Lang already exist.");
        }

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
    public ResponseEntity<Lang> getLang(@PathVariable String title) {
        Lang lang;
        try {
            lang = service.findLangByTitle(title);
            return ResponseEntity.ok(lang);
        }
        catch (LangNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Lang> updateLang(@PathVariable String id, @RequestBody @Valid LangDTO langDTO) {
        Lang lang = langDTO.transformToObject();
        lang.setId(id);

        try {
            Lang updateLang = service.updateLang(lang);
            return ResponseEntity.ok(updateLang);
        }
        catch (LangNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/vote/{title}")
    public ResponseEntity<String> voteLang(@PathVariable String title) {
        String result;
        try {
            result = service.updateVoteLang(title);
            return ResponseEntity.ok(result);
        }
        catch (LangNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{title}")
    public ResponseEntity<String> deleteLang(@PathVariable String title) {
        String result;
        try {
            result = service.deleteLang(title);
            return ResponseEntity.ok(result);
        }
        catch (LangNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }

}
