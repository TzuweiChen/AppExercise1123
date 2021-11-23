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
import razgriz.self.app_exercise_1123.module.user.model.GetUsersModel;

public class UserListFragment extends Fragment implements UserAdapter.Callback {

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private GetUsersModel getUsersModel;

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
        getUsersModel.getError().observe(getViewLifecycleOwner(), error -> Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show());
        getUsersModel.getUser().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    @Override
    public void onUserClicked(User user) {
        Toast.makeText(requireContext(), user.getName() + " clicked.\nIntent to user profile for Phase 2", Toast.LENGTH_SHORT).show();
    }
}
