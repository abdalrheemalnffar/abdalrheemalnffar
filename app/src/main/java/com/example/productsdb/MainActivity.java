package com.example.productsdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText Name;
    EditText number;
    EditText address;
    ListView listView;
    ArrayList arrayList =new ArrayList();
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name=findViewById(R.id.name);
        number=findViewById(R.id.number);
        address=findViewById(R.id.address);
        listView=findViewById(R.id.listId);
    }

    public void saveToFirebase(View v){
        String parName=Name.getText().toString();
        String parnumber=number.getText().toString();
        String paraddress=address.getText().toString();

        Map<String, Object> parson =new HashMap<>();

        parson.put("user Name", parName);
        parson.put("user Number", parnumber);
        parson.put("user address", paraddress);

        db.collection( "parson")
                .add (parson)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    public void onSuccess (DocumentReference documentReference) {

                        Log.d( "TAG",  "Data Added successfully to D8: ");

                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(  "TAG",  "Failed to add to DB"+e);
            }
        });

    }
    public void ReadFromFirbase(View v) {
        db.collection("parson")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String data = document.getId() + " => " + document.getData().toString();
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                arrayList.add(data);
                            }
                            ArrayAdapter array = new ArrayAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arrayList);
                            listView.setAdapter(array);
                        } else {
                            Log.d("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}


