package chaoui.cy_15.www.gestiondabsencecy_15;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import chaoui.cy_15.www.gestiondabsencecy_15.classes.Adapters.rv_classesAdapter;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Database.myDBHelper;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Dialog.Popup;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.Classe;

public class classesActivity extends AppCompatActivity
{
    myDBHelper database;
    RecyclerView rv_classes;
    rv_classesAdapter classesAdapter;
    AlertDialog popupAddClass;
    Button btnAjouterClasse;
    FloatingActionButton fab;
    EditText etLibClasse;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database=new myDBHelper(this);

        rv_classes = (RecyclerView) findViewById(R.id.rv_classes);
        rv_classes.setLayoutManager(new LinearLayoutManager(this, 1, false));
        classesAdapter=new rv_classesAdapter(database.getAllClasses());
        rv_classes.setAdapter(classesAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_add_classe, null);
        popupAddClass = Popup.createPopup(this, view, true);
        btnAjouterClasse = (Button) view.findViewById(R.id.btnAjouterClasse);
        etLibClasse = (EditText) view.findViewById(R.id.etLibClasse);

        /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupAddClass.show();
            }
        });

        btnAjouterClasse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String lib = etLibClasse.getText().toString();
                if (lib.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Veuillez remplire tout les champs !", Toast.LENGTH_LONG).show();
                    return;
                }
                Long insertedId = database.ajouterClasse(new Classe(0, lib));
                if (insertedId == -1)
                    Toast.makeText(getApplicationContext(), "Une erreur dans l'insertion !", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "La classe (" + lib + ") à été ajoutée avec succes.", Toast.LENGTH_LONG).show();

                popupAddClass.dismiss();
                etLibClasse.setText("");
                classesAdapter.setListeClasses(database.getAllClasses());
            }
        });
    }
}
