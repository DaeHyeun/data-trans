package com.example.data_trans.service;

import com.example.data_trans.entity.Users;
import com.example.data_trans.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 사용자 저장
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    // 사용자 조회
    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 사용자 리스트 조회
    public Iterable<Users> getAllUsers() {
        return userRepository.findAll();
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
