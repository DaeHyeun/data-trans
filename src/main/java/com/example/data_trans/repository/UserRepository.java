package com.example.data_trans.repository;

import com.example.data_trans.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    // 기본적인 CRUD 메소드가 자동으로 제공됩니다.
    // 필요에 따라 추가적인 메소드도 작성할 수 있습니다.
}
