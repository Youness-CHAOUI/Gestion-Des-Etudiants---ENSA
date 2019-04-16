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
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.*;
import chaoui.cy_15.www.gestiondabsencecy_15.*;

/**
 * Created by CY_15 on 28/04/2018.
 */
public class rv_matieresAdapter extends RecyclerView.Adapter<rv_matieresAdapter.myViewHolder>
{
    ArrayList<Matiere> listeMatieres;

    public rv_matieresAdapter(ArrayList<Matiere> liste)
    {
        listeMatieres=liste;
    }

    public void setListeMatieres(ArrayList<Matiere> listeMatieres)
    {
        this.listeMatieres = listeMatieres;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.matieres_card_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder myviewholder, int position)
    {
        myviewholder.update(listeMatieres.get(position));
    }

    @Override
    public int getItemCount() {
        return listeMatieres.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder //implements View.OnCreateContextMenuListener//, View.OnContextClickListener
    {
        TextView tv_id, tv_lib, tv_nbH;
        final Context itemContext;

        public myViewHolder(final View itemView)
        {
            super(itemView);
            itemContext = itemView.getContext();

            tv_id = itemView.findViewById(R.id.tv_id);
            tv_lib = itemView.findViewById(R.id.tv_lib);
            tv_nbH = itemView.findViewById(R.id.tv_nbH);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(itemContext, seancesActivity.class);
                    i.putExtra("idMatiere", Integer.parseInt(tv_id.getText().toString()));
                    i.putExtra("idClasse", listeMatieres.get(0).getId_classe());
                    itemContext.startActivity(i);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(itemContext)
                            .setTitle("La Matière : "+tv_id.getText())
                            .setMessage(tv_lib.getText())
                            .show();
                    return false;
                }
            });
        }

        public void update(Matiere m)
        {
            tv_id.setText(String.valueOf(m.getId()));
            tv_lib.setText(m.getLibelle());
            tv_nbH.setText(String.valueOf(m.getNbHeures()));
        }
    }
}
