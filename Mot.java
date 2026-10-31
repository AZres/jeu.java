import java.text.Normalizer;
import java.util.Random;

/** Représente un mot caché avec une seule lettre révélée. */
public class Mot {
    private final String secret;
    private final char[] affichage;
    private boolean trouve;

    public Mot(String secret, Random random) {
        this.secret = secret;
        this.affichage = new char[secret.length()];
        for (int i = 0; i < affichage.length; i++) affichage[i] = '_';
        int position = random.nextInt(secret.length());
        affichage[position] = Character.toUpperCase(secret.charAt(position));
        this.trouve = false;
    }

    /** Minuscules, sans accents, sans espaces autour : sert à comparer les mots. */
    public static String normaliser(String s) {
        String n = Normalizer.normalize(s.trim(), Normalizer.Form.NFD);
        return n.replaceAll("\\p{M}", "").toLowerCase();
    }

    public int getPoints() {
        int l = secret.length();
        if (l == 3) return 15;
        if (l == 4) return 20;
        if (l == 5) return 30;
        return 50;
    }

    public boolean correspond(String proposition) {
        return normaliser(proposition).equals(normaliser(secret));
    }

    public void marquerTrouve() { trouve = true; }
    public boolean estTrouve() { return trouve; }
    public String getSecret() { return secret; }

    /** Ex. : "_ A _ _" ou, une fois trouvé, "CHAT". */
    public String getAffichage() {
        if (trouve) return secret.toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < affichage.length; i++) {
            if (i > 0) sb.append(' ');
            sb.append(affichage[i]);
        }
        return sb.toString();
    }
}
