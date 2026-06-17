package me.imablake21.pokemon.utils;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import static javax.imageio.ImageIO.read;

public final class SpriteLoader {

    private SpriteLoader() {}
        // Classe utility: costruttore privato per evitare istanziazione

    public static BufferedImage load(String path) {
        // Rimuovi lo slash se presente, perché getSystemResourceAsStream vuole il path relativo alla root
        String cleanPath = path.startsWith("/") ? path.substring(1) : path;

        // Questo metodo NON dipende dalla classe chiamante
        try (InputStream is = ClassLoader.getSystemResourceAsStream(cleanPath)) {
            if (is == null) {
                throw new RuntimeException("Il file non esiste nel classpath: " + cleanPath);
            }
            return read(is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
