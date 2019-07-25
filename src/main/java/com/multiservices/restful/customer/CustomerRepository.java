package com.multiservices.restful.customer;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.*;
import com.multiservices.restful.util.MathUtil;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class CustomerRepository {
	
	public Customer addCustomer(@RequestBody Customer customer) {
		DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference().child("customers");
		customersRef.push().setValueAsync(customer);
		return customer;
	}

	private static List<Customer> customerList;
	
	public List<Customer>  getAll() {
		FirebaseDatabase.getInstance().getReference().child("customers")
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						customerList = new ArrayList<>();
						for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
							customerList.add(snapshot.getValue(Customer.class));
							System.out.println(snapshot.getValue(Customer.class));
						}
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {
						System.out.println("The read failed: " + databaseError.getCode());
					}
				});
		return customerList;
	}
	
	public KpiData getKpi() {

		List<Integer> customerAgeList = new ArrayList<Integer>();
		this.getAll();
		for (Customer customer : customerList) {
			customerAgeList.add(customer.getAge());
		}
		double averageAge = MathUtil.average(customerAgeList);
		double standardDeviation = MathUtil.standardDeviation(customerAgeList);

		return new KpiData(averageAge, standardDeviation);
	}
}
