package com.builder.customers.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.util.StringUtils;

import com.builder.customers.domain.CustomerDomain;
import com.builder.customers.exception.CustomerNotFoundException;
import com.builder.customers.exception.InvalidBirthdateException;
import com.builder.customers.repository.CustomerRepository;

public class CustomerServiceTest {

	private CustomerService service;

	private CustomerDomain domain;
	
	private String customerId = UUID.randomUUID().toString();

	@Mock
	CustomerRepository customerRepository;

	@BeforeEach
	public void setUp() throws Exception {
		customerRepository = Mockito.mock(CustomerRepository.class);
		service = new CustomerService(customerRepository);
	}

	@Test
	public void shouldSaveCustomerSuccessfully() throws ParseException, InvalidBirthdateException {
		givenCustomerIsValid();
		whenSaveCustomer();
		thenCustomerIsCreated();
	}

	@Test
	public void shouldSaveCustomerThrowsInvalidBirthdateException() {
		givenCustomerIsNotValid();
		whenSaveCustomerThrowInvalidArgumentException();
	}
	
	@Test
	public void shouldUpdatePartiallyCustomerSuccessfully() throws ParseException, InvalidBirthdateException, CustomerNotFoundException {
		givenCustomerIsValid();
		givenIsPreviousCustomer();
		whenUpdatePartiallyCustomer();
		thenCustomerIsUpdated();
	}

	private void thenCustomerIsUpdated() {
		assertTrue(StringUtils.hasText(domain.getId()));
		
	}
	
	private void givenIsPreviousCustomer() {
		Mockito.when(customerRepository.findById(Mockito.any(String.class))).then(new Answer<Optional<CustomerDomain>>() {

			@Override
			public Optional<CustomerDomain> answer(InvocationOnMock invocation) throws Throwable {
				return Optional.of(new CustomerDomain(UUID.randomUUID().toString()));
			}

		});
	}

	private void whenUpdatePartiallyCustomer() throws CustomerNotFoundException {
		domain = service.updatePartially(customerId, domain);
		
	}

	private void whenSaveCustomerThrowInvalidArgumentException() {

		Assertions.assertThrows(InvalidBirthdateException.class, () -> {
			service.save(domain);
		});

	}

	private void givenCustomerIsNotValid() {
		domain = new CustomerDomain(UUID.randomUUID().toString());
		domain.setName("Joao");
		domain.setDocument("33333333333");
	}

	private void thenCustomerIsCreated() {
		assertTrue(StringUtils.hasText(domain.getId()));
	}

	private void whenSaveCustomer() throws InvalidBirthdateException {
		domain = service.save(domain);
	}

	private void givenCustomerIsValid() throws ParseException {

		Mockito.when(customerRepository.save(Mockito.any(CustomerDomain.class))).then(new Answer<CustomerDomain>() {

			@Override
			public CustomerDomain answer(InvocationOnMock invocation) throws Throwable {
				return new CustomerDomain(UUID.randomUUID().toString());
			}

		});
		domain = new CustomerDomain(UUID.randomUUID().toString());
		domain.setName("Joao");
		domain.setDocument("33333333333");

		Calendar birthdate = Calendar.getInstance();
		birthdate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("1991-04-01"));
		domain.setBirthdate(birthdate);
	}

}
