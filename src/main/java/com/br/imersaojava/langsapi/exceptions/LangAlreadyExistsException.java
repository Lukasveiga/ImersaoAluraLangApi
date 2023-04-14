package com.br.imersaojava.langsapi.exceptions;

public class LangAlreadyExistsException extends RuntimeException {

    public LangAlreadyExistsException() {}

    public LangAlreadyExistsException(String msg) {
        super(msg);
    }
}
