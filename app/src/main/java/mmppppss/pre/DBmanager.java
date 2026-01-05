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
	//tabla 2
	
	private static final String TABLE_NAME2 = "life";
	private static final String COLUMN_DATA = "data";
	private static final String COLUMN_STATE = "state";
	private static final String COLUMN_TYPE = "type";
	private static final String TABLE_CREATE2 =
	"CREATE TABLE " + TABLE_NAME2 + " (" +
	COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
	COLUMN_STATE + " BIT NULL,"+
	COLUMN_DATA + " TEXT NULL ,"+
	COLUMN_DATE + " TEXT NULL, "+
	COLUMN_TYPE + " TEXT NULL);";
	
	
	Context context;
	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		p1.execSQL(TABLE_CREATE);
		p1.execSQL(TABLE_CREATE2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		p1.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		p1.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
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
	public boolean insertDataL(String data, String date, String type) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_STATE, 0);
		values.put(COLUMN_DATA, data);
		values.put(COLUMN_DATE, date);
		values.put(COLUMN_TYPE, type);
	
		//db.execSQL("INSERT INTO "+TABLE_NAME2+"("+COLUMN_DATA+","+COLUMN_DATE+","+COLUMN_TYPE+") VALUES('"+data+"','"+date+"','"+type+"'");
		long result = db.insert(TABLE_NAME2, null, values);
		if (result == -1) {
			Toast.makeText(this.context, "Error al crear nueva", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	public ArrayList<String> getData() {
		SQLiteDatabase db = this.getReadableDatabase();
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
		while (cursor.moveToNext())
			{
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
	public void deletePre(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME,"id="+id,new String[]{});
	}
	public ArrayList<String[]> getDataLife() {
		SQLiteDatabase db = this.getReadableDatabase();

		// Consulta para la tabla 'life'
		Cursor cursor = db.query(TABLE_NAME2, 
								 new String[]{COLUMN_ID, COLUMN_DATA, COLUMN_DATE, COLUMN_TYPE, COLUMN_STATE},
								 null, null, null, null, "id desc");

		// Lista para almacenar los resultados
		ArrayList<String[]> dataList = new ArrayList<String[]>();

		// Recorrer los resultados del cursor
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));       // Obtener el ID
			String data = cursor.getString(cursor.getColumnIndex(COLUMN_DATA));  // Obtener el 'data'
			String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));  // Obtener la fecha
			String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));  // Obtener el 'type'
			String state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE)); // Obtener el 'state'
			// Agregar el resultado en formato deseado (separado por ';')
			String[] record = new String[]{String.valueOf(id), state,date, data, type};
			// Agregar el arreglo a la lista
			dataList.add(record);
			//dataList.add(id + ";" + data + ";" + date + ";" + type + ";" + state);
		}

		// Cerrar el cursor
		cursor.close();

		// Devolver la lista con los resultados
		return dataList;
	}
	
	public boolean updateDataLife(int id,String state, String date, String data, String type) {
		SQLiteDatabase db = this.getWritableDatabase();

		// Crear un ContentValues para almacenar los nuevos valores
		ContentValues values = new ContentValues();
		values.put(COLUMN_DATA, data);   // Actualizar columna 'data'
		values.put(COLUMN_DATE, date);   // Actualizar columna 'date'
		values.put(COLUMN_TYPE, type);   // Actualizar columna 'type'
		values.put(COLUMN_STATE, state); // Actualizar columna 'state'

		// Realizar la actualización en la base de datos
		int rowsAffected = db.update(TABLE_NAME2, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

		// Si la actualización fue exitosa (al menos una fila afectada), devuelve true
		return rowsAffected > 0;
	}
	public void deleteLife(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME2,"id="+id,new String[]{});
	}
}
