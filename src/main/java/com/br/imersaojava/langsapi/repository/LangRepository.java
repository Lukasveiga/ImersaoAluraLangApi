package com.br.imersaojava.langsapi.repository;

import com.br.imersaojava.langsapi.model.Lang;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LangRepository extends MongoRepository<Lang, String> {
    Lang findByTitle(String title);

    void deleteByTitle(String title);
}
