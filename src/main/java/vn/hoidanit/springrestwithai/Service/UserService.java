package vn.hoidanit.springrestwithai.Service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.hoidanit.springrestwithai.Model.User;
import vn.hoidanit.springrestwithai.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User save(User user) {
        // ID do database tự sinh khi tạo mới.
        user.setId(null);

        Instant now = Instant.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return null;
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setAge(user.getAge());
        existingUser.setAddress(user.getAddress());
        existingUser.setGender(user.getGender());
        existingUser.setAvatar(user.getAvatar());
        existingUser.setUpdatedAt(Instant.now());

        return userRepository.save(existingUser);
    }

    @Transactional
    public boolean deleteById(Long id) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return false;
        }

        userRepository.delete(existingUser);
        return true;
    }
}
