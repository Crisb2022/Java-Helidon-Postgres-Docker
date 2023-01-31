package com.distribuida.db;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int id;
    private Author author;
    private String isbn;
    private String title;
    private double price;
}
