package io.dumerz.secured.controller;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.dumerz.secured.model.User;

@RestController
@RequestMapping("api/user")
public class UserController {

    private static final List<User> USERS = Arrays.asList(
        new User(1, "zremud", "password", "email@mail.com", Instant.now(), Instant.now()),
        new User(2, "zremud2", "password2", "email2@mail.com", Instant.now(), Instant.now()),
        new User(3, "zremud3", "password3", "email3@mail.com", Instant.now(), Instant.now()),
        new User(4, "zremud4", "password4", "email4@mail.com", Instant.now(), Instant.now()),
        new User(5, "zremud5", "password5", "email5@mail.com", Instant.now(), Instant.now()),
        new User(6, "zremud6", "password6", "email6@mail.com", Instant.now(), Instant.now()),
        new User(7, "zremud7", "password7", "email7@mail.com", Instant.now(), Instant.now()),
        new User(8, "zremud8", "password8", "email8@mail.com", Instant.now(), Instant.now()),
        new User(9, "zremud9", "password9", "email9@mail.com", Instant.now(), Instant.now())
    );

    @GetMapping("/")
    public List<User> getAll() {
        return USERS.stream()
                    .map(user -> user)
                    .collect(toList());
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable @RequestBody int id) {
        return USERS.stream()
                    .filter(user -> id == user.getId())
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("User " + id + " not found."));
    }
}