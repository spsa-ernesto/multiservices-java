package com.multiservices.restful.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1")
@Api(value = "customer", tags = "Customer API")
public class CustomerController {
	
	@Autowired
    private ICustomerService customerService;

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value="/customers", method = RequestMethod.POST)
	public Customer addCustomer(@RequestBody Customer customer) {
		return this.customerService.addCustomer(customer);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value="/customers/kpi", method = RequestMethod.GET)
	public KpiData getKpi() {		
		return this.customerService.getKpi();
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value="/customers", method = RequestMethod.GET)
	public List<Customer>  getAll() {
		List<Customer> result = this.customerService.getAll();
		return result;
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value="/customers", method = RequestMethod.DELETE)
	public void deleteCustomer(@RequestBody Integer customerId) {
		this.customerService.deleteCustomer(customerId);
	}
}