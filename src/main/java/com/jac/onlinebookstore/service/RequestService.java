package com.jac.onlinebookstore.service;

import com.jac.onlinebookstore.entity.Request;

import java.util.List;

public interface RequestService {
    List<Request> findAll();

    List<Request> findByUsername(String username);

    Request findById(int id);

    void save(Request request);

    void changeStatusById(int id, String status);
}
