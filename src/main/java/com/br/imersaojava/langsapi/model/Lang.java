package com.br.imersaojava.langsapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Langs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lang implements Comparable<Lang> {

    @Id
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
