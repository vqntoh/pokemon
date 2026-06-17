package me.imablake21.pokemon.repositories;

import me.imablake21.pokemon.entities.Party;
import me.imablake21.pokemon.entities.Pokemon;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PartyRepositoryImpl implements  PartyRepository {

    @Override
    public void save(Party party) {
        File saveDir = new File("/saves");

        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        File save_file = new File(saveDir, "party.json");

        ObjectMapper mapper = new ObjectMapper();
//        mapper.writer(SerializationFeature.INDENT_OUTPUT);

        try {

            mapper.writeValue(save_file, party.getPokemonList());
            System.out.println("Salvato correttamente in: " + save_file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Non e' stato possibile salvare il party in: " + save_file.getAbsolutePath(), e);
        }
    }

    @Override
    public List<Pokemon> load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("saves/party.json")) {

            if (is == null) {
                throw new FileNotFoundException("File non trovato nel classpath: saves/party.json");
            }

            return mapper.readValue(is, new TypeReference<List<Pokemon>>() {});

        } catch (IOException e) {
            throw new IOException("Non e' stato possibile caricare il party dal classpath", e);
        }
    }
}
