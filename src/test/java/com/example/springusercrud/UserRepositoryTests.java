package com.example.springusercrud;

import com.example.springusercrud.user.User;
import com.example.springusercrud.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Test
    public void testAddNew(){
        User user = new User();

        user.setEmail("AlexanderGraham@gmail.com");
        user.setPassword("Alex123456");
        user.setFirstName("Alax");
        user.setLastName("Graham");
        User userSave =repo.save(user);

        Assertions.assertThat(userSave).isNotNull();
        Assertions.assertThat(userSave.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAll(){
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);
        users.forEach(user -> System.out.println(user));
    }

    @Test
    public void testUpdate(){
        Integer userId = 1;
        Optional<User> optionalUser =  repo.findById(userId);
        User user = optionalUser.get();
        user.setPassword("hello20001");
        repo.save(user);

        User updateUser = repo.findById(userId).get();
        Assertions.assertThat(updateUser.getPassword()).isEqualTo("hello20001");

    }
}
