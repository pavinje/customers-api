package com.builder.customers.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.builder.customers.domain.CustomerDomain;

public interface CustomerRepository extends CrudRepository<CustomerDomain, String>, QueryByExampleExecutor<CustomerDomain> {

}
