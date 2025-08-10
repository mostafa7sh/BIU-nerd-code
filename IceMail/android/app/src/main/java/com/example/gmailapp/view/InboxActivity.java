
package com.example.gmailapp.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmailapp.R;
import com.example.gmailapp.model.Label;
import com.example.gmailapp.model.Mail;
import com.example.gmailapp.viewmodel.InboxViewModel;
import com.example.gmailapp.viewmodel.LabelViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity {

    private InboxViewModel viewModel;
    private MailAdapter adapter;
    private List<Mail> allMails = new ArrayList<>();
    private RecyclerView recyclerView;
    private FloatingActionButton fabCompose;
    private MaterialToolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        viewModel = new ViewModelProvider(this).get(InboxViewModel.class);
        toolbar = findViewById(R.id.toolbarInbox);
        setSupportActionBar(toolbar);
        LabelViewModel labelViewModel = new ViewModelProvider(this).get(LabelViewModel.class);
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String jwt = prefs.getString("jwt", null);
        if (jwt != null) {
            labelViewModel.getLabels(jwt).observe(this, labels -> {
                if (labels != null) {
                    populateDrawerWithLabels(labels);
                }
            });
        }
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            int id = item.getItemId();
            if (id == R.id.nav_drafts) {
                loadDrafts();
                return true;
            } else if (id == R.id.nav_all) {
                loadMails();
                return true;
            } else if (id == R.id.nav_labels) {
                startActivity(new Intent(this, LabelActivity.class));
                return true;
            } else if (id == R.id.nav_logout) {
                prefs.edit().remove("jwt").apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_important) {
                loadImportant();
                return true;
            }

            return false;
        });
        recyclerView = findViewById(R.id.recyclerViewMails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MailAdapter(new ArrayList<>());
        adapter.setViewModel(viewModel);
        recyclerView.setAdapter(adapter);
        fabCompose = findViewById(R.id.fabCompose);
        fabCompose.setOnClickListener(v -> startActivity(new Intent(this, ComposeActivity.class)));

        loadMails();
    }

    private void loadMails() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String jwt = prefs.getString("jwt", null);
        if (jwt == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        try {
            viewModel.getMails(jwt).observe(this, mails -> {
                if (mails != null) {
                    allMails.clear();
                    allMails.addAll(mails);
                    adapter.updateList(allMails);
                } else {
                    Toast.makeText(this, "Failed to load mails", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e("ERROR", "Crash in loadMails", e);
        }
    }

    private void loadDrafts() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String jwt = prefs.getString("jwt", null);
        if (jwt == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        viewModel.getMails(jwt).observe(this, mails -> {
            if (mails != null) {
                List<Mail> drafts = new ArrayList<>();
                for (Mail mail : mails) {
                    if (mail.isDraft()) {
                        drafts.add(mail);
                    }
                }
                adapter.updateList(drafts);
            } else {
                Toast.makeText(this, "Failed to load drafts", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadImportant() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String jwt = prefs.getString("jwt", null);
        if (jwt == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        viewModel.getImportantMails(jwt).observe(this, mails -> {
            if (mails != null) {
                adapter.updateList(mails);
            } else {
                Toast.makeText(this, "Failed to load important mails", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inbox_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().isEmpty()) {
                    adapter.updateList(allMails);
                } else {
                    SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
                    String jwt = prefs.getString("jwt", null);
                    if (jwt != null) {
                        viewModel.searchMails(jwt, newText).observe(InboxActivity.this, result -> {
                            if (result != null) {
                                adapter.updateList(result);
                            } else {
                                Toast.makeText(InboxActivity.this, "Search failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            prefs.edit().remove("jwt").apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMailsByLabel(String labelId) {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String jwt = prefs.getString("jwt", null);
        if (jwt == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        viewModel.getMailsByLabel(jwt, labelId).observe(this, mails -> {
            if (mails != null) {
                adapter.updateList(mails);
            } else {
                Toast.makeText(this, "Failed to load label mails", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateDrawerWithLabels(List<Label> labels) {
        NavigationView navigationView = findViewById(R.id.navigationView);
        Menu menu = navigationView.getMenu();
        menu.removeGroup(R.id.label_group);
        for (Label label : labels) {
            MenuItem item = menu.add(R.id.label_group, Menu.NONE, Menu.NONE, label.getName());
            item.setOnMenuItemClickListener(menuItem -> {
                loadMailsByLabel(label.getId());
                return true;
            });
        }
    }
}
