package com.my.total_jpa_back.repository;

import com.my.total_jpa_back.common.entitiy.Gender;
import com.my.total_jpa_back.orders.entity.UserOrder;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @Slf4j
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Test
    @DisplayName("회원 전체 조회")
    void findAllTest(){
        //given
        //when
        List<Users>  users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(500);
        //then
    }

    @Test
    @DisplayName("성별 조회")
    void findByGenderTest(){
        //given
        //when
        List<Users>  users = userRepository.findByGender(Gender.Male);
        for(Users user : users) log.info("name = {}, gender = {}" ,user.getName(),user.getGender());
        //then
    }

    @Test
    @DisplayName("이름 조회")
    void findByNameContainingTest(){
        List<Users> users = userRepository.findByNameContaining("kim");
        for(Users user : users) log.info("name = {}" ,user.getName());
    }

    @Test
    @DisplayName("좋아하는 색상 조회")
    void findByLikeColorTest(){
        List<Users> users = userRepository.findByLikeColor("blue");
        for(Users user : users) log.info("name = {} likeColor = {}" ,user.getName(),user.getLikeColor());
    }

    @Test
    @DisplayName("좋아하는 색상과 성별 조회")
    void findByLikeColorAndGenderTest(){
        List<Users> users = userRepository.findByLikeColorAndGender("blue",Gender.Male);
        for(Users user : users) log.info("name = {} likeColor = {}, gender = {}" ,user.getName(),user.getLikeColor(),user.getGender());
    }

    @Test @Transactional
    @DisplayName("회원정보 조회 후 주문 정보 찾아오기")
    void findUserAndOrderInfoTest(){
        Users user = userRepository.findById(1L).orElseThrow();
        log.info("이름 : {}", user.getName());
        for(UserOrder order : user.getOrders()){
            log.info("제품명 : {} 가격 : {}"
            ,order.getProductName(),order.getPrice());
        }
    }

    @Test @Transactional
    @DisplayName("N+1 문제 확인")
    void nPlusOneTest(){
        List<Users> users = userRepository.findAll();
        for(Users user:users){
            log.info("이름 : {}" , user.getName());
            for(UserOrder order : user.getOrders()){
                log.info("주문 번호 : {} 제품명 {}",order.getId(),order.getProductName());
            }
        }
    }

    @Test @Transactional
    @DisplayName("JPQL로 가져오기")
    void joinTest(){
        List<Users> users = userRepository.findAllWithOrders();
        for(Users user:users){
            log.info("이름 : {}" , user.getName());
            for(UserOrder order : user.getOrders()){
                log.info("주문 번호 : {} 제품명 {}",order.getId(),order.getProductName());
            }
        }
    }
}