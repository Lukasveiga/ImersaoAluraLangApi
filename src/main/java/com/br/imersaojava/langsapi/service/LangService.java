package com.br.imersaojava.langsapi.service;

import com.br.imersaojava.langsapi.DTO.LangDTO;
import com.br.imersaojava.langsapi.exceptions.LangAlreadyExistsException;
import com.br.imersaojava.langsapi.exceptions.LangNotFoundException;
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

    private static void updateRanking(List<Lang> langs, LangRepository repository) {

        langs.sort((a, b) -> Integer.compare(b.getCountVote(), a.getCountVote()));

        for (Lang lang : langs) {
            lang.setRanking(langs.indexOf(lang) + 1);
            repository.save(lang);
        }
    }

    public Lang addLang(Lang lang) throws LangAlreadyExistsException {

        if (repository.existsByTitle(lang.getTitle())) {
            throw new LangAlreadyExistsException();
        }

        return repository.save(lang);
    }

    public List<Lang> findAllLangs() {
        List<Lang> langs = repository.findAll();

        updateRanking(langs, repository);

        Collections.sort(langs);
        return langs;
    }

    public Lang findLangByTitle(String title) throws LangNotFoundException {

        if (!repository.existsByTitle(title)) {
            throw new LangNotFoundException();
        }

        return repository.findByTitle(title);
    }

    public Lang updateLang(LangDTO langRequest) throws LangNotFoundException {

        Lang existingLang;

        if (repository.findById(langRequest.transformToObject().getId()).isPresent()){
            existingLang = repository.findById(langRequest.transformToObject().getId()).get();
        } else {
            throw new LangNotFoundException();
        }

        existingLang.setTitle(langRequest.getTitle());
        existingLang.setImage(langRequest.getImage());
        return repository.save(existingLang);
    }

    public String updateVoteLang(String title) throws LangNotFoundException {

        if (!repository.existsByTitle(title)) {
            throw new LangNotFoundException();
        }

        Lang existingLang = repository.findByTitle(title);

        existingLang.setCountVote(existingLang.getCountVote() + 1);

        repository.save(existingLang);

        List<Lang> langs = repository.findAll();

        updateRanking(langs, repository);

        return title + ": Voto cadastrado.";
    }

    public String deleteLang(String title) throws LangNotFoundException {

        if (!repository.existsByTitle(title)) {
            throw new LangNotFoundException();
        }
        repository.deleteByTitle(title);
        return title + " was deleted.";
    }
}
