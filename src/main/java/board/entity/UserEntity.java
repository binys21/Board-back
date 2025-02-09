package board.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="t_jpa_user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(unique = true)
    private String username;

    private String password;
    private String name;
    private String email;

    private String role;
