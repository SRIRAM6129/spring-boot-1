package com.spring_boot.project.one;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

	boolean existsByPhone(Long phone);

	boolean existsByAccount(Long account);

	CustomerModel findByAccount(Long account);
}
