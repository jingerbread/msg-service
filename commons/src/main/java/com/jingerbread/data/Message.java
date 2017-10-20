package com.jingerbread.data;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Message {

    private String title;

    private String text;

    private String author;

    private String created;

    @JsonCreator
    public Message(@JsonProperty("title") String title, @JsonProperty("text") String text,
                   @JsonProperty("author")  String author, @JsonProperty("created")  String date) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.created = date;
    }
}
