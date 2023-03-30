package com.br.imersaojava.langsapi.service;

import com.br.imersaojava.langsapi.DTO.LangDTO;
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

    public Lang updateLang(LangDTO langRequest) {
        Lang existingLang = repository.findById(langRequest.transformToObject().getTitle()).get();
        existingLang.setTitle(langRequest.getTitle());
        existingLang.setImage(langRequest.getImage());
        return repository.save(existingLang);
    }

    public String updateVoteLang(String title) {
        Lang existingLang = repository.findByTitle(title);
        existingLang.setCountVote(existingLang.getCountVote() + 1);

        repository.save(existingLang);

        List<Lang> langs = repository.findAll();

        langs.sort((a,b) -> Integer.compare(b.getCountVote(), a.getCountVote()));

        for (Lang lang : langs){
            lang.setRanking(langs.indexOf(lang) + 1);
            repository.save(lang);
        }

        return title + ": Voto cadastrado.";
    }

    public String deleteLang(String title) {
        repository.deleteByTitle(title);
        return title + " was deleted.";
    }
}
