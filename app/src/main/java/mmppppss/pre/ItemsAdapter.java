package mmppppss.pre;

import android.content.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;

class ItemsAdapter extends ArrayAdapter<String[]> {
    private ArrayList<String[]> values;
    private ListView list;
    private Context ct;
    private DBmanager hl;

    public ItemsAdapter(Context context, ArrayList<String[]> values, DBmanager hl, ListView li) {
        super(context, R.layout.item, values);
        this.values = values;
        this.list = li;
        this.ct = context;
        this.hl = hl;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater infl = LayoutInflater.from(getContext());
        View view = infl.inflate(R.layout.item, parent, false);
        String[] text = getItem(position);

        // Inicializar vistas
        TextView status = (TextView) view.findViewById(R.id.itemstatus);
        TextView date = (TextView) view.findViewById(R.id.itemdate);
        TextView content = (TextView) view.findViewById(R.id.itemContent);

        // Configurar vistas
        configureViews(status, date, content, text);

        // Configurar listener de eventos
        configureListeners(status, content, date, text);

        return view;
    }

    private void configureViews(TextView status, TextView date, TextView content, String[] text) {
        String st = "[" + (text[1].equals("0") ? "✘" : "✓") + "] (" + text[4] + ")";
        status.setText(st);
        date.setText(text[2]);
        content.setText(text[3]);
    }

    private void configureListeners(TextView status, TextView content, TextView date, String[] text) {
        status.setOnClickListener(new StatusClickListener(status, text));
        status.setOnLongClickListener(new DeleteClickListener(text[0]));
        content.setOnClickListener(new EditClickListener());
        content.setOnFocusChangeListener(new UpdateFocusListener(text));
        date.setOnLongClickListener(new DeleteClickListener(text[0]));
    }

    private class StatusClickListener implements OnClickListener {
        private TextView status;
        private String[] text;

        public StatusClickListener(TextView status, String[] text) {
            this.status = status;
            this.text = text;
        }

        @Override
        public void onClick(View p1) {
            boolean newState = status.getText().toString().contains("✘");
            String oldStatus = status.getText().toString().contains("✘") ? "✘" : "✓";
            String newStatus = status.getText().toString().contains("✘") ? "✓" : "✘";
            String st = status.getText().toString().replace(oldStatus, newStatus);
            status.setText(st);
            String toDb = newState ? "1" : "0";
            hl.updateDataLife(Integer.parseInt(text[0]), toDb, text[2], text[3], text[4]);
        }
    }

    private class DeleteClickListener implements OnLongClickListener {
		private  String tv;
		public DeleteClickListener(String tv){
			this.tv =tv;
		}
		
        @Override
        public boolean onLongClick(View p1) {
            hl.deleteLife(Integer.parseInt(tv));
            list.setAdapter(null);
            ItemsAdapter ia = new ItemsAdapter(ct, hl.getDataLife(), hl, list);
            list.setAdapter(ia);
			return true;
        }
    }

    private class EditClickListener implements OnClickListener {
        @Override
        public void onClick(View p1) {
            TextView upd = (TextView) p1;
            upd.setOnClickListener(null);
            upd.setInputType(1);
            upd.setClickable(false);
        }
    }

    private class UpdateFocusListener implements OnFocusChangeListener {
        private String[] text;

        public UpdateFocusListener(String[] text) {
            this.text = text;
        }

        @Override
        public void onFocusChange(View p1, boolean p2) {
            TextView upd = (TextView) p1;
            upd.setClickable(false);
            String textN = upd.getText().toString();
            hl.updateDataLife(Integer.parseInt(text[0]), text[1], text[2], textN, text[4]);
        }
    }
}
