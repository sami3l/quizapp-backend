
package com.example.quizapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "roles")
public class Role {
    @Id
    private String id;
    private ERole name;

    public enum ERole {
        ROLE_USER,
        ROLE_ADMIN
    }

    public Role(ERole name) {
        this.name = name;
    }
}