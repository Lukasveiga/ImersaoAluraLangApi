package com.br.imersaojava.langsapi.DTO;

import com.br.imersaojava.langsapi.model.Lang;
import jakarta.validation.constraints.NotEmpty;

public record LangDTO(
        @NotEmpty(message = "The name is required") String title,
        @NotEmpty(message = "The image url is required") String image
) {
    public Lang transformToObject() {
        return new Lang(this.title, this.image);
    }
}
