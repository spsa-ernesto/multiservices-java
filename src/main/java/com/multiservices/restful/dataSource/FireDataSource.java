package com.multiservices.restful.dataSource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class FireDataSource {

    private Firestore db;

    FireDataSource() throws IOException {
        initialize();
    }

    private void initialize() throws IOException {

        String configPath = "C:\\Proyectos\\mutiservices-java\\src\\main\\resources\\serviceAccountKey.json";
        String databaseUrl = "https://spsa-desafio.firebaseio.com/";

        FileInputStream serviceAccount = new FileInputStream(configPath);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(databaseUrl)
                .build();

        FirebaseApp.initializeApp(options);

        db = FirestoreClient.getFirestore();
    }

    public Firestore getFireStore() {
        return db;
    }
}