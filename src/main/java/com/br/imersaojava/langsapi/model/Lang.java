package com.br.imersaojava.langsapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Document(collection = "Langs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lang implements Comparable<Lang> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String title;
    private String image;
    private int ranking;
    private int countVote;

    public Lang(String title, String image){
        this.title = title;
        this.image = image;
    }

    public Lang(int ranking, int countVote){
        this.ranking = ranking;
        this.countVote = countVote;
    }

    @Override
    public int compareTo(Lang o) {
        return Integer.compare(this.ranking, o.getRanking());
    }
}
