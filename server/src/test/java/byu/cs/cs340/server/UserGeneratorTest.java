package byu.cs.cs340.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import byu.cs.cs340.model.domain.User;
import byu.cs.cs340.server.dao.UserGenerator;

class UserGeneratorTest {

    @Test
    void testGenerateUsers_count() {

        List<User> users = UserGenerator.getInstance().generateUsers(20);
        Assertions.assertEquals(20, users.size());
        System.out.println(users);

        users = UserGenerator.getInstance().generateUsers(2);
        Assertions.assertEquals(2, users.size());
        System.out.println(users);

    }
}
