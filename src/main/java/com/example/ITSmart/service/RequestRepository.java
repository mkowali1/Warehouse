package com.example.ITSmart.service;

import com.example.ITSmart.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    void deleteById(Long requestId);

    Request findById(Long requestId);
}
