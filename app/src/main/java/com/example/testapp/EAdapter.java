package com.example.testapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EAdapter extends FirestoreRecyclerAdapter<Set_item,EAdapter.EViewHolder> {


    public EAdapter(@NonNull FirestoreRecyclerOptions<Set_item> options) {
        super(options);
    }

    class EViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView initial;
        private TextView text;
        private EditText quantityedit;
        private TextView quantity;
        private Button delete;
        private Button update;
        private Button share;
        public EViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.fur);
            text = itemView.findViewById(R.id.furn);
            quantity = itemView.findViewById(R.id.qtyfur);
            initial = itemView.findViewById(R.id.initqtyfur);
            delete = itemView.findViewById(R.id.remfur);
            quantityedit = itemView.findViewById(R.id.qtedfur);
            update = itemView.findViewById(R.id.updtfur);
            share = itemView.findViewById(R.id.shrfur);
            delete = itemView.findViewById(R.id.remfur);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteitem(getAdapterPosition());
                }
            });
        }
    }

    @Override
    public EViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new EViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull EViewHolder holder, int position, @NonNull Set_item model) {
        holder.text.setText(model.getTitle());
        holder.quantity.setText(model.getQty());
        holder.initial.setText(model.getInitqty());
    }

    public void deleteitem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

}



