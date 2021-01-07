package com.pxinfo.pxdelivery.services;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pxinfo.pxdelivery.dto.OrderDTO;
import com.pxinfo.pxdelivery.dto.ProductDTO;
import com.pxinfo.pxdelivery.entities.Order;
import com.pxinfo.pxdelivery.entities.OrderStatus;
import com.pxinfo.pxdelivery.entities.Product;
import com.pxinfo.pxdelivery.repositories.OrderRepository;
import com.pxinfo.pxdelivery.repositories.ProductRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public List<OrderDTO> findAll(){
		List<Order> list = repository.findOrdersWithProducts();
		return list.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public OrderDTO insert(OrderDTO dto){
		Order order = new Order(null, dto.getAddress(), dto.getLatitude(), dto.getLongitude(),
				Instant.now(), OrderStatus.PENDING);
		for(ProductDTO p : dto.getProducts()) {
			Product product = productRepository.getOne(p.getId());
			order.getProducts().add(product);
		}
		repository.save(order);
		return new OrderDTO(order);
	}
}
