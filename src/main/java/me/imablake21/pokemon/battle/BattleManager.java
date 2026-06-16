package me.imablake21.pokemon.battle;

import me.imablake21.pokemon.entities.Player;
import me.imablake21.pokemon.entities.Pokemon;
import me.imablake21.pokemon.main.GamePanel;
import me.imablake21.pokemon.main.GameWindow;

public class BattleManager {

    private final GameWindow parent;
    private final GamePanel parentPanel;
    private final Player player;

    public BattleManager(GameWindow parent, GamePanel parentPanel, Player player) {
        this.parent = parent;
        this.parentPanel = parentPanel;
        this.player = player;
    }

    public void startBattle(Pokemon playerPokemon, Pokemon enemyPokemon) {
        BattleScreen battleScreen = new BattleScreen(playerPokemon, enemyPokemon, parent, parentPanel, player);
        parent.setContentPane(battleScreen);
        parent.revalidate();
        parent.repaint();
    }
}
