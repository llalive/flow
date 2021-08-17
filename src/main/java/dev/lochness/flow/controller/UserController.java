package dev.lochness.flow.controller;

import dev.lochness.flow.domain.User;
import dev.lochness.flow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController extends AuthenticatedUserContoller {

    private final UserService userService;

    @DeleteMapping("/api/users/karma")
    public ResponseEntity<Integer> spendKarma(@RequestParam Integer count) {
        User user = getCurrentUser();
        if (user.getKarma() < count) {
            return ResponseEntity.badRequest().build();
        }
        userService.removeKarma(user, count);
        return ResponseEntity.ok(user.getKarma());
    }
}
