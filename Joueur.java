/** Un joueur avec un nom et un score. */
public class Joueur {
    private final String nom;
    private int score;

    public Joueur(String nom) {
        this.nom = nom;
        this.score = 0;
    }

    public void ajouterPoints(int points) { score += points; }
    public String getNom() { return nom; }
    public int getScore() { return score; }
}
