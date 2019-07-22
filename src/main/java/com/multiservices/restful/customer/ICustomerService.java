package com.multiservices.restful.customer;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface ICustomerService {

	public Customer addCustomer(Customer customer);
	
	public List<Customer> getAll();
	
	public KpiData getKpi();
}
