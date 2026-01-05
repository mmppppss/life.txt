package mmppppss.pre;
import android.content.*;
import android.widget.*;
import java.util.*;
import android.view.*;

public class ItemAdapter extends ArrayAdapter<String[]>
{
	Context ct;
	ListView li;
	ArrayList<String[]> values;
	public ItemAdapter(Context context, ArrayList<String[]> values, ListView li){
		super(context, R.layout.item,values);
		this.li = li;
		ct = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater infl = LayoutInflater.from(getContext());
		View view = infl.inflate(R.layout.item, parent, false);
		
		String[] text = getItem(position);
		TextView status = (TextView) view.findViewById(R.id.itemstatus);
		TextView date = (TextView) view.findViewById(R.id.itemdate);
		TextView content = (TextView) view.findViewById(R.id.itemContent);
		status.setText(text[1]);
		date.setText(text[2]);
		content.setText(text[3]);
		
		//return super.getView(position, convertView, parent);
		return view;
	}
	
}
