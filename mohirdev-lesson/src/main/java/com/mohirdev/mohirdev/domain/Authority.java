package com.mohirdev.mohirdev.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Entity
@Table(name = "mohirdev_authority")
public class Authority  implements Serializable {

    @Id
    @NonNull
    private String name;


    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
