package com.my.total_jpa_back.users.service;

import com.my.total_jpa_back.common.exception.UserNotFoundException;
import com.my.total_jpa_back.orders.dto.PageResponse;
import com.my.total_jpa_back.users.dto.UserCreateRequest;
import com.my.total_jpa_back.users.dto.UserResponse;
import com.my.total_jpa_back.users.dto.UserUpdateRequest;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public PageResponse<UserResponse> findPages(Pageable pageable){
        Page<Users> users = userRepository.findAll(pageable);

        Page<UserResponse> responses =
                users.map(user -> UserResponse.from(user));
        return new PageResponse<>(responses);
    }
    //User Update
    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        // 먼저 수정할 id가 실제 존재하는지 찾아봐야 합니다.
        Users user = userRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setLikeColor(request.getLikeColor());

        return UserResponse.from(user);
    }
    //User Create
    @Transactional
    public UserResponse create(UserCreateRequest request){
        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setLikeColor(request.getLikeColor());

        //Repo에 저장요청
        //Repo의 save() 메서드는 기본적으로 저장하고 난 다음
        // Entity를 반환 해줍니다.
        Users savedUser = userRepository.save(user);
        // Entity를 Dto로 변환
        return UserResponse.from(savedUser);
    }


    @Transactional
    public void delete(Long id) {
        //해당하는 아이디가 존재하는지 확인하고 없으면 exception
        Users user = userRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException());
        userRepository.delete(user);
    }
}
