package examples.aaronhoskins.com.networkandrxjavademo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import examples.aaronhoskins.com.networkandrxjavademo.model.randomuser.Result;

public class RandomUserRvAdapter extends RecyclerView.Adapter<RandomUserRvAdapter.ViewHolder>{


    List<Result> resultList;

    public RandomUserRvAdapter(List<Result> resultList) {
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.random_user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result currentResult = resultList.get(position);
        final String name = String.format("%s %s",
                currentResult.getName().getFirst(), currentResult.getName().getLast());

        holder.tvEmail.setText(currentResult.getEmail());
        holder.tvName.setText(name);
        //Populate Image View Using Glide
        Glide
                .with(holder.itemView)
                .load(currentResult.getPicture().getMedium())
                .into(holder.imgUserImage);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvEmail;
        TextView tvName;
        ImageView imgUserImage;
        public ViewHolder(View itemView) {
            super(itemView);

            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvName = itemView.findViewById(R.id.tvName);
            imgUserImage = itemView.findViewById(R.id.imgUserPhoto);
        }
    }
}
