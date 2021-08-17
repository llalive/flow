package dev.lochness.flow.service;

import dev.lochness.flow.domain.User;

public interface UserService {

    User addKarma(User user, int count);

    User removeKarma(User user, int count);

    User getUserById(Long userId);
}
