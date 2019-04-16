package chaoui.cy_15.www.gestiondabsencecy_15.classes.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import chaoui.cy_15.www.gestiondabsencecy_15.R;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Database.myDBHelper;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.*;

/**
 * Created by CY_15 on 28/04/2018.
 */
public class rv_listeAbsenceAdapter extends RecyclerView.Adapter<rv_listeAbsenceAdapter.myViewHolder>
{
    //ArrayList<Etudiant> listeEtudiants;
    ArrayList<Presence> listePresence;
    myDBHelper database;
    int ID_SEANCE, ID_CLASSE;

    public rv_listeAbsenceAdapter(myDBHelper database, int id_seance, int id_classe)
    {
        this.database=database;
        this.ID_SEANCE=id_seance;
        this.ID_CLASSE=id_classe;
        //listeEtudiants=database.getAllEtudiantByClasse(id_classe);
        listePresence=database.getAllSeancesByIdLA(id_seance);
    }

    /*public void setListeEtudiants(ArrayList<Etudiant> listeEtudiants)
    {
        this.listeEtudiants = listeEtudiants;
    }*/
    public void setListePresence(ArrayList<Presence> liste)
    {
        this.listePresence = liste;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.liste_absence_card_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder myviewholder, int position)
    {
        //myviewholder.update(listeEtudiants.get(position));
        myviewholder.update(listePresence.get(position));
    }

    @Override
    public int getItemCount() {
        //return listeEtudiants.size();
        return listePresence.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_cne, tv_prenom, tv_nom;
        Switch sw_presence;
        final Context itemContext;

        public myViewHolder(final View itemView)
        {
            super(itemView);
            itemContext = itemView.getContext();

            tv_cne = itemView.findViewById(R.id.tv_cne);
            tv_prenom = itemView.findViewById(R.id.tv_prenom);
            tv_nom = itemView.findViewById(R.id.tv_nom);
            sw_presence = itemView.findViewById(R.id.sw_presence);

            sw_presence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = sw_presence.isChecked();
                    if(isChecked)
                    {
                        sw_presence.setText("Présent(e)");
                        sw_presence.setTextColor(Color.GREEN); //itemContext.getResources().getColor(R.color.XXX);
                        //database
                        database.updatePresenceByCne(tv_cne.getText().toString(), ID_SEANCE, true);
                    }
                    else
                    {
                        sw_presence.setText("Absent(e)");
                        sw_presence.setTextColor(Color.LTGRAY);
                        //database
                        database.updatePresenceByCne(tv_cne.getText().toString(), ID_SEANCE, false);
                    }
                }
            });
        }

        public void update(Presence presence)
        {
            String cne = presence.getCne();
            tv_cne.setText(cne);

            Etudiant e = database.findEtudiantByCNE(cne);
            tv_prenom.setText(e.getPrenom());
            tv_nom.setText(e.getNom());

            if(presence.getPresent()==1)
            {
                sw_presence.setChecked(true);
                sw_presence.setText("Présent(e)");
                sw_presence.setTextColor(Color.GREEN); //itemContext.getResources().getColor(R.color.XXX);
            }
            else
            {
                sw_presence.setChecked(false);
                sw_presence.setText("Absent(e)");
                sw_presence.setTextColor(Color.LTGRAY);
            }
        }

        /*public void update(Etudiant e)
        {
            String cne = e.getCne();
            tv_cne.setText(cne);
            tv_prenom.setText(e.getPrenom());
            tv_nom.setText(e.getNom());

            Presence p = database.getPresenceByCne(cne, ID_SEANCE);
            if(p.getPresent()==1)
            {
                sw_presence.setText("Présent(e)");
                sw_presence.setTextColor(itemContext.getResources().getColor(R.color.colorPrimary));
            }
            else
            {
                sw_presence.setText("Absent(e)");
                sw_presence.setTextColor(Color.RED);
            }
        }*/
    }
}
