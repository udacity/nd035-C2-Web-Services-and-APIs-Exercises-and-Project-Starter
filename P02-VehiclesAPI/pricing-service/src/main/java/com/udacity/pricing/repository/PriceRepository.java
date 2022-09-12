package com.udacity.pricing.repository;

import org.springframework.data.repository.CrudRepository;

import com.udacity.pricing.model.Price;

public interface PriceRepository extends CrudRepository<Price, Long> {

}
