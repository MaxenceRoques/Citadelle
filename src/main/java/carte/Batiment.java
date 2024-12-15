package carte;

public class Batiment extends Carte {
    private String nom;  // une carte bâtiment possède un nom
    private int cout; // une carte bâtiment possède un coût de construction
    private quartier type; // une carte bâtiment est un quartier particulier

    public Batiment(String nom,int cout, quartier type){
        this.nom = nom;
        this.cout = cout;
        this.type = type;
    }

    public String getNom(){
        return this.nom;
    }

    public int getCout(){
        return this.cout;
    }

    public quartier getType() {
        return this.type;
    }

    // la seule action d'un bâtiment est le fait de le construire
    public String messageActionCarte(){
        return "La carte "+this.getNom()+" a été construite.";
    }

    @Override
    public String toString(){ // Permet d'afficher la cité et la main du Joueur
        return getNom();
    }
}
