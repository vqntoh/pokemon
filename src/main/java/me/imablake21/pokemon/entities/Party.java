package me.imablake21.pokemon.entities;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Party {

    private final List<Pokemon> party;

    public Party(List<Pokemon> party) {
        this.party = party;
    }

    /**
     * Il metodo scambia i pokemon di posizione all'interno del party.
     * Se gli indici dovessero essere invalidi ci sara' un avvertimento in console.
     *
     * @param a primo indice da scambiare
     * @param b secondo indice
     */
    public void swap(int a, int b) {
        if (!areIndicesValid(a, b)) {
            logSwapError(a, b);
            return;
        }
        Collections.swap(party, a, b);
    }
    private boolean areIndicesValid(int a, int b) {
        return a >= 0 && a < party.size() &&
                b >= 0 && b < party.size();
    }
    private void logSwapError(int a, int b) {
        System.err.println("Warning: tentativo di swap ignorato, indici fuori range: " + a + ", " + b);
    }

    public Optional<Pokemon> addPokemon(Pokemon pokemon) {
        if (isFull()) {
            return Optional.empty();
        }
        this.party.add(pokemon);
        return Optional.of(pokemon);
    }

    public Optional<Pokemon> getPokemon(int index) {
        return Optional.ofNullable(this.party.get(index));
    }

    public Optional<Pokemon> removePokemon(int index) {
        return Optional.ofNullable(this.party.remove(index));
    }

    public Optional<Pokemon> getFirstAvailablePokemon() {
        for (Pokemon p : party) {
            if (p.isFainted()) {
                continue;
            }
            return Optional.of(p);
        }
        return Optional.empty();
    }

    public int getSize() {
        return this.party.size();
    }
    public boolean isFull() {
        return this.party.size() >= 6;
    }

    public List<Pokemon> getPokemonList() {
        return this.party;
    }
}
