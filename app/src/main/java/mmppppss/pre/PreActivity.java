package mmppppss.pre;
import android.app.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;

public class PreActivity extends Activity
{
	ListView lvpre;
	DBmanager db ;
	EditText date, detail, amount;
	Button bt;
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
		PreAdapter aaa = new PreAdapter(this, db.getData(), lvpre);
		lvpre.setAdapter(aaa);
		String csnt = db.getAmount();
		mainAmount.setText(csnt + "BS");
	}
}
