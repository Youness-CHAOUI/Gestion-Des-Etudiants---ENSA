package chaoui.cy_15.www.gestiondabsencecy_15;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import chaoui.cy_15.www.gestiondabsencecy_15.classes.Adapters.*;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Database.myDBHelper;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Dialog.Popup;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.*;

public class matieresActivity extends AppCompatActivity
{
    myDBHelper database;
    RecyclerView rv_matieres;
    rv_matieresAdapter matieresAdapter;
    AlertDialog popupAddMatiere;
    Button btnAjouterMatiere;
    FloatingActionButton fab;
    EditText et_lib, et_nbH;
    TextView tv_empty;

    int ID_CLASSE;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matieres);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        database = new myDBHelper(this);
        ID_CLASSE = getIntent().getExtras().getInt("idClasse");
        Toast.makeText(this, "La classe N° "+ID_CLASSE, Toast.LENGTH_LONG).show();

        tv_empty = (TextView) findViewById(R.id.tv_empty);
        rv_matieres = (RecyclerView) findViewById(R.id.rv_matieres);
        rv_matieres.setLayoutManager(new LinearLayoutManager(this, 1, false));
        matieresAdapter = new rv_matieresAdapter(database.getAllMatieresByClasse(ID_CLASSE));
        rv_matieres.setAdapter(matieresAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab3);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_add_matiere, null);
        popupAddMatiere = Popup.createPopup(this, view, true);
        btnAjouterMatiere = (Button) view.findViewById(R.id.btnAjouterMatiere);
        et_lib = (EditText) view.findViewById(R.id.et_lib);
        et_nbH = (EditText) view.findViewById(R.id.et_nbH);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        tv_empty.setText("Vous n'avez pas de matières pour l'instant.");
        if(matieresAdapter.getItemCount()==0)
            tv_empty.setVisibility(View.VISIBLE);
        else
            tv_empty.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupAddMatiere.show();
            }
        });

        btnAjouterMatiere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String lib = et_lib.getText().toString();
                if (lib.length() == 0 || et_nbH.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tou les inforamtions.", Toast.LENGTH_LONG).show();
                    return;
                }
                int nbH = Integer.parseInt(et_nbH.getText().toString());

                Long insertedId = database.ajouterMatiere(new Matiere(0,lib,nbH,ID_CLASSE));
                if (insertedId == -1)
                    Toast.makeText(getApplicationContext(), "Une erreur dans l'insertion !", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "La matière ("+lib+") à été ajoutée avec succes.", Toast.LENGTH_LONG).show();

                popupAddMatiere.dismiss();
                et_lib.setText("");
                et_nbH.setText("");
                matieresAdapter.setListeMatieres(database.getAllMatieresByClasse(ID_CLASSE));
                if(matieresAdapter.getItemCount()==0)
                    tv_empty.setVisibility(View.VISIBLE);
                else
                    tv_empty.setVisibility(View.INVISIBLE);
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
