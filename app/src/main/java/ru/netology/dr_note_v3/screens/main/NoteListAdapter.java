package ru.netology.dr_note_v3.screens.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import ru.netology.dr_note_v3.R;
import ru.netology.dr_note_v3.model.Note;
import ru.netology.dr_note_v3.utils.Constants;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListHolder> {

    private SortedList<Note> sortedList;

    public NoteListAdapter() {
        sortedList = new SortedList<>(Note.class, new SortedList.Callback<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return (int) (o2.timestamp - o1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Note item1, Note item2) {
                return item1.id == item2.id;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public NoteListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListHolder holder, int position) {
        holder.bind(sortedList.get(position));
        holder.updateStrokeOut(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void setItems(List<Note> notes) {
        sortedList.replaceAll(notes);
    }

    static class NoteListHolder extends RecyclerView.ViewHolder {
        TextView noteName, noteText, deadLine;
        Note note;

        public NoteListHolder(@NonNull final View itemView) {
            super(itemView);

            noteName = itemView.findViewById(R.id.item_note_name);
            noteText = itemView.findViewById(R.id.item_note_text);
            deadLine = itemView.findViewById(R.id.item_note_timestamp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.KEY_EXTRA_NOTE, note);
                    Constants.APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_editNoteFragment, bundle);
                }
            });
        }

        public void bind(Note note) {
            this.note = note;
            noteName.setText(note.name);
            noteText.setText(note.text);
            if (note.hasDeadline) {
                SimpleDateFormat formating = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss:SSS", Locale.getDefault());
                deadLine.setText(formating.format(note.timestamp));
            } else {
                deadLine.setVisibility(View.GONE);
            }
        }

        public void updateStrokeOut(Note note) {
            Calendar calendar = Calendar.getInstance();
            long l = calendar.getTimeInMillis();
            if (note.hasDeadline && calendar.getTimeInMillis() - note.timestamp > Constants.MILLS_IN_DAY) {
                //окрасить в крассный цвет
                noteName.setTextColor(noteName.getResources().getColor(R.color.colorRed));
                noteText.setTextColor(noteText.getResources().getColor(R.color.colorRed));
                deadLine.setTextColor(noteText.getResources().getColor(R.color.colorRed));
            } else if (note.hasDeadline && calendar.getTimeInMillis() - note.timestamp >= 0 &&
                    calendar.getTimeInMillis() - note.timestamp < Constants.MILLS_IN_DAY) {
                //окрасить в желтый цвет
                noteName.setTextColor(noteName.getResources().getColor(R.color.colorYellow));
                noteText.setTextColor(noteText.getResources().getColor(R.color.colorYellow));
                deadLine.setTextColor(noteText.getResources().getColor(R.color.colorYellow));
            } else {
                noteName.setTextColor(noteName.getResources().getColor(R.color.colorPrimaryLight));
                noteText.setTextColor(noteText.getResources().getColor(R.color.colorPrimaryLight));
                deadLine.setTextColor(noteText.getResources().getColor(R.color.colorPrimaryLight));
            }
        }
    }
}
