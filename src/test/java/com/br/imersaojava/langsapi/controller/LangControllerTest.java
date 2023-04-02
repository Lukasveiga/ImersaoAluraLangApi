package com.br.imersaojava.langsapi.controller;

import com.br.imersaojava.langsapi.DTO.LangDTO;
import com.br.imersaojava.langsapi.model.Lang;
import com.br.imersaojava.langsapi.service.LangService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

        mockMvc.perform(post("/langs").contentType("application/json")
                                .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void shouldReturn400BadRequest_WhenCreateNewLangWithNullFields() throws Exception {

        Lang lang = new LangDTO("", "").transformToObject();
        String json = mapper.writeValueAsString(lang);

        when(service.addLang(lang)).thenReturn(lang);

        mockMvc.perform(post("/langs").contentType("application/json")
                                .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldReturnSuccess200_WhenGetListLangs() throws Exception {
        Lang lang = new LangDTO("Java", "imagem.svg").transformToObject();

        List<Lang> langs = List.of(lang);

        when(service.findAllLangs()).thenReturn(langs);

        mockMvc.perform(get("/langs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0].title").value("Java"))
                .andExpect(jsonPath("$.[0].image").value("imagem.svg"))
                .andDo(print());

    }

    @Test
    public void shouldReturnSuccess200_WhenSearchByTitle() throws Exception {

        Lang lang = new LangDTO("Java", "imagem.svg").transformToObject();

        when(service.findLangByTitle("Java")).thenReturn(lang);

        mockMvc.perform(get("/langs/{title}", "Java"))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
