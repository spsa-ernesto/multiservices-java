package com.multiservices.restful.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.multiservices.restful.dataSource.FireDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {
	
	//@Autowired
	//private FirebaseAuth db;
	
	//private FirebaseAuth.getInstance().
/*
	FirebaseDatabase
	.getInstance(FirebaseApp.getInstance())
	.setPersistenceEnabled(true);

	@Autowired
	private FirebaseConfig fbConfig;
*/

	@Autowired
	FireDataSource db;

	
	public Customer addCustomer(Customer customer) {	
		/*
		DocumentReference docRef = db
				.getFireStore()
				.collection("customers")
				.document(customer.getCustomerId().toString());
		
		ApiFuture<WriteResult> result = docRef.set(customer);
		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		*/
		return customer;
	}
	
	public List<Customer>  getAll() {		
		List<Customer> listResult = new ArrayList<Customer>();

		ApiFuture<QuerySnapshot> query = db.getFireStore().collection("customers").get();
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot = null;
		try {
			querySnapshot = query.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			listResult.add(document.toObject(Customer.class));
		}

		return listResult;
	}
	
	public KpiData getKpi() {		
		/*
		List<Integer> customerAgeList = new ArrayList<Integer>();		

		ApiFuture<QuerySnapshot> future = db.getFireStore().collection("customers").get();
		try {
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				customerAgeList.add(document.toObject(Customer.class).getAge());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		double averageAge = mathutil.average(customerAgeList);
		double standardDeviation = mathutil.standardDeviation(customerAgeList);
		*/
		
		//return new KpiData(averageAge, standardDeviation);
		return new KpiData(25, 35);
	}	

}
