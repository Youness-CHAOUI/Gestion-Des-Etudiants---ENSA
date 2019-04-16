package chaoui.cy_15.www.gestiondabsencecy_15;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import chaoui.cy_15.www.gestiondabsencecy_15.classes.Adapters.*;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Database.myDBHelper;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Dialog.Popup;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.*;

public class seancesActivity extends AppCompatActivity
{
    myDBHelper database;
    View view, view2;
    DatePicker dp_date;
    TimePicker tp_time;
    RecyclerView rv_seances;
    rv_seancesAdapter seancesAdapter;
    AlertDialog popupAddSeance,popupAddSeance2;
    Button btnSuivant;
    Button btnTerminer;
    FloatingActionButton fab;
    TextView tv_empty;

    int ID_CLASSE, ID_MATIERE;
    String DATE, HEURE;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seances);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        database = new myDBHelper(this);
        ID_CLASSE = getIntent().getExtras().getInt("idClasse");
        ID_MATIERE = getIntent().getExtras().getInt("idMatiere");
        //Toast.makeText(this, "La matière N° "+ID_MATIERE, Toast.LENGTH_LONG).show();

        tv_empty = (TextView) findViewById(R.id.tv_empty);
        rv_seances = (RecyclerView) findViewById(R.id.rv_seances);
        rv_seances.setLayoutManager(new LinearLayoutManager(this, 1, false));
        seancesAdapter = new rv_seancesAdapter(ID_CLASSE, ID_MATIERE, database.getAllSeances(ID_CLASSE, ID_MATIERE));
        rv_seances.setAdapter(seancesAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab4);
        view = LayoutInflater.from(this).inflate(R.layout.popup_add_seance1, null);
        view2 = LayoutInflater.from(this).inflate(R.layout.popup_add_seance2, null);
        popupAddSeance = Popup.createPopup(this, view, true);
        popupAddSeance2 = Popup.createPopup(this, view2, true);
        btnSuivant = (Button) view.findViewById(R.id.btnSuivant);
        btnTerminer = (Button) view2.findViewById(R.id.btnTerminer);
        dp_date = (DatePicker) view.findViewById(R.id.datePicker);
        tp_time = (TimePicker) view2.findViewById(R.id.timePicker);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //Toast.makeText(getApplicationContext(), "CLASSE: "+ID_CLASSE+", MAT: "+ID_MATIERE, Toast.LENGTH_LONG).show();

        if(seancesAdapter.getItemCount()==0)
            tv_empty.setVisibility(View.VISIBLE);
        else
            tv_empty.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupAddSeance.show();
            }
        });

        btnSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DATE = dp_date.getDayOfMonth()+"/"+(dp_date.getMonth()+1)+"/"+dp_date.getYear();
                popupAddSeance.dismiss();
                popupAddSeance2.show();
            }
        });

        btnTerminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String strH, strM;
                int h = tp_time.getCurrentHour(), m = tp_time.getCurrentMinute();

                if(h>=0 && h<=9) strH="0"+h;
                else strH=h+"";
                if(m>=0 && m<=9) strM="0"+m;
                else strM=m+"";

                HEURE = strH+":"+strM;
                popupAddSeance2.dismiss();

                Long insertedId = database.ajouterSeance(new ListeAbsence(0, DATE, HEURE, ID_MATIERE, ID_CLASSE));
                if (insertedId == -1)
                    Toast.makeText(getApplicationContext(), "Une erreur dans l'insertion !", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "CLIQUER sur la nouvelle séance pour noter l'absence.", Toast.LENGTH_LONG).show();

                if(seancesAdapter.getItemCount()==0)
                    tv_empty.setVisibility(View.VISIBLE);
                else
                    tv_empty.setVisibility(View.INVISIBLE);
                seancesAdapter.setListeSeances(database.getAllSeances(ID_CLASSE, ID_MATIERE));
                seancesAdapter.notifyDataSetChanged();
            }
        });
    }

    /** ActionBar Menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_0, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.quitter:
                finishAffinity();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
