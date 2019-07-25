package com.multiservices.restful.customer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1")
@Api(value = "customer", tags = "Customer API")
public class CustomerController {
	
	@Autowired
    private ICustomerService customerService;
		
	@RequestMapping(value="/customers", method = RequestMethod.POST)
	public Customer addCustomer(@RequestBody Customer customer) {
		return this.customerService.addCustomer(customer);
	}

	@RequestMapping(value="/customers/kpi", method = RequestMethod.GET)
	public KpiData getKpi() {		
		return this.customerService.getKpi();
	}
	
	@RequestMapping(value="/customers", method = RequestMethod.GET)
	public List<Customer>  getAll() {		
		return this.customerService.getAll();
	}
}