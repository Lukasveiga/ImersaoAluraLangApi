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
    public void shouldReturnSuccess_WhenCreateNewLang() throws Exception {

        Lang lang = new LangDTO("Java", "imagem.svg").transformToObject();
        String json = mapper.writeValueAsString(lang);

        when(service.addLang(lang)).thenReturn(lang);

        mockMvc.perform(post("/langs").contentType("application/json")
                                .content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    public void shouldReturnSuccess_WhenSearchByTitle() throws Exception {

        Lang lang = new LangDTO("Java", "imagem.svg").transformToObject();

        when(service.findLangByTitle("Java")).thenReturn(lang);

        mockMvc.perform(get("/langs/{title}", "Java"))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
