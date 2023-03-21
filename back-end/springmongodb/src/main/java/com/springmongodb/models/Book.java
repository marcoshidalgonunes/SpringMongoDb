package com.springmongodb.models;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Document("Books")
@Data
public class Book {
    @Id
    public String id;

    @JsonProperty("name")
    public String Name;

    @JsonProperty("price")
    public BigDecimal Price;

    @JsonProperty("category")
    public String Category;

    @JsonProperty("author")
    public String Author;
}
