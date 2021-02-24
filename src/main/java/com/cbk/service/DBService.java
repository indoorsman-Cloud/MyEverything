package com.cbk.service;

import com.cbk.dao.InitDAO;
public class DBService {
    private final InitDAO initDAO = new InitDAO();

    public void init() {
        initDAO.init();
    }
}
