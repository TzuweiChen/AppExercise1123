package razgriz.self.app_exercise_1123.module.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import razgriz.self.app_exercise_1123.R;
import razgriz.self.app_exercise_1123.bean.User;

public class UserAdapter extends PagedListAdapter<User, UserAdapter.ViewHolder> {

    public interface Callback {
        void onUserClicked(User user);
    }

    private Context context;
    private Callback callback;

    public UserAdapter(Context context, Callback callback) {
        super(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getId() == newItem.getId();
            }
        });

        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = getItem(position);

        if (user == null) {
            return;
        }

        Glide.with(context)
                .load(user.getAvatarUrl())
                .circleCrop()
                .into(holder.imgAvatar);

        holder.txtLogin.setText(user.getLogin());
        holder.txtStaff.setVisibility(user.isSiteAdmin() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            if (callback != null) {
                callback.onUserClicked(user);
            }
        });
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        ImageView imgAvatar;
        TextView txtLogin;
        View txtStaff;

        public ViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtLogin = itemView.findViewById(R.id.txtLogin);
            txtStaff = itemView.findViewById(R.id.txtStaff);
        }
    }
}
