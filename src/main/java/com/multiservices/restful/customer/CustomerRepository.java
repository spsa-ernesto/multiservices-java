package com.multiservices.restful.customer;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.*;
import com.multiservices.restful.util.MathUtil;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public class CustomerRepository {
	private static List<Customer> resultList;
	private static List<Customer> customerList;
	private static Integer nextCustomerId;

	public Customer addCustomer(@RequestBody Customer customer) {
		DatabaseReference customersRef = FirebaseDatabase.getInstance().getReference().child("customers");
		customersRef.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {
						nextCustomerId = (int) (long) dataSnapshot.getChildrenCount() + 1;
						customer.setCustomerId(nextCustomerId);
						customersRef.push().setValueAsync(customer);
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {
						System.out.println("The read failed: " + databaseError.getCode());
					}
				});
		return customer;
	}

	public List<Customer> getAll() {
		getAll(list -> resultList = list);
		return resultList;
	}

	private void getAll(FirebaseCallback firebaseCallback) {
		customerList = new ArrayList<>();
		FirebaseDatabase.getInstance().getReference().child("customers")
			.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					customerList.clear();
					for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
						customerList.add(snapshot.getValue(Customer.class));
					}
					firebaseCallback.onCallback(customerList);
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {
					System.out.println("The read failed: " + databaseError.getCode());
				}
			});
	}
	
	public KpiData getKpi() {
		List<Integer> customerAgeList = new ArrayList<Integer>();
		getAll(list -> resultList = list);

		for (Customer customer : resultList) {
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

	private interface FirebaseCallback {
		void onCallback(List<Customer> list);
	}
}
