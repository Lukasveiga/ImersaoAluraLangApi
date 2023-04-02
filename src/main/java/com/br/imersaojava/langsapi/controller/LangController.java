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
    @ResponseStatus(HttpStatus.CREATED)
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
    @ResponseStatus(HttpStatus.OK)
    public Lang getLang(@PathVariable String title) throws LangNotFoundException {
        return service.findLangByTitle(title);
    }

    @PutMapping
    public Lang modifyLang(@RequestBody LangDTO langDTO) throws LangNotFoundException {
        return service.updateLang(langDTO);
    }

    @PatchMapping("/vote/{title}")
    public String voteLang(@PathVariable String title) throws LangNotFoundException {
        return service.updateVoteLang(title);
    }

    @DeleteMapping("/{title}")
    public String deleteLang(@PathVariable String title) throws LangNotFoundException {
        return service.deleteLang(title);
    }

}
