package com.i2kiselev.springCourseProject.repository;

import com.i2kiselev.springCourseProject.model.Role;
import com.i2kiselev.springCourseProject.model.Roll;
import com.i2kiselev.springCourseProject.model.RollSet;
import com.i2kiselev.springCourseProject.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class RollSetRepositoryTest {
    @Autowired
    private RollSetRepository rollSetRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findById(){
        RollSet test = new RollSet();
        test.setName("testRollSet");
        rollSetRepository.save(test);
        RollSet product = rollSetRepository.findById(test.getId()).get();
        assertThat(product.getName()).isEqualTo("testRollSet");
    }

    @Test
    void findAll(){
        RollSet test1 = new RollSet();
        test1.setName("testRollSet1");
        RollSet test2 = new RollSet();
        test2.setName("testRollSet2");
        rollSetRepository.save(test1);
        rollSetRepository.save(test2);
        Iterable<RollSet> products = rollSetRepository.findAll();
        List<RollSet> productList = new ArrayList<>();
        products.forEach(productList::add);
        assertThat(productList.get(0).getName()).isEqualTo("testRollSet1");
        assertThat(productList.get(1).getName()).isEqualTo("testRollSet2");
    }

    @Test
    void findAllByUser(){
        User user = new User();
        user.setUsername("user");
        userRepository.save(user);
        RollSet test1 = new RollSet();
        test1.setName("testRollSet1");
        test1.setUser(user);
        rollSetRepository.save(test1);
        Iterable<RollSet> rollSets = rollSetRepository.findAllByUser(user);
        List<RollSet> rollSets1 = new ArrayList<>();
        rollSets.forEach(rollSets1::add);
        assertThat(rollSets1.get(0).getName()).isEqualTo("testRollSet1");
        assertThat(rollSets1.get(0).getUser()).isEqualTo(user);
    }

    @Test
    void findAllByStaff(){
        User user = new User();
        user.setUsername("user");
        ArrayList<Role> roles = new ArrayList<Role>();
        roles.add(new Role("ROLE_ADMIN"));
        user.setRoles(roles);
        userRepository.save(user);
        RollSet test1 = new RollSet();
        test1.setName("testRollSet1");
        test1.setUser(user);
        rollSetRepository.save(test1);
        Iterable<RollSet> rollSets = rollSetRepository.findAllByStaff();
        List<RollSet> rollSets1 = new ArrayList<>();
        rollSets.forEach(rollSets1::add);
        assertThat(rollSets1).isNotEmpty();
        assertThat(rollSets1.get(0).getName()).isEqualTo("testRollSet1");
    }
}
