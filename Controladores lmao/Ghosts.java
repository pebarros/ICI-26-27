package es.ucm.fdi.ici.c2627.practica0.grupoIndividual;

import java.util.EnumMap;
import java.util.Random;

import pacman.controllers.GhostController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Ghosts extends GhostController{
	private EnumMap<GHOST, MOVE> moves = new EnumMap<GHOST, MOVE>(GHOST.class);
	private EnumMap<GHOST, GhostIndividualController> controllers = new EnumMap<>(GHOST.class);
	private int limit = 35;
	
	public Ghosts() {
	    controllers.put(GHOST.BLINKY, new BlinkyController());
	    controllers.put(GHOST.PINKY, new PinkyController());
	    controllers.put(GHOST.INKY, new InkyController());
	    controllers.put(GHOST.SUE, new SueController());
	}
	@Override
	public EnumMap<GHOST, MOVE> getMove(Game game, long timeDue){
		moves.clear();
		for(GHOST ghost : GHOST.values()) {
			if (game.doesGhostRequireAction(ghost)) {
		        MOVE move = controllers.get(ghost).getMove(game, limit);
		        moves.put(ghost, move);
			}
		}
		return moves;
	}
	
	public String getName() {
		return "Ghosts";
	}
}
