package es.ucm.fdi.ici.c2627.practica0.grupoIndividual;

import pacman.game.Constants.MOVE;
import pacman.game.Game;

public interface GhostIndividualController {
	
	MOVE getMove(Game game, int limit);
	
}
