package rs.djokafioka.freja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import rs.djokafioka.freja.adapter.PersonListAdapter;
import rs.djokafioka.freja.model.Person;
import rs.djokafioka.freja.network.response.PersonResponse;
import rs.djokafioka.freja.viewmodel.PersonListViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private PersonListAdapter mAdapter;
    private List<Person> mPersonList;
    private PersonListViewModel mPersonListViewModel;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        mPersonListViewModel.getPersonListObserver().observe(this, new Observer<PersonResponse>() {
            @Override
            public void onChanged(PersonResponse response) {
                mProgressBar.setVisibility(View.GONE);
                if(response != null)
                {
                    if (response.getPersonList() != null)
                    {
                        mPersonList = response.getPersonList();
                        if (mPersonList.size() > 0)
                        {
                            Collections.sort(mPersonList, new Comparator<Person>()
                            {
                                @Override
                                public int compare(Person o1, Person o2)
                                {
                                    return (o1.getLastName().compareTo(o2.getLastName()));
                                }
                            });
                        }
                        mAdapter.updatePersonList(mPersonList);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, R.string.error_unkonwn, Toast.LENGTH_SHORT).show();
                }
            }
        });

//        if (savedInstanceState != null)
//            mPersonListViewModel.loadData();
    }

}