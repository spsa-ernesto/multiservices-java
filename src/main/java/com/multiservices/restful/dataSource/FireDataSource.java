package com.multiservices.restful.dataSource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FireDataSource {

    private ResourceLoader resourceLoader;

    @Autowired
    FireDataSource(ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;
        initialize();
    }

    private void initialize() throws IOException {
        // Fetch the service account key JSON file contents
        //FileInputStream serviceAccount =
        //        new FileInputStream("C:\\Proyectos\\mutiservices-java\\src\\main\\resources\\serviceAccountKey.json");

        Resource resource = resourceLoader.getResource("classpath:serviceAccountKey.json");
        InputStream targetStream = new FileInputStream(resource.getFile());

        // Initialize the app with a service account, granting admin privileges
        FirebaseOptions options = new FirebaseOptions.Builder()
                //.setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setCredentials(GoogleCredentials.fromStream(targetStream))
                .setDatabaseUrl("https://spsa-desafio.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);

        // As an admin, the app has access to read and write all data, regardless of Security Rules
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("restricted_access/secret_document");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println(document);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }
}