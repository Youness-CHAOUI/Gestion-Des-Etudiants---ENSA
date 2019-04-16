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

import chaoui.cy_15.www.gestiondabsencecy_15.R;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.ListeAbsence;
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.Matiere;
import chaoui.cy_15.www.gestiondabsencecy_15.listeAbsenceActivity;

/**
 * Created by CY_15 on 28/04/2018.
 */
public class rv_seancesAdapter extends RecyclerView.Adapter<rv_seancesAdapter.myViewHolder>
{
    ArrayList<ListeAbsence> listeSeances;
    int ID_CLASSE, ID_MATIERE;

    public rv_seancesAdapter(int id_classe, int id_mat, ArrayList<ListeAbsence> liste)
    {
        ID_CLASSE=id_classe;
        ID_MATIERE=id_mat;
        listeSeances=liste;
    }

    public void setListeSeances(ArrayList<ListeAbsence> listeSeances)
    {
        this.listeSeances = listeSeances;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.seances_card_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder myviewholder, int position)
    {
        myviewholder.update(listeSeances.get(position));
    }

    @Override
    public int getItemCount() {
        return listeSeances.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_id, tv_date, tv_heure;
        final Context itemContext;

        public myViewHolder(final View itemView)
        {
            super(itemView);
            itemContext = itemView.getContext();

            tv_id = itemView.findViewById(R.id.tv_id);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_heure = itemView.findViewById(R.id.tv_heure);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(itemContext, listeAbsenceActivity.class);
                    i.putExtra("idSeance", Integer.parseInt(tv_id.getText().toString()));
                    i.putExtra("idClasse", ID_CLASSE);
                    i.putExtra("idMatiere", ID_MATIERE);
                    itemContext.startActivity(i);
                }
            });
        }

        public void update(ListeAbsence lst)
        {
            tv_id.setText(String.valueOf(lst.getId()));
            tv_date.setText(lst.getDate().toString());
            tv_heure.setText(lst.getHeure().toString());
        }
    }
}
