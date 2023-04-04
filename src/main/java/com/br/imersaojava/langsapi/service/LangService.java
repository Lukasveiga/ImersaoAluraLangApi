package com.br.imersaojava.langsapi.service;

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

    private static void updateRanking(LangRepository repository) {

        List<Lang> langs = repository.findAll();

        langs.sort((a, b) -> Integer.compare(b.getCountVote(), a.getCountVote()));

        for (Lang lang : langs) {
            lang.setRanking(langs.indexOf(lang) + 1);
            repository.save(lang);
        }
    }

    public Lang addLang(Lang lang) throws LangAlreadyExistsException {

        if (repository.existsByTitle(lang.getTitle())) {
            throw new LangAlreadyExistsException("Lang already exist: " + lang.getTitle());
        }

        return repository.save(lang);
    }

    public List<Lang> findAllLangs() {
        updateRanking(repository);

        List<Lang> langs = repository.findAll();

        Collections.sort(langs);
        return langs;
    }

    public Lang findLangByTitle(String title) throws LangNotFoundException {

        if (repository.existsByTitle(title)) {
            return repository.findByTitle(title);
        }

        throw new LangNotFoundException("Lang not found: " + title);
    }

    public Lang updateLang(Lang updateLang) throws LangNotFoundException {

        Lang existingLang = repository.findById(updateLang.getId())
                .orElseThrow(() -> new LangNotFoundException("Lang not found: " + updateLang.getTitle()));

        updateLang.setRanking(existingLang.getRanking());
        updateLang.setCountVote(existingLang.getCountVote());

        return repository.save(updateLang);

    }

    public String updateVoteLang(String title) throws LangNotFoundException {

        if (!repository.existsByTitle(title)) {
            throw new LangNotFoundException("Lang not found: " + title);
        }

        Lang existingLang = repository.findByTitle(title);

        existingLang.setCountVote(existingLang.getCountVote() + 1);

        repository.save(existingLang);

        updateRanking(repository);

        return title + ": Voto cadastrado.";
    }

    public String deleteLang(String title) throws LangNotFoundException {

        if (!repository.existsByTitle(title)) {
            throw new LangNotFoundException("Lang not found: " + title);
        }
        repository.deleteByTitle(title);
        return title + " was deleted.";
    }
}
