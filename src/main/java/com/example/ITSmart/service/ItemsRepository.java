package com.example.ITSmart.service;

import com.example.ITSmart.entity.Item;
import com.example.ITSmart.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ItemsRepository extends JpaRepository<Item, Integer> {

    Item findById(Long itemId);

    void deleteById(Long itemId);
}
