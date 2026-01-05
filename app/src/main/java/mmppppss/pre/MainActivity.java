package mmppppss.pre;

import android.app.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.view.View.*;
import android.content.*;

public class MainActivity extends Activity {
    private TextView twnewline, twdatenew;
    private ListView list;
    private Spinner cate;
    private DBmanager hl;
    private ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Inicializar vistas
        initViews();

        // Configurar adaptador de spinner
        configureSpinnerAdapter();

        // Configurar listener de eventos
        configureListeners();

        // Cargar datos
        loadData();
    }

    private void initViews() {
        twnewline = (TextView) findViewById(R.id.contentNew);
        twdatenew = (TextView) findViewById(R.id.dateNew);
        list = (ListView) findViewById(R.id.list);
        cate = (Spinner) findViewById(R.id.spinCate);
        hl = new DBmanager(this);
        twdatenew.setHint(getDate());
    }

    private void configureSpinnerAdapter() {
        String[] categories = {"Tarea", "Evento", "Cita", "Meta"};
        ArrayAdapter<String> cates = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        cate.setAdapter(cates);
    }

    private void configureListeners() {
        findViewById(R.id.mainGoPre).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(MainActivity.this, PreActivity.class));
				}
			});
    }

    private void loadData() {
        adapter = new ItemsAdapter(this, hl.getDataLife(), hl, list);
        list.setAdapter(adapter);
    }

    public void add(View view) {
        if (!twnewline.getText().toString().equals("")) {
            String type = getType();
            String date = twdatenew.getText().toString().equals("") ? getDate() : twdatenew.getText().toString() + " " + getDate();
            String data = twnewline.getText().toString();
            hl.insertDataL(data, date, type);
            loadData();
            twnewline.setText("");
        } else {
            toast("Task vacia");
        }
    }

    private String getType() {
        switch (cate.getSelectedItemPosition()) {
            case 0:
                return "T";
            case 1:
                return "E";
            case 2:
                return "C";
			case 3:
				return "M";
            default:
                return "N";
        }
    }

    public String getDate() {
        Date k = new Date();
        String[] months = {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};
        String dateof = months[k.getMonth()] + " " + (k.getDate() < 10 ? "0" + k.getDate() : k.getDate());
        return dateof;
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
