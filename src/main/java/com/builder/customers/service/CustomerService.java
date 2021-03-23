package com.builder.customers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.builder.customers.domain.CustomerDomain;
import com.builder.customers.exception.CustomerNotFoundException;
import com.builder.customers.exception.InvalidBirthdateException;
import com.builder.customers.repository.CustomerRepository;

@Service
public class CustomerService {
	
	private final CustomerRepository repository;
	
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}
	
	public CustomerDomain save(CustomerDomain domain) throws InvalidBirthdateException {
		
		if (domain.getBirthdate() == null) {
			throw new InvalidBirthdateException();
		}
		domain.setId(UUID.randomUUID().toString());
		return this.repository.save(domain);
	}

	public CustomerDomain updatePartially(String customerId, CustomerDomain domain) throws CustomerNotFoundException {
		
		Optional<CustomerDomain> domainDB = this.repository.findById(customerId);
		if (domainDB.isPresent()) {
			domain.setBirthdate((domain.getBirthdate() != null ? domain.getBirthdate() : domainDB.get().getBirthdate()));
			domain.setDocument((StringUtils.hasText(domain.getDocument()) ? domain.getDocument() : domainDB.get().getDocument()));
			domain.setName(StringUtils.hasText(domain.getName()) ? domain.getName() : domainDB.get().getName());
			domain.setId(customerId);
			return this.repository.save(domain);
		} else {
			throw new CustomerNotFoundException();
		}
	}
	
	public CustomerDomain update(String customerId, CustomerDomain domain) throws CustomerNotFoundException {
		
		Optional<CustomerDomain> domainDB = this.repository.findById(customerId);
		if (domainDB.isPresent()) {
			domain.setId(customerId);
			return this.repository.save(domain);
		} else {
			throw new CustomerNotFoundException();
		}
	}
	
	public List<CustomerDomain> find(CustomerDomain domain) {
		Example<CustomerDomain> example = Example.of(domain);
		List<CustomerDomain> result = new ArrayList<CustomerDomain>();
		this.repository.findAll(example).iterator().forEachRemaining(result::add);
		return result;
	
	}

}
