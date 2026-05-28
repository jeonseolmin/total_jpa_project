package com.my.total_jpa_back.users.controller;

import com.my.total_jpa_back.common.entitiy.Gender;
import com.my.total_jpa_back.common.exception.UserNotFoundException;
import com.my.total_jpa_back.users.dto.*;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import com.my.total_jpa_back.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// RestFul 한 API를 제공할 때 사용하는 어노테이션
@RestController @RequiredArgsConstructor
@RequestMapping("/api")
public class userController {
    private final UserRepository userRepository;
    private final UserService userService;

    // User Delete
    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "회원 삭제 완료";
    }

    // User update API
    // 수정 대상은 PathVariable 값은 RequestBody 받아서 수정
    @PutMapping("/users/{id}")
    public UserResponse update(@PathVariable Long id,
                               @RequestBody UserUpdateRequest request){
        return userService.update(id,request);
    }

    @PostMapping("/create")
    public UserResponse create(@RequestBody UserCreateRequest userCreateRequest){
        return  userService.create(userCreateRequest);
    }

    // 예외 처리 테스트
    @GetMapping("/users/{id}")
    public Users findById(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException());
    }

    // RequestBody Test
    @PostMapping("/test")
    // RequestBody : PostMan에서 Json으로 보낸 데이터를 받는 아이.
    public HelloResponse test(@RequestBody HelloRequest request){
        return HelloResponse.builder()
                .massage("안녕하세요 " + request.getName())
                .age(request.getAge()).build();
    }

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
