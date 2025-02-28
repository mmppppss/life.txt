package mmppppss.pre;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.*;
import android.widget.*;
import java.util.*;

public class DBmanager extends android.database.sqlite.SQLiteOpenHelper
 {
	private static final String DATABASE_NAME = "pre.txt.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME = "presupuesto";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_DETAIL = "detail";
	private static final String COLUMN_DATE = "date";
	private static final String COLUMN_SUMA = "amount";
	private static final String TABLE_CREATE =
	"CREATE TABLE " + TABLE_NAME + " (" +
	COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	COLUMN_DETAIL + " TEXT NOT NULL, "+
	COLUMN_DATE + " DATE NOT NULL, "+
	COLUMN_SUMA + " MONEY NOT NULL);";
	
	Context context;
	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		p1.execSQL(TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		p1.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(p1);
	}
	
	public DBmanager(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context=context;
	}
	
	public boolean insertData(String detail, String date, String amount) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_DETAIL, detail);
		values.put(COLUMN_SUMA, amount);
		values.put(COLUMN_DATE, date);

		long result = db.insert(TABLE_NAME, null, values);
		if (result == -1) {
			Toast.makeText(this.context, "Error al crear nueva", Toast.LENGTH_SHORT).show();
			//return false;
		}
		return true;
	}
	
	public ArrayList<String> getData() {
		SQLiteDatabase db = this.getReadableDatabase();
		
		//db.delete(TABLE_NAME,null,null);
		Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_DATE ,COLUMN_DETAIL, COLUMN_SUMA},
								 null, null, null, null, "id desc");
		ArrayList<String> a = new ArrayList<String>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
			String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
			String detail = cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL));
			Double amount = cursor.getDouble(cursor.getColumnIndex(COLUMN_SUMA));
			
			a.add(id+";"+date+";"+detail+";"+amount);
		}

		cursor.close();
		return a;
	}
	
	public String getAmount(){

		SQLiteDatabase db = this.getReadableDatabase();

		//db.delete(TABLE_NAME,null,null);
		Cursor cursor = db.query(TABLE_NAME, new String[]{"sum("+COLUMN_SUMA+")"},
								 null, null, null, null, "id desc");
						
		Double amount = null;
		while (cursor.moveToNext()) {
			 amount = cursor.getDouble(0);
		}

		cursor.close();
		return amount.toString();
	}
	

	// Método para actualizar datos
	public void updateData(int id, String newData) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(COLUMN_DETAIL, newData);

		db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
		
	}
	public void deleteAll(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, "",new String[]{});
	}
	public void delete(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME,"id="+id,new String[]{});
	}
}
