package chaoui.cy_15.www.gestiondabsencecy_15.classes.Metier;

/**
 * Created by CY_15 on 28/04/2018.
 */
public class Matiere
{
    int id;
    String libelle;
    int nbHeures;
    int id_classe;

    public static String sqlCreateClasse="create table Matiere " +
                                        "(id integer primary key autoincrement,"+
                                        "libelle TEXT," +
                                        "nbHeures integer," +
                                        "id_classe integer)";
    public static String sqlDropClasse="drop table if exists Matiere";

    public Matiere() {}
    public Matiere(int id, String libelle, int nbHeures, int id_classe) {
        this.id = id;
        this.libelle = libelle;
        this.nbHeures = nbHeures;
        this.id_classe = id_classe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getNbHeures() {
        return nbHeures;
    }

    public void setNbHeures(int nbHeures) {
        this.nbHeures = nbHeures;
    }

    public int getId_classe() {
        return id_classe;
    }

    public void setId_classe(int id_classe) {
        this.id_classe = id_classe;
    }

    @Override
    public String toString() {
        return "Matiere{" +
                "id='" + id + '\'' +
                ", libelle='" + libelle + '\'' +
                ", nbHeures=" + nbHeures +
                ", id_classe=" + id_classe +
                '}';
    }
}
