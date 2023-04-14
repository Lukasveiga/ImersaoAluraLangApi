package com.br.imersaojava.langsapi.controller;

import com.br.imersaojava.langsapi.DTO.LangDTO;
import com.br.imersaojava.langsapi.exceptions.LangAlreadyExistsException;
import com.br.imersaojava.langsapi.exceptions.LangNotFoundException;
import com.br.imersaojava.langsapi.model.Lang;
import com.br.imersaojava.langsapi.service.LangService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LangController.class)
public class LangControllerTest {

    @MockBean
    private LangService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnSuccess201_WhenCreateNewLang() throws Exception {

        Lang lang = new LangDTO("Java", "imagem.svg").transformToObject();
        String json = mapper.writeValueAsString(lang);

        when(service.addLang(lang)).thenReturn(lang);

        mockMvc.perform(post("/api/v1/langs").contentType("application/json")
                                .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void shouldReturnSuccess200_WhenLangAlreadyExists() throws Exception {
        Lang lang = new LangDTO("Java", "imagem.svg").transformToObject();
        String json = mapper.writeValueAsString(lang);

        when(service.addLang(lang)).thenThrow(LangAlreadyExistsException.class);

        mockMvc.perform(post("/api/v1/langs").contentType("application/json")
                                .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldReturnBadRequest400_WhenCreateNewLangWithNullFields() throws Exception {

        Lang lang = new LangDTO("", "").transformToObject();
        String json = mapper.writeValueAsString(lang);

        when(service.addLang(lang)).thenReturn(lang);

        mockMvc.perform(post("/api/v1/langs").contentType("application/json")
                                .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldReturnSuccess200_WhenGetListLangs() throws Exception {
        Lang lang = new LangDTO("Java", "imagem.svg").transformToObject();

        List<Lang> langs = List.of(lang);

        when(service.findAllLangs()).thenReturn(langs);

        mockMvc.perform(get("/api/v1/langs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0].title").value("Java"))
                .andExpect(jsonPath("$.[0].image").value("imagem.svg"))
                .andDo(print());

    }

    @Test
    public void shouldReturnBadRequest204_WhenLangListEmpty() throws Exception {

        when(service.findAllLangs()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/langs"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void shouldReturnSuccess200_WhenSearchByTitle() throws Exception {

        Lang lang = new LangDTO("Java", "imagem.svg").transformToObject();

        when(service.findLangByTitle("Java")).thenReturn(lang);

        mockMvc.perform(get("/api/v1/langs/{title}", "Java"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldReturnNotFound404_WhenSearchByTitle() throws Exception {

        when(service.findLangByTitle("Java")).thenThrow(LangNotFoundException.class);

        mockMvc.perform(get("/api/v1/langs/{title}", "Java"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test // Needs to implement the andExpect(jsonPath("$.title").value("Java"))
    public void shouldReturnSuccess200_WhenUpdateLang() throws Exception {

        String title = "java";
        String image = "image.svg";
        Lang lang = new Lang(title, image);
        LangDTO langDTO = new LangDTO(title, image);

        when(service.updateLang(lang.getId(), langDTO)).thenReturn(lang);

        String json = mapper.writeValueAsString(lang);

        mockMvc.perform(put("/api/v1/langs/" + lang.getId()).content(json).contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldReturnBadRequest400_WhenUpdateLang() throws Exception {
        Lang lang = new LangDTO("", "image.svg").transformToObject();

        String json = mapper.writeValueAsString(lang);

        mockMvc.perform(put("/api/v1/langs/" + lang.getId()).contentType("application/json").content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldReturnBadRquest404_WhenUpdateLang() throws Exception {

        String id = "123";

        LangDTO lang = new LangDTO("Java", "image.svg");

        when(service.updateLang(id, lang)).thenThrow(LangNotFoundException.class);

        String json = mapper.writeValueAsString(lang);

        mockMvc.perform(put("/api/v1/langs/" + id).contentType("application/json").content(json))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void shouldReturnSuccess200_WhenUpdateVoteLang() throws Exception {

        Lang lang = new LangDTO("Java", "image.svg").transformToObject();

        when(service.updateVoteLang(lang.getTitle())).thenReturn(lang.getTitle() + ": Voto cadastrado.");

        mockMvc.perform(patch("/api/v1/langs/vote/" + lang.getTitle()))
                .andExpect(status().isOk())
                .andExpect(content().string(lang.getTitle() + ": Voto cadastrado."))
                .andDo(print());
    }

    @Test
    public void shouldReturnNotFound404_WhenUpdateVoteLang() throws Exception {

        Lang lang = new LangDTO("Java", "image.svg").transformToObject();

        when(service.updateVoteLang(lang.getTitle())).thenThrow(LangNotFoundException.class);

        mockMvc.perform(patch("/api/v1/langs/vote/" + lang.getTitle()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void shouldReturnSuccess200_WhenDeleteLang() throws Exception {

        String langTitle = "Java";

        when(service.deleteLang(langTitle)).thenReturn(langTitle + " was deleted.");

        mockMvc.perform(delete("/api/v1/langs/" + langTitle))
                .andExpect(status().isOk())
                .andExpect(content().string(langTitle + " was deleted."))
                .andDo(print());
    }

    @Test
    public void shouldReturnNotFound404_WhenDeleteLang() throws Exception {
        String langTitle = "Java";

        when(service.deleteLang(langTitle)).thenThrow(LangNotFoundException.class);

        mockMvc.perform(delete("/api/v1/langs/" + langTitle))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
