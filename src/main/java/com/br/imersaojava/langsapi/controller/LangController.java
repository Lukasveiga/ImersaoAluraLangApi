package com.br.imersaojava.langsapi.controller;

import com.br.imersaojava.langsapi.DTO.LangDTO;
import com.br.imersaojava.langsapi.model.Lang;
import com.br.imersaojava.langsapi.service.LangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/langs")
public class LangController {

    @Autowired
    private LangService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lang createLang(@RequestBody LangDTO langDTO) throws Exception {
        Lang langAlreadyExists;
        langAlreadyExists = service.findLangByTitle(langDTO.getTitle());

        if (langAlreadyExists != null) {
            throw new Exception("This lang already exist.");
        }
        return service.addLang(langDTO.transformToObject());
    }

    @GetMapping
    public List<Lang> getLangs() {
        return service.findAllLangs();
    }

    @GetMapping("/title/{title}")
    public Lang getLang(@PathVariable String title) {
        return service.findLangByTitle(title);
    }

    @PutMapping
    public Lang modifyLang(@RequestBody LangDTO langDTO) {
        return service.updateLang(langDTO);
    }

    @PatchMapping("/vote/{title}")
    public String voteLang(@PathVariable String title) {
        return service.updateVoteLang(title);
    }

    @DeleteMapping("/{title}")
    public String deleteLang(String title) {
        return service.deleteLang(title);
    }


}
