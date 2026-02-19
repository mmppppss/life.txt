package mmppppss.pre;

import android.app.*;
import android.content.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;

class ItemsAdapter extends ArrayAdapter<String[]> {
    private ArrayList<String[]> values;
    private ListView list;
    private Context ct;
    private DBmanager hl;
	private Activity ac;

    public ItemsAdapter(Context context, Activity ac, ArrayList<String[]> values, DBmanager hl, ListView li) {
        super(context, R.layout.item, values);
        this.values = values;
        this.list = li;
        this.ct = context;
        this.hl = hl;
		this.ac = ac;
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
        content.setOnClickListener(new EditClickListener(text, content));
        date.setOnLongClickListener(new DeleteClickListener(text[0]));
    }
	public void updateText(int position, String newText) {
		values.get(position)[3] = newText;
		notifyDataSetChanged();
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
            ItemsAdapter ia = new ItemsAdapter(ct, ac, hl.getDataLife(), hl, list);
            list.setAdapter(ia);
			return true;
        }
    }

    private class EditClickListener implements OnClickListener {
		private String[] text;
		private TextView tv;
		public EditClickListener(String[] text, TextView tv) {
			this.text = text;
			this.tv = tv;
		}
        @Override
        public void onClick(View p1) {
            TextView upd = (TextView) p1;
			int position = list.getPositionForView(p1);
			EditDialogFragment edf = new EditDialogFragment(upd.getText().toString(), hl, text, position, list);
			edf.show(ac.getFragmentManager(), "editdialog");
	
        }
    }
}
