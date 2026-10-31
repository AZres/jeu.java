import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Charge les mots depuis un fichier texte (un mot par ligne) et en tire au hasard. */
public class Dictionnaire {
    private static final String[] SECOURS = {
        "bus", "chat", "arbre", "python", "table", "livre", "soleil", "maison",
        "chien", "ecole", "voiture", "fleur", "jardin", "stylo", "ordinateur", "pont"
    };

    private final List<String> mots = new ArrayList<>();

    public Dictionnaire(String nomFichier) {
        try {
            Set<String> uniques = new LinkedHashSet<>();
            for (String ligne : Files.readAllLines(Paths.get(nomFichier), StandardCharsets.UTF_8)) {
                String mot = ligne.trim().toLowerCase();
                // au moins 3 caractères, uniquement des lettres
                if (mot.length() >= 3 && mot.matches("\\p{L}+")) uniques.add(mot);
            }
            mots.addAll(uniques);
        } catch (IOException e) {
            System.out.println("Fichier '" + nomFichier + "' introuvable : utilisation de la liste interne.");
        }
        if (mots.size() < 4) {
            mots.clear();
            Collections.addAll(mots, SECOURS);
        }
    }

    /** Tire n mots différents au hasard. */
    public List<String> tirer(int n, java.util.Random random) {
        List<String> copie = new ArrayList<>(mots);
        Collections.shuffle(copie, random);
        return new ArrayList<>(copie.subList(0, n));
    }
}
