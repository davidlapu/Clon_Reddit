package cat.itb.clonreddit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.models.SubReddit;

public class SubredditAdapter extends FirebaseRecyclerAdapter<SubReddit, SubredditAdapter.SubredditViewHolder> {
    private final Context context;


    public SubredditAdapter(@NonNull FirebaseRecyclerOptions<SubReddit> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public SubredditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_bottom_sheet, parent, false); // TODO: cambiar id: fragment_sub_reddit_list

        return new SubredditViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull SubredditViewHolder holder, int position, @NonNull SubReddit model) {
        holder.bind(model);
    }


    class SubredditViewHolder extends RecyclerView.ViewHolder {


        public SubredditViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private void bind(SubReddit subReddit) {

        }
    }
}
