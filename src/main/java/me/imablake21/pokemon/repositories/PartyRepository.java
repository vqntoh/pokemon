package me.imablake21.pokemon.repositories;

import me.imablake21.pokemon.entities.Party;

import java.io.IOException;

public interface PartyRepository {
    void save();
    Party load() throws IOException;
}
