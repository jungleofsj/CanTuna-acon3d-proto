package CanTuna.CanTunaacon3d.repository;

import CanTuna.CanTunaacon3d.domain.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User createUser(User user);

    Optional<User> findUserById(Long userId);

    Optional<User> findUserByName(String userName);

    List<User> findByUserType(Long userType);

    List<User> findAllUser();

    Long authUser(User user);

}
