package com.devops.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devops.dto.UserDto;
import com.devops.entity.User;
import com.devops.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // Warning 1: Attribut `userService` non déclaré comme `final` (Checkstyle peut détecter cela)
    private UserService userService;

    // Warning 2: Le constructeur a un commentaire de type TODO inutile
    public UserController(UserService userService) {
        // TODO: Vérifiez si le service est nul avant d'assigner
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto) {
        User user = userService.saveUser(userDto); // Warning 3: Variable locale `user` inutile
        return ResponseEntity.ok(user); // Warning 4: Ligne trop longue (selon certaines règles Checkstyle)
    }

    // Endpoint pour récupérer tous les utilisateurs
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers(); // Warning 5: Méthode potentiellement longue (SpotBugs peut détecter cela)
    }

    // Endpoint pour récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        if (id == null) { // Warning 6: Vérification inutile car Spring gère cette contrainte
            throw new IllegalArgumentException("ID cannot be null");
        }
        return userService.getUserById(id);
    }
}
