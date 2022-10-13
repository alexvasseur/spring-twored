package com.example.alextwored;

import com.example.alextwored.person.Person;
import com.example.alextwored.person.PersonRepository;
import com.example.alextwored.user.User;
import com.example.alextwored.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Autowired
    private UserRepository userR;

    @Autowired
    private PersonRepository personR;

    @GetMapping("/")
    public ResponseEntity<String> load() {
        Person p = new Person("alex", 45);
        personR.save(p);

        User u = new User("guy", 38);
        userR.save(u);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/reset")
    public ResponseEntity<String> reset() {
        personR.deleteAll();
        userR.deleteAll();
        return ResponseEntity.ok("ok");
    }
}
