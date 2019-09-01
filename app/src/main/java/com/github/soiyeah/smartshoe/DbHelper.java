package com.github.soiyeah.smartshoe;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DbHelper
{

    private DatabaseReference dbRef;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    public DbHelper(String ref)
    {
         database = FirebaseDatabase.getInstance();
         myRef = database.getReference();
         this.dbRef = myRef.child("SmartShoe").child(ref);
    }


    public DatabaseReference getDbRef() // getter
    {
        return dbRef;
    }


}
