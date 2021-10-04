package CanTuna.CanTunaacon3d.Service;

import CanTuna.CanTunaacon3d.domain.User;
import CanTuna.CanTunaacon3d.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    void joinUser() {
        //given
        User user = new User();
        user.setUserName("user1");
        user.setUserType(1L);

        System.out.println(user.getUserName());
        //when
        Long saveId = userService.joinUser(user);
        //then
        User findUser = userService.findUser(saveId).get();

        System.out.println(findUser.getUserId());
        System.out.println(findUser.getUserName());

        Assertions.assertThat(user.getUserName()).isEqualTo(findUser.getUserName());
    }

    @Test
    void findOne() {

        User user = new User();
        user.setUserName("user1");
        user.setUserType(1L);
        System.out.println(userRepository.findUserByName(user.getUserName()));

    }
    @Test
    void findUser() {

    }
}