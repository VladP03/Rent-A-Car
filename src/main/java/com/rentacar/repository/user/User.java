package com.rentacar.repository.user;

import lombok.*;
import javax.persistence.*;

@Entity(name = "users")
@SequenceGenerator(name = "UserSeq", allocationSize = 1500)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserSeq")
    private Integer ID;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;
}
