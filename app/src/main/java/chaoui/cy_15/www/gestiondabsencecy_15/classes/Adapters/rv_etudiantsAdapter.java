package chaoui.cy_15.www.gestiondabsencecy_15.classes.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import chaoui.cy_15.www.gestiondabsencecy_15.R;
import chaoui.cy_15.www.gestiondabsencecy_15.afficherEtudiantActivity;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.*;

/**
 * Created by CY_15 on 28/04/2018.
 */
public class rv_etudiantsAdapter extends RecyclerView.Adapter<rv_etudiantsAdapter.myViewHolder>
{
    ArrayList<Etudiant> listeEtudiants;

    public rv_etudiantsAdapter(ArrayList<Etudiant> liste)
    {
        this.listeEtudiants=liste;
    }

    public void setListeEtudiants(ArrayList<Etudiant> liste_Etudiants)
    {
        this.listeEtudiants = liste_Etudiants;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.etudiants_card_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder myviewholder, int position)
    {
        myviewholder.update(listeEtudiants.get(position));
    }

    @Override
    public int getItemCount() {
        return listeEtudiants.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder //implements View.OnCreateContextMenuListener//, View.OnContextClickListener
    {
        TextView tv_cne, tv_prenom, tv_nom;
        final Context itemContext;

        public myViewHolder(final View itemView)
        {
            super(itemView);
            itemContext = itemView.getContext();

            tv_cne = itemView.findViewById(R.id.tv_cne);
            tv_prenom = itemView.findViewById(R.id.tv_prenom);
            tv_nom = itemView.findViewById(R.id.tv_nom);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent i = new Intent(itemContext, afficherEtudiantActivity.class);
                    i.putExtra("cne", tv_cne.getText());
                    itemContext.startActivity(i);
                }
            });
        }

        public void update(Etudiant e)
        {
            tv_cne.setText(e.getCne());
            tv_prenom.setText(e.getPrenom());
            tv_nom.setText(e.getNom());
        }
    }
}
