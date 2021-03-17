package cat.itb.clonreddit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

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
                .inflate(R.layout.subreddit_item, parent, false);

        return new SubredditViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull SubredditViewHolder holder, int position, @NonNull SubReddit model) {
        holder.bind(model);
    }


    class SubredditViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final ShapeableImageView imageViewSubreddit;


        public SubredditViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.imageViewSubredditPost);
            imageViewSubreddit = itemView.findViewById(R.id.textViewNameSubreddit);
        }

        private void bind(SubReddit subReddit) {
            textViewName.setText(subReddit.getTitle());
            Picasso.with(context).load(subReddit.getImgUrl()).into(imageViewSubreddit);
        }
    }
}
