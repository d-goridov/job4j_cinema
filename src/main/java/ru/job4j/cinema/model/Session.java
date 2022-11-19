package ru.job4j.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Session {
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private byte[] photo;

    public Session(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
