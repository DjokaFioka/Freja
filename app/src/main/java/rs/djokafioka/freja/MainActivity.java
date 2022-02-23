package rs.djokafioka.freja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rs.djokafioka.freja.adapter.PersonListAdapter;
import rs.djokafioka.freja.model.Person;
import rs.djokafioka.freja.network.response.ApiResponse;
import rs.djokafioka.freja.network.response.PersonResponse;
import rs.djokafioka.freja.network.response.ErrorResponse;
import rs.djokafioka.freja.viewmodel.PersonListViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PersonListAdapter mAdapter;
    private List<Person> mPersonList;
    private PersonListViewModel mPersonListViewModel;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter =  new PersonListAdapter(mPersonList);
        mRecyclerView.setAdapter(mAdapter);

        mPersonListViewModel = new ViewModelProvider(this).get(PersonListViewModel.class);
        mProgressBar.setVisibility(View.VISIBLE);

        mPersonListViewModel.getPersonListObserver().observe(this, new Observer<ApiResponse<PersonResponse, ErrorResponse>>() {
            @Override
            public void onChanged(ApiResponse<PersonResponse, ErrorResponse> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response != null) {
                    if (response.isSuccess()) {
                        mPersonList = response.getSuccess().getPersonList();
                        mAdapter.updatePersonList(mPersonList);
                    } else {
                        Toast.makeText(MainActivity.this, response.getFailure().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, R.string.error_unkonwn, Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (savedInstanceState == null)
            mPersonListViewModel.downloadDataUsingThread();
            //mPersonListViewModel.downloadData();
    }

}