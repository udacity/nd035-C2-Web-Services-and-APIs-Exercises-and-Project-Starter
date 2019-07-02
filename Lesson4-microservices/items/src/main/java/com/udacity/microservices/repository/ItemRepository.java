package com.udacity.microservices.repository;

import com.udacity.microservices.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}


