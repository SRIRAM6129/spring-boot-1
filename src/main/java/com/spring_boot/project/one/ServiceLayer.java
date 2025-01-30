package com.spring_boot.project.one;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

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
			LOGGER.info("The user data has been fetched");
			return customers;

		} catch (Exception e) {
			LOGGER.error("exception while fetching  data " + e.getMessage());
			throw new RuntimeException("Error while fetching the user data : "+ e.getMessage());
		}
	}

	public CustomerModel showCustomerById(Long id) {
		idChecker(id);
		LOGGER.info("The User data has been fetched ");
		return customerrepo.findById(id).orElseThrow(() -> {
			LOGGER.error("The User not found in the database");
			return new IllegalStateException("The user is not found ");
		});

	}

	public CustomerModel addCustomer(CustomerModel customer) {

		if (customer.getPhone() == null) {
			throw new IllegalArgumentException("Account cannot be null");
		}
		if (customer.getAccount() == null) {
			throw new IllegalArgumentException("Account cannot be null");
		}
		if (customer.getPhone() < 0) {
			throw new IllegalArgumentException("Phone number cannot be negative");
		}
		if (customer.getAccount() < 0) {
			throw new IllegalArgumentException("Account number cannot be negative");
		}

		try {
			LOGGER.info("Customer Saved");
			return customerrepo.save(customer);
		} catch (Exception e) {
			LOGGER.error("Error while saving an new user : " + e.getMessage());
			throw new RuntimeException("Cannot add new user : "+ e.getMessage());
		}
	}

	public CustomerModel deleteCustomer(Long id) {
		idChecker(id);

		try {
			CustomerModel customer = customerrepo.findById(id)
					.orElseThrow(() -> new IllegalStateException("The user is not found"));
			customerrepo.deleteById(id);
			LOGGER.info("The customer has been deleted");
			return customer;
		} catch (Exception e) {
			LOGGER.error("Error deleting the customer" + e.getMessage());
			throw new RuntimeException("Cannot delete the customer : ");
		}
	}

	@Transactional
	public CustomerModel updateCustomer(Long id, CustomerModel customer) {
		idChecker(id);
		try {
			CustomerModel existing_customer = customerrepo.findById(id)
					.orElseThrow(() -> new IllegalStateException("The user is not found"));
			if (customer.getName().isEmpty()) {
				throw new IllegalArgumentException("The name Cannot be empty");
			}
			if (customer.getAccount() == null) {
				throw new IllegalArgumentException("Account number is empty");
			}
			existing_customer.setName(customer.getName());
			existing_customer.setAccount(customer.getAccount());
			existing_customer.setPhone(customer.getPhone());
			LOGGER.info("The user" + id + "update successfully");
			return customerrepo.save(existing_customer);
		} catch (Exception e) {
			LOGGER.error("The user cannot be updated" + e.getMessage());
			throw new RuntimeException("Cannot update the customer" + e.getMessage());
		}
	}

	// --------------------------------------------------PRODUCT------------------------------------------------------
	public List<ProductModel> showAllProduct() {
		try {
			List<ProductModel> products = productrepo.findAll();
			if (products.isEmpty()) {
				LOGGER.info("THE DATABASE IS EMPTY");

			}
			LOGGER.info("The user data has been fetched");
			return products;
		} catch (Exception e) {
			LOGGER.error("exception while fetching  the product data " + e.getMessage());
			throw new RuntimeException("Error while fetching the product data");
		}
	}

	public List<ProductModel> showProductByCustomerId(Long id) {
		idChecker(id);
		try {
			CustomerModel customer = customerrepo.findById(id).orElseThrow(() -> {
				LOGGER.error("The customer not found");
				return new IllegalArgumentException("The  customer not found ");
			});
			return productrepo.findByCustomer(customer);
		} catch (Exception e) {
			LOGGER.error("The product Updation failed : " + e.getMessage());
			throw new RuntimeException("Something went wrong : " + e.getMessage());
		}
	}

	public ProductModel addProduct(Long id, Double quantity) {
		idChecker(id);
		if (quantity == null) {
			throw new IllegalArgumentException("The quantity cannot be null");
		}
		if (quantity < 0) {
			throw new IllegalArgumentException("The quantity cannot be an negative value");
		}
		try {
			CustomerModel customer = customerrepo.findById(id)
					.orElseThrow(() -> new IllegalStateException("The user is not found"));
			ProductModel product = ProductModel.builder().quantity(quantity).customer(customer).build();
			return productrepo.save(product);
		} catch (Exception e) {
			LOGGER.error("The product Updation failed : " + e.getMessage());
			throw new RuntimeException("Something went wrong : " + e.getMessage());
		}
	}
}
