package com.builder.customers.interfaces;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.builder.customers.domain.CustomerDomain;
import com.builder.customers.exception.InvalidBirthdateException;
import com.builder.customers.service.CustomerService;
@RestController
public class CustomerController {

	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@RequestMapping(path = "/customers", method = RequestMethod.POST)
	public ResponseEntity<?> post(@RequestBody HashMap<String, String> domain, UriComponentsBuilder builder) {
		ResponseEntity<?> response = null;
		try {
			CustomerDomain customerDomain = new CustomerDomain();		
			customerDomain.setName(domain.get("name"));
			customerDomain.setDocument(domain.get("document"));
			if (domain.get("birthdate") != null) {
				Calendar birthdate = Calendar.getInstance();
				try {
					birthdate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(domain.get("birthdate")));
				} catch (Exception e) {
					throw new InvalidBirthdateException();
				}
				customerDomain.setBirthdate(birthdate);
			}
			customerDomain = this.service.save(customerDomain);
			UriComponents uriComponents = builder.path("/customers/{id}").buildAndExpand(customerDomain.getId());

		    HttpHeaders headers = new HttpHeaders();
		    headers.setLocation(uriComponents.toUri());
		    response = new ResponseEntity<Void>(headers, HttpStatus.CREATED);
			
		} catch (InvalidBirthdateException ex) {
			response = new ResponseEntity<ErrorMessage>(new ErrorMessage("422.001", "Invalid Birthdate"), HttpStatus.PRECONDITION_FAILED);
		} catch (Exception ex) {
			response = new ResponseEntity<ErrorMessage>(new ErrorMessage("500.001", "Internal error, try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(path = "/customers/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> patch(@PathVariable(name = "id") String id, @RequestBody HashMap<String, String> domain, UriComponentsBuilder builder) {
		ResponseEntity<?> response = null;
		try {
			CustomerDomain customerDomain = new CustomerDomain();		
			customerDomain.setName(domain.get("name"));
			customerDomain.setDocument(domain.get("document"));
			if (domain.get("birthdate") != null) {
				Calendar birthdate = Calendar.getInstance();
				try {
					birthdate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(domain.get("birthdate")));
				} catch (Exception e) {
					throw new InvalidBirthdateException();
				}
				customerDomain.setBirthdate(birthdate);
			}
			customerDomain = this.service.updatePartially(id, customerDomain);
			UriComponents uriComponents = builder.path("/customers/{id}").buildAndExpand(customerDomain.getId());

		    HttpHeaders headers = new HttpHeaders();
		    headers.setLocation(uriComponents.toUri());
		    response = new ResponseEntity<Void>(headers, HttpStatus.NO_CONTENT);
			
		} catch (InvalidBirthdateException ex) {
			response = new ResponseEntity<ErrorMessage>(new ErrorMessage("422.001", "Invalid Birthdate"), HttpStatus.PRECONDITION_FAILED);
		} catch (Exception ex) {
			response = new ResponseEntity<ErrorMessage>(new ErrorMessage("500.001", "Internal error, try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(path = "/customers/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> put(@PathVariable(name = "id") String id, @RequestBody HashMap<String, String> domain, UriComponentsBuilder builder) {
		ResponseEntity<?> response = null;
		try {
			CustomerDomain customerDomain = new CustomerDomain();		
			customerDomain.setName(domain.get("name"));
			customerDomain.setDocument(domain.get("document"));
			Calendar birthdate = Calendar.getInstance();
			try {
				birthdate.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(domain.get("birthdate")));
			} catch (Exception e) {
				throw new InvalidBirthdateException();
			}
			customerDomain.setBirthdate(birthdate);
			customerDomain = this.service.update(id, customerDomain);
			UriComponents uriComponents = builder.path("/customers/{id}").buildAndExpand(customerDomain.getId());

		    HttpHeaders headers = new HttpHeaders();
		    headers.setLocation(uriComponents.toUri());
		    response = new ResponseEntity<Void>(headers, HttpStatus.NO_CONTENT);
			
		} catch (InvalidBirthdateException ex) {
			response = new ResponseEntity<ErrorMessage>(new ErrorMessage("422.001", "Invalid Birthdate"), HttpStatus.PRECONDITION_FAILED);
		} catch (Exception ex) {
			response = new ResponseEntity<ErrorMessage>(new ErrorMessage("500.001", "Internal error, try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@RequestMapping(path = "/customers", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> get(CustomerDomain domain, UriComponentsBuilder builder) {
		ResponseEntity<?> response = null;
		try {
			List<CustomerDomain> customers = this.service.find(domain);
			response = new ResponseEntity<List<CustomerDomain>>(customers, HttpStatus.OK);
			
		/*} catch (InvalidBirthdateException ex) {
			response = new ResponseEntity<ErrorMessage>(new ErrorMessage("422.001", "Invalid Birthdate"), HttpStatus.PRECONDITION_FAILED);
		*/} catch (Exception ex) {
			response = new ResponseEntity<ErrorMessage>(new ErrorMessage("500.001", "Internal error, try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}
