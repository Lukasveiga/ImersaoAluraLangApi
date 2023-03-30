package com.br.imersaojava.langsapi.service;

import com.br.imersaojava.langsapi.model.Lang;
import com.br.imersaojava.langsapi.repository.LangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LangService {

    @Autowired
    private LangRepository repository;

    public Lang addLang(Lang lang) {
        return repository.save(lang);
    }

    public List<Lang> findAllLangs() {
        List<Lang> langs = repository.findAll();
        Collections.sort(langs);
        return langs;
    }

    public Lang findLangByTitle(String title) {
        return repository.findByTitle(title);
    }

    public Lang updateLang(Lang langRequest) {
        Lang existingLang = repository.findById(langRequest.getId()).get();
        existingLang.setTitle(langRequest.getTitle());
        existingLang.setImage(langRequest.getImage());
        existingLang.setRanking(langRequest.getRanking());
        return repository.save(existingLang);
    }

    public String deleteLang(String title) {
        repository.deleteByTitle(title);
        return title + " was deleted.";
    }


}
