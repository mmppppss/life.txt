package mmppppss.pre;

import android.app.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.view.View.*;
import android.content.*;

public class MainActivity extends Activity 
{
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		TextView pre=findViewById(R.id.mainGoPre);
		final Context ct =  this;
		pre.setOnClickListener(kkk(ct));
    }
	public OnClickListener kkk(final Context ct){
		return new OnClickListener(){

			@Override
			public void onClick(View p1)
			{
				Intent preac=new Intent(ct, PreActivity.class);
				startActivity(preac);
			}
			
		};
		
	}
	public void pre(View view){
		
		Intent preac=new Intent(this, PreActivity.class);
		startActivity(preac);
	}
}
