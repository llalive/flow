package dev.lochness.flow.service;

import dev.lochness.flow.domain.User;
import dev.lochness.flow.exceptions.NotFoundException;
import dev.lochness.flow.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersManager implements UserService {

    private final UserRepository userRepository;

    public UsersManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addKarma(User user, int count) {
        user.setKarma(user.getKarma() + count);
        return userRepository.save(user);
    }

    @Override
    public User removeKarma(User user, int count) {
        user.setKarma(user.getKarma() - count);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }
}
