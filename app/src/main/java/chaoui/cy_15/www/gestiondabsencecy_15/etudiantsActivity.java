package chaoui.cy_15.www.gestiondabsencecy_15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import chaoui.cy_15.www.gestiondabsencecy_15.classes.Adapters.rv_classesAdapter;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Adapters.rv_etudiantsAdapter;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Database.myDBHelper;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Dialog.Popup;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.Classe;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.Etudiant;

public class etudiantsActivity extends AppCompatActivity
{
    myDBHelper database;
    RecyclerView rv_etudiants;
    rv_etudiantsAdapter etudiantsAdapter;
    AlertDialog popupAddEtudiant;
    Button btnAjouterEtudiant;
    FloatingActionButton fab;
    EditText et_cne, et_prenom, et_nom, et_email;

    int ID_CLASSE;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiants);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        database=new myDBHelper(this);
        ID_CLASSE = getIntent().getExtras().getInt("idClasse");
        Toast.makeText(this, "La classe N° "+ID_CLASSE, Toast.LENGTH_LONG).show();

        rv_etudiants = (RecyclerView) findViewById(R.id.rv_etudiants);
        rv_etudiants.setLayoutManager(new LinearLayoutManager(this, 1, false));
        etudiantsAdapter = new rv_etudiantsAdapter(database.getAllEtudiantByClasse(ID_CLASSE));
        rv_etudiants.setAdapter(etudiantsAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab2);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_add_etudiant, null);
        popupAddEtudiant = Popup.createPopup(this, view, true);
        btnAjouterEtudiant = (Button) view.findViewById(R.id.btnAjouterEtudiant);
        et_cne = (EditText) view.findViewById(R.id.et_CNE);
        et_prenom = (EditText) view.findViewById(R.id.et_prenom);
        et_nom = (EditText) view.findViewById(R.id.et_nom);
        et_email = (EditText) view.findViewById(R.id.et_email);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupAddEtudiant.show();
            }
        });

        btnAjouterEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String cne = et_cne.getText().toString();
                String prenom = et_prenom.getText().toString();
                String nom = et_nom.getText().toString();
                String email = et_email.getText().toString();

                if (cne.length() < 10 || cne.length() > 10) {
                    Toast.makeText(getApplicationContext(), "CNE avec 10 chiffres.\nexemple: 1200000000", Toast.LENGTH_LONG).show();
                    return;
                }
                if (prenom.length() == 0 || nom.length() == 0 || email.length()==0) {
                    Toast.makeText(getApplicationContext(), "Veuillez SVP remplir toutes les inforamtions.", Toast.LENGTH_LONG).show();
                    return;
                }
                Long insertedId = database.ajouterEtudiant(new Etudiant(cne,prenom,nom,email,ID_CLASSE));
                if (insertedId == -1)
                    Toast.makeText(getApplicationContext(), "Une erreur dans l'insertion !\n\nCNE existe déjà.", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "L'étudiant(e) (" + prenom+" "+nom + ") à été ajouté(e) avec succes.", Toast.LENGTH_LONG).show();

                popupAddEtudiant.dismiss();
                et_cne.setText("");
                et_prenom.setText("");
                et_nom.setText("");
                et_email.setText("");
                etudiantsAdapter.setListeEtudiants(database.getAllEtudiantByClasse(ID_CLASSE));
                etudiantsAdapter.notifyDataSetChanged();
            }
        });
    }

    /** ActionBar Menu **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_etudiants, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.uploadEtdList:
                Toast.makeText(this, "Chargement de la liste des étudiants.", Toast.LENGTH_SHORT).show();
                String file = uploadFile();
                if(file.length()==0)
                    Toast.makeText(this, "La liste est vide.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, file, Toast.LENGTH_LONG).show();
                break;
            case R.id.matieres:
                Intent i = new Intent(this, matieresActivity.class);
                i.putExtra("idClasse", ID_CLASSE);
                startActivity(i);
                break;
            case R.id.quitter:
                finishAffinity();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    String uploadFile()
    {
        String file="";
        File dir = Environment.getExternalStorageDirectory();
        File fichier = new File(dir, "liste.txt");
        Toast.makeText(this, dir.getAbsolutePath().toString(), Toast.LENGTH_LONG).show();

        if(fichier.exists())
        {
            StringBuilder txt = new StringBuilder();
            try
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fichier));
                String ligne;
                while ((ligne=bufferedReader.readLine())!=null)
                {
                    txt.append(ligne);
                }
                file = txt.toString();
                Toast.makeText(this, "TXT: "+txt.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(this, "FILE: "+file, Toast.LENGTH_LONG).show();

            }
            catch (Exception ex){
                Toast.makeText(this, "Une erreur c'est produite lors du Chargement du fichier.", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }else
            Toast.makeText(this, "fichier n'existe plus.", Toast.LENGTH_LONG).show();


        /*try
        {
            FileInputStream fis = new FileInputStream("ListeEtudiants.txt");
            int c;
            while ((c=fis.read())!=-1)
            {
                file+= String.valueOf((char)c);
            }
        }
        catch (Exception ex){
            Toast.makeText(this, "Une erreur c'est produite lors du Chargement du fichier.", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }*/

        return file;
    }
}
