package razgriz.self.app_exercise_1123.module.user.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import razgriz.self.app_exercise_1123.R;
import razgriz.self.app_exercise_1123.bean.User;
import razgriz.self.app_exercise_1123.module.user.adapter.UserAdapter;
import razgriz.self.app_exercise_1123.module.user.model.GetUserModel;
import razgriz.self.app_exercise_1123.module.user.model.GetUsersModel;

public class UserListFragment extends Fragment implements UserAdapter.Callback {

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private View progressBar;

    private GetUsersModel getUsersModel;
    private GetUserModel getUserModel;


    private UserAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new UserAdapter(requireContext(), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        progressBar = view.findViewById(R.id.progressBar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            PagedList pagedList = adapter.getCurrentList();
            if (pagedList != null) {
                pagedList.getDataSource().invalidate();
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView.setAdapter(adapter);

        getUsersModel = new ViewModelProvider(this).get(GetUsersModel.class);
        getUsersModel.getProgress().observe(getViewLifecycleOwner(), isProgress -> swipeRefreshLayout.setRefreshing(isProgress));
        getUsersModel.getError().observe(getViewLifecycleOwner(), error -> Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show());
        getUsersModel.getUser().observe(getViewLifecycleOwner(), adapter::submitList);

        getUserModel = new ViewModelProvider(this).get(GetUserModel.class);
        getUserModel.getProgress().observe(getViewLifecycleOwner(), isProgress -> progressBar.setVisibility(isProgress ? View.VISIBLE : View.GONE));
        getUserModel.getError().observe(getViewLifecycleOwner(), error -> Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show());
        getUserModel.getUser().observe(getViewLifecycleOwner(), user -> {
            // todo intent to user detail page
            Toast.makeText(requireContext(), user.getName(), Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public void onUserClicked(User user) {
        if (getUserModel.getProgress().getValue() != null && getUserModel.getProgress().getValue()) {
            Toast.makeText(requireContext(), "Last user fetching, please try again later.", Toast.LENGTH_SHORT).show();
        } else {
            getUserModel.doGetUser(user.getLogin());
        }
    }
}
