package cat.itb.clonreddit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import cat.itb.clonreddit.R;
import cat.itb.clonreddit.fragments.SubRedditListFragmentDirections;
import cat.itb.clonreddit.models.SubReddit;

public class SubredditAdapter extends FirebaseRecyclerAdapter<SubReddit, SubredditAdapter.SubredditViewHolder> {
    private final Context context;
    private final String dest;


    public SubredditAdapter(@NonNull FirebaseRecyclerOptions<SubReddit> options, Context context, String dest) {
        super(options);
        this.context = context;
        this.dest = dest;
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
            textViewName = itemView.findViewById(R.id.textViewNameSubreddit);
            imageViewSubreddit = itemView.findViewById(R.id.imageViewSubredditPost);


            if (dest.equals("img")) itemView.setOnClickListener(this::toImg);
            else if (dest.equals("txt")) itemView.setOnClickListener(this::toTxt);
        }

        private void toTxt(View view) {
            SubRedditListFragmentDirections.ActionSubRedditListFragmentToTextPostFragment action =
            SubRedditListFragmentDirections.actionSubRedditListFragmentToTextPostFragment(getItem(getLayoutPosition()));

            Navigation.findNavController(view).navigate(action);
        }

        private void toImg(View view) {
            SubRedditListFragmentDirections.ActionSubRedditListFragmentToImagePostFragment action =
                    SubRedditListFragmentDirections.actionSubRedditListFragmentToImagePostFragment(getItem(getLayoutPosition()));

            Navigation.findNavController(view).navigate(action);
        }

        private void bind(SubReddit subReddit) {
            textViewName.setText(subReddit.getTitle());
            Picasso.with(context).load(subReddit.getImgUrl()).into(imageViewSubreddit);
        }
    }
}
