package com.pxinfo.pxdelivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pxinfo.pxdelivery.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
