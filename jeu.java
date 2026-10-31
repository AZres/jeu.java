import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/** Programme principal : jeu de devinette de mots à deux joueurs (console). */
public class Jeu {
    private static final int NB_MOTS = 4;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.println("=== JEU DE DEVINETTE DE MOTS ===");
        Joueur[] joueurs = new Joueur[2];
        for (int i = 0; i < 2; i++) {
            System.out.print("Nom du joueur " + (i + 1) + " : ");
            String nom = sc.nextLine().trim();
            if (nom.isEmpty()) nom = "Joueur " + (i + 1);
            joueurs[i] = new Joueur(nom);
        }

        Dictionnaire dico = new Dictionnaire("mots.txt");
        List<Mot> mots = new ArrayList<>();
        for (String s : dico.tirer(NB_MOTS, random)) mots.add(new Mot(s, random));

        int courant = 0;
        while (!tousTrouves(mots)) {
            afficherEtat(mots, joueurs);
            System.out.print("Tour de " + joueurs[courant].getNom() + ", votre proposition : ");
            String proposition = sc.nextLine();

            Mot cible = chercher(mots, proposition);
            if (cible != null) {
                cible.marquerTrouve();
                joueurs[courant].ajouterPoints(cible.getPoints());
                System.out.println("Bravo ! +" + cible.getPoints() + " points.");
            } else {
                System.out.println("Mauvaise réponse.");
            }
            courant = 1 - courant; // l'autre joueur joue
        }

        System.out.println("\n=== FIN DE PARTIE ===");
        afficherEtat(mots, joueurs);
        if (joueurs[0].getScore() > joueurs[1].getScore())
            System.out.println("Vainqueur : " + joueurs[0].getNom());
        else if (joueurs[1].getScore() > joueurs[0].getScore())
            System.out.println("Vainqueur : " + joueurs[1].getNom());
        else
            System.out.println("Égalité !");
        sc.close();
    }

    private static boolean tousTrouves(List<Mot> mots) {
        for (Mot m : mots) if (!m.estTrouve()) return false;
        return true;
    }

    /** Retourne le mot non trouvé qui correspond à la proposition, ou null. */
    private static Mot chercher(List<Mot> mots, String proposition) {
        for (Mot m : mots) if (!m.estTrouve() && m.correspond(proposition)) return m;
        return null;
    }

    private static void afficherEtat(List<Mot> mots, Joueur[] joueurs) {
        System.out.println("\n--- Mots ---");
        for (int i = 0; i < mots.size(); i++)
            System.out.println((i + 1) + ". " + mots.get(i).getAffichage());
        System.out.println("--- Scores ---");
        for (Joueur j : joueurs) System.out.println(j.getNom() + " : " + j.getScore());
        System.out.println();
    }
}
