package me.imablake21.pokemon.repositories;

import me.imablake21.pokemon.entities.Party;
import me.imablake21.pokemon.entities.Pokemon;

import java.io.IOException;
import java.util.List;

public interface PartyRepository {
    void save(Party party);
    List<Pokemon> load() throws IOException;
}
