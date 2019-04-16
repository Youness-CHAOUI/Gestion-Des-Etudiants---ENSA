package chaoui.cy_15.www.gestiondabsencecy_15;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import chaoui.cy_15.www.gestiondabsencecy_15.classes.Adapters.*;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Database.myDBHelper;
import chaoui.cy_15.www.gestiondabsencecy_15.*;

public class listeAbsenceActivity extends AppCompatActivity
{
    RecyclerView rv_listeAbsence;
    rv_listeAbsenceAdapter listeAbsenceAdapter;
    myDBHelper database;
    int ID_SEANCE, ID_CLASSE, ID_MATIERE;
    Button btn_valider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_absence);

        database = new myDBHelper(this);
        ID_SEANCE = getIntent().getExtras().getInt("idSeance");
        ID_CLASSE = getIntent().getExtras().getInt("idClasse");
        ID_MATIERE = getIntent().getExtras().getInt("idMatiere");

        btn_valider = (Button) findViewById(R.id.btn_valider);
        rv_listeAbsence = (RecyclerView) findViewById(R.id.rv_listeAbsence);
        rv_listeAbsence.setLayoutManager(new LinearLayoutManager(this, 1, false));
        listeAbsenceAdapter = new rv_listeAbsenceAdapter(database, ID_SEANCE, ID_CLASSE);
        rv_listeAbsence.setAdapter(listeAbsenceAdapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //Toast.makeText(getApplicationContext(), "S'il vous plait marquer pour chaque étudiant(e) s'il est présent(e) ou pas.",Toast.LENGTH_LONG).show();
        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), seancesActivity.class);
                i.putExtra("idClasse", ID_CLASSE);
                i.putExtra("idMatiere", ID_MATIERE);
                finish();
                startActivity(i);
            }
        });
    }
}
