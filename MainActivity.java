package com.example.lab_terminal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
private FirebaseDatabase myDataBase;
private DatabaseReference myDataBaseRef;
private DatabaseReference myDataBaseRefFirst;
Integer user_number = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDataBase = FirebaseDatabase.getInstance();
        myDataBaseRef = myDataBase.getReference("users");
        myDataBaseRefFirst = myDataBase.getReference("users").child("user1");
        EditText name = findViewById(R.id.Name);
        EditText email = findViewById(R.id.email);
        TextView data = findViewById(R.id.data);
        Button insert = findViewById(R.id.insertButton);
        Button clear = findViewById(R.id.clearButton);
        Button delete = findViewById(R.id.delete);
        Button read = findViewById(R.id.readButton);
        Button update = findViewById(R.id.updateButton);
        Button readAll = findViewById(R.id.readAllButton);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp =name.getText().toString();
                String temp1 =email.getText().toString();
                if (temp.isEmpty() && email.length()==0)
                {
                    toaster("Empty Field");
                }
                else
                {
                    user_number++;
                    String user = "user"+user_number;
                    user.toString();
                    myDataBaseRef.child(user).child("name").setValue(temp);
                    myDataBaseRef.child(user).child("email").setValue(temp1);
                    toaster("Your Information insered successfully");
                }

            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<=user_number;i++)
                {}
                myDataBaseRefFirst.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String info="";
                        Map<String,Object> data1 = (Map<String, Object>) snapshot.getValue();
                        info +="NAme : " + data1.get("name").toString();
                        info += "\n"+"Email : "+data1.get("email").toString();
                        data.setText(info);
                        toaster("Your Information Feched successfully");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<=user_number;i++)
                {}
                myDataBaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String info="";
                        Map<String,Object> data1 =new HashMap<>();
                        data1.put("user1/name",name.getText().toString());
                        data1.put("user1/email",email.getText().toString());
                        myDataBaseRef.updateChildren(data1);
                        toaster("Your Information Updated successfully");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        readAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<=user_number;i++)
                {}
                myDataBaseRefFirst.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String info="";
                        for (DataSnapshot mysnapshot:snapshot.getChildren()
                             ) {
                            Map<String,Object> data1 = (Map<String, Object>) snapshot.getValue();
                            info+=snapshot.getKey()+"\n";
                            info+=data1.get("name").toString();
                            info+=data1.get("email").toString();
                        }
                       // Map<String,Object> data1 = (Map<String, Object>) snapshot.getValue();
                        data.setText(info);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<=user_number;i++)
                {}
                myDataBaseRefFirst.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       // myDataBaseRef.child("user1").removeValue();
                      // myDataBaseRef.child("user1").child("name").removeValue();
                       myDataBaseRef.child("user1").child("email").removeValue();
                       //myDataBaseRef.child("user1").removeValue();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<=user_number;i++)
                {}
                myDataBaseRefFirst.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myDataBaseRef.removeValue();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });



    }
    public void toaster(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }
}