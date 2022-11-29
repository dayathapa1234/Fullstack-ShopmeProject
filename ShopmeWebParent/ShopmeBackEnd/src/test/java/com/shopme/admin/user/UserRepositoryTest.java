package com.shopme.admin.user;
import static org.assertj.core.api.Assertions.assertThat;

import com.shopme.Role;
import com.shopme.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void testCreateUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class,1);
        User daya = new User("dayathapa@gmail.com","Random123","Daya","Thapa");
        daya.addRole(roleAdmin);

        User savedUser = repo.save(daya);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwo(){
        Role roleEditor = new Role(3);
        Role roleAssistant = entityManager.find(Role.class,5);
        User tia = new User("tialam24@hotmail.com","Coco99","Tia","Lam");
        tia.addRole(roleEditor);
        tia.addRole(roleAssistant);

        User savedUser = repo.save(tia);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUser(){
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
        User userTia = repo.findById(1).get();
        System.out.println(userTia.getFirstName());
        assertThat(userTia).isNotNull();
    }

    @Test
    public void testUpdateUserDetail(){
        User userTia = repo.findById(1).get();
        userTia.setEnabled(true);
        userTia.setEmail("tiajlam24@hotmail.com");
        repo.save(userTia);
    }

    @Test
    public void testUpdateUserRole(){
        User userTia = repo.findById(1).get();
        Role roleEditor = new Role(3);
        userTia.getRoles().remove(roleEditor);
        userTia.addRole(new Role(2));

        repo.save(userTia);
    }

    @Test
    public  void testDeletingUser(){
        Role roleShipper = entityManager.find(Role.class,4);
        User randomUser = new User("random@gmail.com","123","Abc","Abc");
        randomUser.addRole(roleShipper);
        repo.save(randomUser);
        User repoUser = repo.findByEmail("random@gmail.com");
        assertThat(repoUser.getFirstName()).isEqualTo(randomUser.getFirstName());

        repo.deleteByEmail("random@gmail.com");
        User repoDeletedUser = repo.findByEmail("random@gmail.com");
        assertThat(repoDeletedUser).isNull();
    }
}
