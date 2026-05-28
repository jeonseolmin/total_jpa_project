package com.my.total_jpa_back.repository;
import com.my.total_jpa_back.common.entity.Gender;
import com.my.total_jpa_back.users.entitiy.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @Slf4j
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    // Slice : 무한 스크롤 용으로 자료가 필요할 때
    // 가볍다. 정보를 다음페이지 ?
    @Test @DisplayName("슬라이스 무한 스크롤")
    void sliceTest(){
        Pageable pageable = PageRequest.of(0,20,
                Sort.by("createdAt").descending()
        );
        Slice<Users> result = userRepository.findAll(pageable);
        List<Users> users = result.getContent();
        // 현재 페이지
        log.info("현재 페이지 : "+result.getNumber());
        // 다음 페이지 ?
        log.info(" IsNextPage? : "+result.hasNext());
        // 이전 페이지 ?
        log.info(" 이전 페이지 : "+result.hasPrevious());
        users.stream()
                .forEach(x -> log.info("All {} ",x));
    }
    //페이징 처리에 사용되는 클래스 : Pageable
    // 전체 회원 자료에서 10개 묶음
    // PageRequest.of
    @Test @DisplayName("회원 페이지로 가져오기 테스트 ")
    void pagingTest(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Users> result = userRepository.findAll(pageable);

        List<Users> users = result.getContent();
        // 전체 행 수
        log.info("전체 행 수 : "+ result.getTotalElements());
        // 전체 페이지
        log.info("전체 페이지 : " + result.getTotalPages());
        // 현재 페이지
        log.info("현재 페이지 : "+result.getNumber());
        // 다음 페이지 ?
        log.info(" IsNextPage? : "+result.hasNext());
        // 이전 페이지 ?
        log.info(" 이전 페이지 : "+result.hasPrevious());
        users.stream()
                .forEach(x -> log.info("All {} ",x));
    }

    // 최근 가입한 회원 정보 중 10번째 페이지를 추출
    // 한 페이지 당 30개씩 출력
    @Test @DisplayName("최근 가입한 회원 정보 중 10번째 , 한 페이지 당 30개씩 ")
    void pagingAndSort(){
        Pageable pageable = PageRequest.of(9,30,
                Sort.by("createdAt")
                        .descending()
        );
        Page<Users> result = userRepository.findAll(pageable);
        List<Users> users = result.getContent();
        users.stream()
                .forEach(x -> log.info("All {} ",x));
    }

    @Test @DisplayName("회원 이름을 오름차순 정렬 ")
    void orderByNameAscTest(){
        // 정렬 기계를 하나 세팅
        Sort sort = Sort
                .by("name")
                .ascending();

        // 전체 검색 할 때 Sort 기계를 삽입해서 정렬되도록 처리
        List<Users> users = userRepository.findAll(sort);
        users.stream().forEach(x ->log.info("name : {}", x.getName()));
    }

    @Test @DisplayName("최근 가입 회원 10명 출력(ID, Name)")
    void orderByCreatedAtDescTest(){
        Sort sort = Sort.by("createdAt").descending();
        List<Users> users = userRepository.findAll(sort);
        users.stream().limit(10)
                .forEach(x ->log.info("name : {}, ID : {}", x.getName(),x.getId()));
    }

    @Test @DisplayName("색상 오름차순 , 같은 색상 자료는 이름 내림차순 상위 100개 출력")
    void multiSortTest(){
        Sort sort = Sort.by("likeColor").ascending()
                .and(
                        Sort.by("name").descending()
                );
        List<Users> users = userRepository.findAll(sort);
        users.stream().limit(100)
                .forEach(x ->log.info("name : {}, color : {}", x.getName(),x.getLikeColor()));
    }

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
}