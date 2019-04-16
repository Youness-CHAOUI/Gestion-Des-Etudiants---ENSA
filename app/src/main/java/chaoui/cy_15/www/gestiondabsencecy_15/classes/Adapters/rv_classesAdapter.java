package chaoui.cy_15.www.gestiondabsencecy_15.classes.Adapters;

import android.app.Activity;
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
import chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier.Classe;
import chaoui.cy_15.www.gestiondabsencecy_15.etudiantsActivity;

/**
 * Created by CY_15 on 28/04/2018.
 */
public class rv_classesAdapter extends RecyclerView.Adapter<rv_classesAdapter.myViewHolder>
{
    ArrayList<Classe> listeClasses;

    public rv_classesAdapter(ArrayList<Classe> liste)
    {
        listeClasses=liste;
    }

    public void setListeClasses(ArrayList<Classe> listeClasses)
    {
        this.listeClasses = listeClasses;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.classes_card_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(myViewHolder myviewholder, int position)
    {
        myviewholder.update(listeClasses.get(position));
    }

    @Override
    public int getItemCount() {
        return listeClasses.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder //implements View.OnCreateContextMenuListener//, View.OnContextClickListener
    {
        TextView tv_id, tv_lib;
        final Context itemContext;

        public myViewHolder(final View itemView)
        {
            super(itemView);
            itemContext = itemView.getContext();

            tv_id = itemView.findViewById(R.id.tv_id);
            tv_lib = itemView.findViewById(R.id.tv_filiere);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(itemContext, etudiantsActivity.class);
                    i.putExtra("idClasse", Integer.parseInt(tv_id.getText().toString()));
                    itemContext.startActivity(i);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(itemContext)
                            .setTitle("La classe : "+tv_id.getText())
                            .setMessage(tv_lib.getText())
                            .show();
                    return false;
                }
            });
        }

        public void update(Classe c)
        {
            //iv_produit.setImageResource(R.drawable.p0);
            tv_id.setText(String.valueOf(c.getId()));
            tv_lib.setText(c.getLibelle());
        }
    }
}
