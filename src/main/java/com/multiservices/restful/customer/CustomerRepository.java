package com.multiservices.restful.customer;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.*;
import com.multiservices.restful.util.MathUtil;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class CustomerRepository {
	private static Integer nextCustomerId;
	private static List<Customer> customerList;

	private Integer getNextCustomerId() {
		FirebaseDatabase.getInstance().getReference().child("customers")
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						nextCustomerId = (int)(long) dataSnapshot.getChildrenCount() + 1;
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {
						System.out.println("The read failed: " + databaseError.getCode());
					}
				});
		return nextCustomerId;
	}
	
	public Customer addCustomer(@RequestBody Customer customer) {
		DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference().child("customers");
		customer.setCustomerId(getNextCustomerId());
		customersRef.push().setValueAsync(customer);
		return customer;
	}
	
	public List<Customer> getAll() {
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

	public void deleteCustomer(Integer customerId) {
		FirebaseDatabase.getInstance().getReference().child("customers")
				.orderByChild("customerId").equalTo(customerId)
				.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
							snapshot.getRef().removeValueAsync();
						}
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {
						System.out.println("Delete failed: " + databaseError.getCode());
					}
		});
	}
}
