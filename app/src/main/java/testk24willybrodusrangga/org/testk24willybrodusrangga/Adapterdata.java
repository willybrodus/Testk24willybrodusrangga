package testk24willybrodusrangga.org.testk24willybrodusrangga;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by WR on 2/11/2017.
 */

public class Adapterdata extends RecyclerView.Adapter<Adapterdata.InboxHolder> {

    public Context context;
    private ArrayList<dataCLASS> mDataset;
    private int lastCheckedPosition = -1;

    public Adapterdata(ArrayList<dataCLASS> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public InboxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_listview, parent, false);
        // set the view's size, margins, paddings and layout parameters


        InboxHolder vh = new InboxHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(InboxHolder holder, final int position) {
        final dataCLASS item = mDataset.get(position);

        holder.mNama.setText(item.getNama());
        holder.mID.setText(item.getID());
        holder.mAsal.setText(item.getAsal());
        holder.mJoint.setText(item.getJoin());
        //holder.imageView.getLayoutParams().height = 200;

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class InboxHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
//            private SparseBooleanArray selectedItems = new SparseBooleanArray();

        public TextView mNama;
        public TextView mID;
        public TextView mAsal;
        public TextView mJoint;
        public ImageView imageView;
        public Context context;


        public InboxHolder(View v) {
            super(v);
            context = v.getContext();

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mNama = (TextView) v.findViewById(R.id.tv_textNama);
            mID = (TextView) v.findViewById(R.id.tv_id);
            mAsal = (TextView) v.findViewById(R.id.tv_alamat);
            mJoint = (TextView) v.findViewById(R.id.tv_joint);
            imageView = (ImageView) v.findViewById(R.id.iv_image);


        }
    }
}
