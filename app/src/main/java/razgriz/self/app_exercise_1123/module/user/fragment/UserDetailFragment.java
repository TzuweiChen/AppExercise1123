package razgriz.self.app_exercise_1123.module.user.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import razgriz.self.app_exercise_1123.R;
import razgriz.self.app_exercise_1123.bean.User;
import razgriz.self.app_exercise_1123.constant.ExtraKey;

public class UserDetailFragment extends Fragment {

    public static UserDetailFragment newInstance() {
        return new UserDetailFragment();
    }

    private View imgClose;
    private ImageView imgAvatar;
    private TextView txtName;
    private TextView txtBio;
    private TextView txtLogin;
    private View txtStaff;
    private TextView txtLocation;
    private TextView txtLink;

    private User user;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() == null) {
            return;
        }

        user = (User) getArguments().getSerializable(ExtraKey.USER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        imgClose = view.findViewById(R.id.imgClose);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        txtName = view.findViewById(R.id.txtName);
        txtBio = view.findViewById(R.id.txtBio);
        txtLogin = view.findViewById(R.id.txtLogin);
        txtStaff = view.findViewById(R.id.txtStaff);
        txtLocation = view.findViewById(R.id.txtLocation);
        txtLink = view.findViewById(R.id.txtLink);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgClose.setOnClickListener(view1 -> requireActivity().finish());
        Glide.with(this)
                .load(user.getAvatarUrl())
                .circleCrop()
                .into(imgAvatar);
        txtName.setText(user.getName());
        txtBio.setText(user.getBio());
        txtLogin.setText(user.getLogin());
        txtStaff.setVisibility(user.isSiteAdmin() ? View.VISIBLE : View.GONE);
        txtLocation.setText(user.getLocation());
        txtLink.setText(user.getBlog());
    }
}
