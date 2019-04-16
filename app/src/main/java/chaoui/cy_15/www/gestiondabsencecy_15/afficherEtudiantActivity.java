package chaoui.cy_15.www.gestiondabsencecy_15;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import chaoui.cy_15.www.gestiondabsencecy_15.classes.Database.myDBHelper;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.Etudiant;

public class afficherEtudiantActivity extends AppCompatActivity
{
    myDBHelper database;
    TextView tv_cne, tv_nom, tv_prenom, tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_etudiant);

        database=new myDBHelper(this);

        tv_cne = (TextView) findViewById(R.id.tv_cne);
        tv_prenom = (TextView) findViewById(R.id.tv_prenom);
        tv_nom = (TextView) findViewById(R.id.tv_nom);
        tv_email = (TextView) findViewById(R.id.tv_email);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        String cne = getIntent().getExtras().getString("cne");
        Etudiant etd = database.findEtudiantByCNE(cne);

        tv_cne.setText(etd.getCne());
        tv_prenom.setText(etd.getPrenom());
        tv_nom.setText(etd.getNom());
        tv_email.setText(etd.getEmail());
    }
}
