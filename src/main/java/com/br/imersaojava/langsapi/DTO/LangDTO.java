package com.br.imersaojava.langsapi.DTO;

import com.br.imersaojava.langsapi.model.Lang;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LangDTO {

    private String title;
    private String image;

    public Lang transformToObject() {
        return new Lang(this.title, this.image);
    }
}
