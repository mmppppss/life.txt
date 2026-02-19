package mmppppss.pre;

import android.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class EditDialogFragment extends DialogFragment
 {

    private EditText editText;
    private Button btnCancel;
    private Button btnSave;
    private String text;
    private DBmanager hl;
	private String[] textArray;
	private int pos;
	private ListView list;
    public EditDialogFragment(String text, DBmanager hl, String[] textArray, int pos, ListView list ) {
        this.text = text;
		this.hl = hl;
		this.textArray = textArray;
		this.pos = pos;
		this.list = list;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog, null);
        editText = (EditText) view.findViewById(R.id.edit_text);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnSave = (Button) view.findViewById(R.id.btn_save);

        editText.setText(text);

        btnCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dismiss();
				}
			});

        btnSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String newText = editText.getText().toString();
					((ItemsAdapter) list.getAdapter()).updateText(pos, newText);
					hl.updateDataLife(Integer.parseInt(textArray[0]), textArray[1], textArray[2], newText, textArray[4]);
					dismiss();
				}
			});

        builder.setView(view);
        return builder.create();
    }
}
