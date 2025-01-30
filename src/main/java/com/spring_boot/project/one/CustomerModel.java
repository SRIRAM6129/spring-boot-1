package com.spring_boot.project.one;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name ="name",nullable = false)
	private String name;

	@Column(name = "phone",nullable = false)
	private Long phone;


	@Column(name = "account",nullable = false)
	private Long account;


	@CreationTimestamp
	@Column(name = "createdAt",nullable = false)
	private LocalDateTime createAt;

	@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
	private List<ProductModel> products;

}

