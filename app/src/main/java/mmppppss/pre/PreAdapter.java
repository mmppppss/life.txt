package mmppppss.pre;

import android.content.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;


public class PreAdapter extends ArrayAdapter<String>
{
	String values[];
	ListView list;
	TextView amount;
	Context ct;
	DBmanager hl;
	public PreAdapter(Context context, ArrayList values, DBmanager hl,ListView li){
		super(context, R.layout.row, values);
		String k[]=new String[values.size()];
		int i=0;
		for (String x: values)
		{
			k[i++] = x;
		}
		this.values=k;
		this.list=li;
		ct=context;
		this.hl=hl;
	}
	public void setAmount(TextView am){
		this.amount=am;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater infl = LayoutInflater.from(getContext());
		View view = infl.inflate(R.layout.row, parent, false);
		String text = getItem(position);
		final String[] elements = text.split(";");
		final int ID =Integer.parseInt( elements[0]);
		final TextView am = (TextView) view.findViewById(R.id.rowPrice);
		//status.setId(position);
		TextView date = (TextView) view.findViewById(R.id.rowDate);
		//date.setId(position+142857);
		TextView detail = (TextView) view.findViewById(R.id.rowDetail);
		//content.setId(position+714285);
		am.setText(elements[3]+"BS");
		date.setText(elements[1]);
		detail.setText(elements[2]);
		OnLongClickListener delete= new OnLongClickListener(){
			@Override
			public boolean onLongClick(View p1)
			{
				hl.deletePre(ID);
				list.setAdapter(null);
				PreAdapter ia = new PreAdapter(ct, hl.getData(), hl, list);
				ia.setAmount(amount);
				list.setAdapter(ia);
				amount.setText(hl.getAmount()+"BS");
				return false;
			}
		};
		detail.setOnLongClickListener(delete);
		date.setOnLongClickListener(delete);
		return view;

	}


}
