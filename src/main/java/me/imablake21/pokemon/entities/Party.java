package me.imablake21.pokemon.entities;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Party {

    private final List<Pokemon> pokemonList;
    private final String saveFileName;

    public Party(String saveFileName) {
        this.pokemonList = new ArrayList<>();
        this.saveFileName = saveFileName;
    }
    
    public void swap(int index1, int index2) {
        if (index1 < 0 || index1 >= pokemonList.size() || index2 < 0 || index2 >= pokemonList.size()) {
            // Potremmo lanciare un'eccezione, ma per ora non facciamo nulla per sicurezza.
            System.err.println("Indici per lo scambio fuori dai limiti.");
            return;
        }
        // La classe Collections di Java ha un metodo molto comodo per scambiare elementi in una lista.
        Collections.swap(pokemonList, index1, index2);
    }

    // Metodi di gestione della lista

    public boolean addPokemon(Pokemon pokemon) {
        if (isFull()) {
            return false;
        }
        return this.pokemonList.add(pokemon);
    }

    public Pokemon getPokemon(int index) {
        return this.pokemonList.get(index);
    }
    
    public Pokemon getFirstAvailablePokemon() {
        for (Pokemon p : pokemonList) {
            if (!p.isFainted()) {
                return p;
            }
        }
        return null; // O il primo, se sono tutti esausti
    }

    public int getSize() {
        return this.pokemonList.size();
    }

    public boolean isFull() {
        return this.pokemonList.size() >= 6;
    }

    // Restituisce una copia non modificabile della lista per la visualizzazione,
    // proteggendo la lista originale (buona pratica di incapsulamento).
    public List<Pokemon> getPokemonList() {
        return Collections.unmodifiableList(pokemonList);
    }
    
    // Metodi di Salvataggio e Caricamento

    public void loadFromFile() {
        this.pokemonList.clear();
        // Usiamo getResourceAsStream per leggere dal classpath
        String path = "/assets/saves/" + this.saveFileName;
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) throw new IOException("File di salvataggio non trovato: " + path);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        String name = parts[0];
                        int level = Integer.parseInt(parts[1]);
                        int maxHp = Integer.parseInt(parts[2]);
                        int currentHp = Integer.parseInt(parts[3]);
                        int attack = Integer.parseInt(parts[4]);
                        int speed = Integer.parseInt(parts[5]);

                        Pokemon p = new Pokemon(name, level, maxHp, attack, 0, speed); // defense a 0 momentaneamente
                        p.setCurrentHp(currentHp);
                        addPokemon(p);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nel caricare la squadra: " + e.getMessage());
        }
    }

    public boolean saveToFile() {
        // Definiamo una cartella di salvataggio esterna al JAR
        // Esempio: cartella "saves" nella home dell'utente
        File saveDir = new File(System.getProperty("user.home"), ".pokemon/saves");
        if (!saveDir.exists()) saveDir.mkdirs();

        File saveFile = new File(saveDir, this.saveFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            for (Pokemon p : this.pokemonList) {
                String line = String.join(",",
                        p.getName(),
                        String.valueOf(p.getLevel()),
                        String.valueOf(p.getMaxHp()),
                        String.valueOf(p.getCurrentHp()),
                        String.valueOf(p.getAttack()),
                        String.valueOf(p.getSpeed())
                );
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Salvato correttamente in: " + saveFile.getAbsolutePath());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
