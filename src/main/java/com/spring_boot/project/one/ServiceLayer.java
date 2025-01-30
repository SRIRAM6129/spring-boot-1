package com.spring_boot.project.one;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceLayer {

	@Autowired
	private CustomerRepository customerrepo;

	@Autowired
	private ProductRepository productrepo;

	private Logger LOGGER = LoggerFactory.getLogger(ServiceLayer.class);

	public void idChecker(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("The Id cannot be null");

		}
		if (id < 0) {
			throw new IllegalArgumentException("The id cannot be an negative value");
		}
	}

	public List<CustomerModel> showAllCustomer() {
		try {
			List<CustomerModel> customers = customerrepo.findAll();
			if (customers.isEmpty()) {
				LOGGER.info("THE DATABASE IS EMPTY");

			}
			return customers;

		} catch (Exception e) {
			LOGGER.error("exception while fetching user data " + e.getMessage());
			throw new RuntimeException("Error while fetching the user data");
		}
	}

	public CustomerModel showCustomerById(Long id) {
		idChecker(id);
		try {
			return customerrepo.findById(id)
					.orElseThrow(() -> new RuntimeException("The user is not found "));
		} catch (Exception e) {
			LOGGER.error("exception while fetching user data " + e.getMessage());
			throw new RuntimeException("Error while fetching the user data");
		}
	}

	public void addCustomer(Long id, CustomerModel customer) {

		idChecker(id);
		if (customer.getPhone() ==  null) {
			throw new IllegalArgumentException("Account cannot be null");
		}
		if(customer.getAccount() ==  null){
			throw new IllegalArgumentException("Account cannot be null");
		}
		if(customer.getPhone() < 0){
			throw new IllegalArgumentException("Phone number cannot be negative");
		}
		if(customer.getAccount() < 0){
			throw new IllegalArgumentException("Account number cannot be negative");
		}
		if(customerrepo.existsByPhone(customer.getPhone())){
			throw new IllegalArgumentException("Phone number already exist");
		}
		if (customerrepo.existsByAccount(customer.getAccount())) {
			throw new IllegalArgumentException("Account number already exist");
		}
		try {
			customerrepo.save(customer);
		} catch (Exception e) {
			LOGGER.error("Error while saving an new user" + e.getMessage());
			throw new RuntimeException("Cannot add new user");
		}
	}

	public void deleteCustomer(Long id ){
		idChecker(id);
		try {
			customerrepo.deleteById(id);
		} catch (Exception e) {
			LOGGER.error("Error deleting the customer" + e.getMessage());
			throw new RuntimeException("Cannot delete the customer");
		}
	}
}
