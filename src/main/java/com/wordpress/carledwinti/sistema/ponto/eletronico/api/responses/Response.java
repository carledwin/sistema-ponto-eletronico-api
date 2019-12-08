package com.wordpress.carledwinti.sistema.ponto.eletronico.api.responses;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

    private T data;
    private List<String> errors;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<String> getErrors() {

        if(errors == null){
            this.errors = new ArrayList<>();
        }

        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
