package com.example.data_trans.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users") // 테이블 이름과 매핑
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "users_seq")
    @Column(name = "id")  // 필드 이름과 매핑
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "status", nullable = false)
    private String status;
}
