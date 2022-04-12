package com.example.closhare;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.closhare.R;
import com.example.closhare.CollectionFragment;
import com.example.closhare.CreateFragment;
import com.example.closhare.GalleryFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;
    private StorageReference mStorageRef;
    private Fragment activeFragment = new CollectionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        chipNavigationBar = findViewById(R.id.chipNavigation);

        chipNavigationBar.setItemSelected(R.id.collection, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new CollectionFragment()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.collection:
                        fragment = new CollectionFragment();
                        activeFragment = new CollectionFragment();
                        break;
                    case R.id.create:
                        fragment = new BottomSheet();
                        break;
                    case R.id.gallery:
                        fragment = new GalleryFragment();
                        activeFragment = new GalleryFragment();
                        break;

                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });
    }
}