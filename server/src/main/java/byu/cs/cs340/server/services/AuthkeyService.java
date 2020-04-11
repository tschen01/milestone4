package byu.cs.cs340.server.services;

import byu.cs.cs340.server.dao.AuthkeyDAO;

public class AuthkeyService {
    public boolean verify(String authkey) {
        AuthkeyDAO authkeyDAO = new AuthkeyDAO();
        return authkeyDAO.verifyAuthkey(authkey);
    }
}
