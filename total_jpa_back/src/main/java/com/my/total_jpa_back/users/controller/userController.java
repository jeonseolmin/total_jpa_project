package com.my.total_jpa_back.users.controller;

import com.my.total_jpa_back.common.Gender;
import com.my.total_jpa_back.common.OrderStatus;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// RestFul 한 API를 제공할 때 사용하는 어노테이션
@RestController @RequiredArgsConstructor
@RequestMapping("/api")
public class userController {
    private final UserRepository userRepository;

    //전체 리스트를 요청하는 컨트롤러
    @GetMapping("/users")
    public List<Users> findAll(){
        return userRepository.findAll();
    }

    @GetMapping("/gender/{gender}")
    public List<Users> findByGender(@PathVariable Gender gender){
        return userRepository.findByGender(gender);
    }

    @GetMapping("/name")
    public List<Users> findByName(@RequestParam String keyword){
        return userRepository.findByNameContaining(keyword);
    }

    @GetMapping("/email")
    public List<Users> findByEmailContaining(@RequestParam String email){
        return userRepository.findByEmailContaining(email);
    }

    @GetMapping("/color")
    public List<Users> findByColor(@RequestParam String color){
        return userRepository.findByLikeColor(color);
    }

    @GetMapping("/gender-color")
    public List<Users> findByColor(
            @RequestParam("color") String color,
            @RequestParam("gender") Gender gender){
        return userRepository.findByLikeColorAndGender(color,gender);
    }

    @GetMapping("/sort")
    public List<Users> sort(){
        Sort sort = Sort.by("name").ascending() .and(
                Sort.by("createdAt").descending()
        );
        return userRepository.findAll(sort);
    }

    @GetMapping("/page")
    public Page<Users> findAllPage(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "10")int size){
        Pageable pageable = PageRequest.of(page,size,
                Sort.by("createdAt").descending()
        );
        return userRepository.findAll(pageable);
    }

    // Slice
    @GetMapping("/slice")
    public Slice<Users> findAllSlice(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("createdAt")
                                .descending()
                );

        return userRepository.findAllBy(pageable);
    }
}
