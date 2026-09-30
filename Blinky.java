package es.ucm.fdi.ici.c2627.practica0.grupoIndividual;

import java.util.EnumMap;

import pacman.controllers.GhostController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class Blinky {

	
	public MOVE getMove(Game game, long timeDue) {
		int pacmanNode = game.getPacmanCurrentNodeIndex(); //Posicion actual MsPacman
		MOVE move = null;
			if(game.doesGhostRequireAction(GHOST.BLINKY)) {
				int ghostNode = game.getGhostCurrentNodeIndex(GHOST.BLINKY); //Posicion actual Ghost
				MOVE lastMove = game.getGhostLastMoveMade(GHOST.BLINKY); //Ultimo movimiento Ghost
				boolean edible = game.getGhostEdibleTime(GHOST.BLINKY) > 0;
				int closestPowerPill = game.getClosestNodeIndexFromNodeIndex(pacmanNode, game.getActivePowerPillsIndices(),DM.PATH);
				double distancePowerPill = game.getDistance(pacmanNode, closestPowerPill, DM.PATH);
				boolean closePowerPill = distancePowerPill <limit;
				if (edible || closePowerPill) {
					move = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(ghost), DM.PATH));
				} else {
				move = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode, lastMove, DM.PATH);
				}
			}
		return move;
	}
	
	public String getName() {
		return "Blinky";
	}
}
