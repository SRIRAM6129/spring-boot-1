package com.spring_boot.project.one;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

	List<ProductModel> findByCustomer(CustomerModel customer);

}
