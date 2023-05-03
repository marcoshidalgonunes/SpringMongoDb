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

    @JsonProperty("Name")
    public String Name;

    @JsonProperty("Price")
    public BigDecimal Price;

    @JsonProperty("Category")
    public String Category;

    @JsonProperty("Author")
    public String Author;
}
