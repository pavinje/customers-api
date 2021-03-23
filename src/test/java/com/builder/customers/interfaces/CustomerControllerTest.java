package com.builder.customers.interfaces;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.builder.customers.domain.CustomerDomain;
import com.builder.customers.exception.InvalidBirthdateException;
import com.builder.customers.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerControllerTest {

	private CustomerController controller;
	
	@InjectMocks
	private CustomerService service;

	private CustomerDomain domain;
	
	private HashMap<String, String> request;	
	
	private ResponseEntity<?> response;

	@BeforeEach
	public void setUp() throws Exception {
		service = Mockito.mock(CustomerService.class);
		controller = new CustomerController(service);
	}

	@Test
	public void shouldPostSuccessfully() throws ParseException, InvalidBirthdateException {
		givenCustomerIsValid();
		whenSaveCustomer();
		thenResponseCreated();
	}

	private void thenResponseCreated() {
		assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
	}

	@Test
	public void shouldSaveCustomerReturnPreConditionFailed() throws InvalidBirthdateException {
		givenCustomerIsNotValid();
		whenSaveCustomer();
		thenResponsePreconditionFailed();
	}

	private void thenResponsePreconditionFailed() {
		assertTrue(response.getStatusCode().equals(HttpStatus.PRECONDITION_FAILED));
	}

	private void givenCustomerIsNotValid() throws InvalidBirthdateException {
		
		Mockito.when(service.save(Mockito.any())).thenThrow(InvalidBirthdateException.class);
		
		domain = new CustomerDomain(UUID.randomUUID().toString());
		domain.setName("Joao");
		domain.setDocument("33333333333");
	}

	private void whenSaveCustomer() throws InvalidBirthdateException {
        ObjectMapper oMapper = new ObjectMapper();
        request = oMapper.convertValue(domain, HashMap.class);
        if (domain.getBirthdate() != null)
        	request.put("birthdate", new SimpleDateFormat("yyyy-MM-dd").format(domain.getBirthdate().getTime()));
		response = controller.post(request, UriComponentsBuilder.newInstance());
	}

	private void givenCustomerIsValid() throws ParseException, InvalidBirthdateException {

		
		domain = new CustomerDomain(UUID.randomUUID().toString());
		domain.setName("Joao");
		domain.setDocument("33333333333");
		
		Calendar birthdate = Calendar.getInstance();
		birthdate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("1991-04-01"));
		domain.setBirthdate(birthdate);

		Mockito.when(service.save(Mockito.any())).then(new Answer<CustomerDomain>() {

			@Override
			public CustomerDomain answer(InvocationOnMock invocation) throws Throwable {
				return new CustomerDomain(UUID.randomUUID().toString());
			}

		});
		
		
	}

}
