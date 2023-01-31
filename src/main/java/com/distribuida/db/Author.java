package com.distribuida.db;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private int id;
    private String firstName;
    private String lastName;
}
