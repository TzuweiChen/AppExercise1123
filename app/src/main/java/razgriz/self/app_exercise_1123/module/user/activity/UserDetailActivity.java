package razgriz.self.app_exercise_1123.module.user.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import razgriz.self.app_exercise_1123.R;
import razgriz.self.app_exercise_1123.module.user.fragment.UserDetailFragment;

public class UserDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_fragment_container);

        Fragment fragment = UserDetailFragment.newInstance();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getName()).commit();
    }
}
