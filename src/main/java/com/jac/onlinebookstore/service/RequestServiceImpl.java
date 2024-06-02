package com.jac.onlinebookstore.service;

import com.jac.onlinebookstore.dao.RequestRepository;
import com.jac.onlinebookstore.entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService{

    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository theRequestRepository) { requestRepository = theRequestRepository; }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public List<Request> findByUsername(String username) {
        return requestRepository.findByUsername(username);
    }

    @Override
    public Request findById(int id) {
        Optional<Request> result = requestRepository.findById(id);

        if (result.isPresent()) {
            return result.get();
        }
        else {
            throw new RuntimeException("Could not find request id - " + id);
        }
    }

    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }

    @Override
    public void changeStatusById(int id, String status) {
        Request request = this.findById(id);

        request.setStatus(status);

        requestRepository.save(request);
    }
}
