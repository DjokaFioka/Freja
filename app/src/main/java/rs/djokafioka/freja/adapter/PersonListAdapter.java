package rs.djokafioka.freja.adapter;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import rs.djokafioka.freja.R;
import rs.djokafioka.freja.model.Person;

/**
 * Created by Djordje on 18.2.2022..
 */
public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.PersonListViewHolder> {
    private static final String TAG = "PersonListAdapter";
    private List<Person> mPersonList;

    public PersonListAdapter(List<Person> personList) {
        mPersonList = personList;
    }

    @NonNull
    @Override
    public PersonListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_person, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonListViewHolder holder, int position) {
        final Person person = mPersonList.get(position);

        holder.loadPhoto(person.getBase64Photo());
        holder.setName(person.getName());
    }

    @Override
    public int getItemCount() {
        if (mPersonList != null)
            return mPersonList.size();
        return 0;
    }

    public void updatePersonList(List<Person> personList) {
        mPersonList = personList;
        notifyDataSetChanged();
    }

    static final class PersonListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImgPhoto;
        private final TextView mTxtName;

        public PersonListViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgPhoto = itemView.findViewById(R.id.imgPhoto);
            mTxtName = itemView.findViewById(R.id.txtName);
        }

        void loadPhoto(String base64photo) {
            byte[] imageByteArray = Base64.decode(base64photo, Base64.DEFAULT);
            Glide.with(mImgPhoto.getContext())
                    .load(imageByteArray)
                    //.apply(RequestOptions.fitCenterTransform())
                    .into(mImgPhoto);
        }
        void setName(String name) {
            mTxtName.setText(name);
        }
    }
}
