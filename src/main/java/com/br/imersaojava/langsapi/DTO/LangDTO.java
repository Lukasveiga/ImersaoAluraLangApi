package com.br.imersaojava.langsapi.DTO;

import com.br.imersaojava.langsapi.model.Lang;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LangDTO {

    @NotEmpty(message = "The name is required.")
    private String title;
    @NotEmpty(message = "The image url is required.")
    private String image;

    public Lang transformToObject() {
        return new Lang(this.title, this.image);
    }
}
