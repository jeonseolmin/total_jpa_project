package com.my.total_jpa_back.users.repository;

import com.my.total_jpa_back.common.entitiy.Gender;
import com.my.total_jpa_back.users.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    // JPQL (자바 기반의 SQL)
    @Query("""
    SELECT DISTINCT u FROM Users u JOIN FETCH u.orders
""")
    List<Users> findAllWithOrders();

    // 1. 성별 조회
    List<Users> findByGender(Gender gender);

    // 2. 이름의 특정 문장을 포함하는 검색 :Containing (Like와 같다.)
    List<Users> findByNameContaining(String keyword);

    // 3. 좋아하는 색상 일치 자료 검색
    List<Users> findByLikeColor(String color);

    // 4. 색상과 성별로 검색하기
    // SELECT * FROM users WHERE like_color ='red' AND gender = 'female'
    List<Users> findByLikeColorAndGender(String color, Gender gender);

    // 5. email 중 특정 사이트포함 이메일 찾기
    List<Users> findByEmailContaining(String keyword);

    Slice<Users> findAllBy(Pageable pageable);
}
