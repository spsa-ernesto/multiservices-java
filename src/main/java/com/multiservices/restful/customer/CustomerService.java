package com.multiservices.restful.customer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer addCustomer(Customer customer) {		
		return customerRepository.addCustomer(customer);
	}
	
	@Override
	public List<Customer> getAll() {		
		return customerRepository.getAll();
	}
	
	@Override
	public KpiData getKpi() {
		return customerRepository.getKpi();
	}	

}
