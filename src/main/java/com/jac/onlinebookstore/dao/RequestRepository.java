package com.jac.onlinebookstore.dao;

import com.jac.onlinebookstore.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByUsername(String username);

    List<Request> findByStatus(String status);
}
