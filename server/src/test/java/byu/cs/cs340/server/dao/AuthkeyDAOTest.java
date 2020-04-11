package byu.cs.cs340.server.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthkeyDAOTest {
    AuthkeyDAO authkeyDAO = new AuthkeyDAO();

    @Test
    void addAuthkeyAndDelete() {
        String authkey = authkeyDAO.addAuthkey();

        Assertions.assertNotNull(authkey);

        boolean verify = authkeyDAO.verifyAuthkey(authkey);

        Assertions.assertTrue(verify);

        authkeyDAO.delete(authkey);
    }
}
