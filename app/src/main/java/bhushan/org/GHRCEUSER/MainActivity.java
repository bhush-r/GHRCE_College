package bhushan.org.GHRCEUSER;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import bhushan.org.GHRCEUSER.authentication.LoginActivity;
import bhushan.org.GHRCEUSER.ebook.EbookActivity;
import bhushan.org.GHRCEUSER.paper.PaperActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private final String CHECKEDITEM = "checked_item";

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this, R.id.frame_layout);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);


        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        if (item.getItemId() == R.id.logout){
            auth.signOut();
            openLogin();
        }
        return true;
    }

    private void openLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() == null){
            openLogin();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
//used switch case but not working proper show me error but i am convert to if else if
        if (itemId == R.id.navigation_developer) {
            startActivity(new Intent(this, Developer.class));
//            Toast.makeText(this, "Developer", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.navigation_paper) {
//            Toast.makeText(this, "Previous Paper", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, PaperActivity.class));
        } else if (itemId == R.id.navigation_rate) {
            Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.navigation_ebook) {
//            Toast.makeText(this, "Ebooks", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, EbookActivity.class));
        } else if (itemId == R.id.navigation_theme) {
            Toast.makeText(this, "Theme", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.navigation_website) {
            startActivity(new Intent(this, Website.class));
//            Toast.makeText(this, "Website", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.navigation_share) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
        }else if (itemId == R.id.navigation_results) {
//            Toast.makeText(this, "Result", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Results.class));
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        super.onBackPressed();
    }
}