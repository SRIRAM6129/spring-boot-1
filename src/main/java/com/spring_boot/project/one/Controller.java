package com.spring_boot.project.one;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	private ServiceLayer service;

	@GetMapping("/")
	public String greet() {
		return "THE SERVER IS UP";
	}

	@GetMapping("/customer/show/all")
	public ResponseEntity<?> showAllCustomer() {
		try {

			List<CustomerModel> customers = service.showAllCustomer();
			return new ResponseEntity<>(customers, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping("/customer/show/{id}")
	public ResponseEntity<?> showCustomerById(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(service.showCustomerById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/customer/add")
	public ResponseEntity<?> addUser(@RequestBody CustomerModel customer) {
		try {

			return new ResponseEntity<>(service.addCustomer(customer), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@DeleteMapping("/customer/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(service.deleteCustomer(id), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@PutMapping("/customer/update/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerModel customer) {
		try {
			return new ResponseEntity<>(service.updateCustomer(id, customer), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping("/products/show/all")
	public ResponseEntity<?> showAllproducts() {
		try {

			List<ProductModel> products = service.showAllProduct();
			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping("/products/show/{id}")
	public ResponseEntity<?> showProductByCustomerId(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(service.showProductByCustomerId(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping("/products/add/{id}")
	public ResponseEntity<?> addProduct(@PathVariable("id") Long id, Double quantity) {
		try {
			return new ResponseEntity<>(service.addProduct(id, quantity), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
	}
}
