package mmppppss.pre;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.text.*;
import java.util.*;

public class PreActivity extends Activity
{
	ListView lvpre;
	DBmanager db ;
	EditText date, detail, amount;
	Button bt, btlife;
	TextView mainAmount;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpre);
		lvpre = findViewById(R.id.lvpre);
		db = new DBmanager(this);
		date = findViewById(R.id.maindate);
		detail = findViewById(R.id.maindetalle);
		amount = findViewById(R.id.mainamount);
		mainAmount = findViewById(R.id.maintotal);
		date.setText(getDate());
		/*btlife=findViewById(R.id.preGoMain);
		btlife.setOnClickListener(kkk(this));*/
		
		refresh();
		
	}
	public void clickAdd(View view){
		String x = detail.getText().toString();
		String y = date.getText().toString();
		String z = amount.getText().toString();
		db.insertData(x, y, z);
		refresh();
		
	}
	public void refresh(){
		lvpre.setAdapter(null);
		PreAdapter aaa = new PreAdapter(this, db.getData(), db, lvpre);
		aaa.setAmount(mainAmount);
		lvpre.setAdapter(aaa);
		String csnt = db.getAmount();
		mainAmount.setText(csnt + "BS");
	}
	public String getDate(){
		Date k=new Date();
		SimpleDateFormat dt = new SimpleDateFormat("yy-MM-dd");
		return dt.format(k);
	}
	public OnClickListener kkk2(final Context ct){
		return new OnClickListener(){

			@Override
			public void onClick(View p1)
			{
				Intent preac=new Intent(ct, MainActivity.class);
				startActivity(preac);
			}

		};

	}
}
