package com.br.imersaojava.langsapi.controller;

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
        public Lang createLang(@RequestBody Lang lang) throws Exception {
            Lang langAlreadyExists;
            langAlreadyExists = service.findLangByTitle(lang.getTitle());

            if (langAlreadyExists != null){
                throw new Exception("This lang already exist.");
            }
            return service.addLang(lang);
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
        public Lang modifyLang(@RequestBody Lang lang) {
            return service.updateLang(lang);
        }


}
