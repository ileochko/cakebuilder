package ru.sfedu.cakebuilder;

import ru.sfedu.cakebuilder.models.enums.Status;

public class Answer<T> {
    private T data;
    private String answer;
    private Status status;

    public Answer(Status status,T data, String answer ){
        this.status = status;
        this.answer = answer;
        this.data = data;
    }
    public Answer(Status status, T data){
        this.status = status;
        this.data = data;
    }
    public Answer(Status status){
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
