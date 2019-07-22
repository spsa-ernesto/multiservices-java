package com.multiservices.restful.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/customers")
@Api(value = "customer", tags = "Customer API")
public class CustomerController {
	
	@Autowired
    private ICustomerService customerService;
	
    /*
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }*/
		
	@RequestMapping(value="/", method = RequestMethod.POST)
	public Customer addCustomer(Customer customer) {		
		return this.customerService.addCustomer(customer);
	}

	@RequestMapping(value="/kpi", method = RequestMethod.GET)
	public KpiData getKpi() {		
		return this.customerService.getKpi();
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Customer>  getAll() {		
		return this.customerService.getAll();
	}	
/*
    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Integer id){
        //customerService.deleteCustomer(id);
        return new ResponseEntity("Cliente borrado satisfactoriamente.", HttpStatus.OK);
    } */
}