package test.rvp.happtask;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CoverAdapter extends RecyclerView.Adapter<CoverAdapter.CoverModelViewHolder> {

    private static final int CLICK = 0;

    final private CoverAdapter.ListItemClickListener mOnClickListener;
    private Context context;
    private ArrayList<CoverModel> mArrayList;

    public CoverAdapter(MainActivity context, ArrayList<CoverModel> arrayList, ListItemClickListener listener) {
        mOnClickListener = listener;
        this.context = context;
        this.mArrayList = arrayList;

    }

    private Context getContext() {
        return context;
    }

    @Override
    public CoverAdapter.CoverModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.story_card_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieView = inflater.inflate(layoutIdForListItem, parent, false);
        CoverAdapter.CoverModelViewHolder viewHolder = new CoverAdapter.CoverModelViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CoverAdapter.CoverModelViewHolder holder, int position) {

        holder.coverText.setText(mArrayList.get(position).getCovertitle());
        Picasso.get().load(mArrayList.get(position).getCoverPath()).into(holder.coverImg);

    Log.e("Image","Path is "+mArrayList.get(position).getCoverPath());

        //*****************
    }

    @Override
    public int getItemCount() {

        return mArrayList.size();
    }

    public interface ListItemClickListener {

        void onListItemClick(int clickedItemIndex, int whichClick);

    }

    public class CoverModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView coverText;
        public CircleImageView coverImg;

        public CoverModelViewHolder(View view) {
            super(view);

            coverImg =  view.findViewById(R.id.img_story_cover);
            coverText =  view.findViewById(R.id.tv_cover);
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition, CLICK);


        }


    }


}
