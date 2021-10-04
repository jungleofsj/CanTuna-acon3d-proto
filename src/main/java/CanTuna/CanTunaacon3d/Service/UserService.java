package CanTuna.CanTunaacon3d.Service;

import CanTuna.CanTunaacon3d.domain.User;
import CanTuna.CanTunaacon3d.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Long joinUser(User user) {
        if (this.isExistUser(user)) {
            return -1L;
        }
        userRepository.createUser(user);
        return user.getUserId();
    }

    private Boolean isExistUser(User user) {
        Optional<User> result = userRepository.findUserByName(user.getUserName());
        return !result.isEmpty();
    }

    public Optional<User> findUser(Long userId) {
        return userRepository.findUserById(userId);
    }
    public Optional<User> findUserbyName(String userName) {
        return userRepository.findUserByName(userName);
    }
    public List<User> findAllUser() {
        return userRepository.findAllUser();
    }

    public Long loginUser(User user){
        return userRepository.authUser(user);
    }


}
