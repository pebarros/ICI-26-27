package es.ucm.fdi.ici.c2627.practica0.grupoIndividual;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BlinkyController2 implements GhostIndividualController{

	public MOVE getMove(Game game, int limit) {
		int pacmanNode = game.getPacmanCurrentNodeIndex(); //Posicion actual MsPacman
		MOVE move = null;
			if(game.doesGhostRequireAction(GHOST.BLINKY)) {
				int ghostNode = game.getGhostCurrentNodeIndex(GHOST.BLINKY); //Posicion actual Ghost
				MOVE lastMove = game.getGhostLastMoveMade(GHOST.BLINKY); //Ultimo movimiento Ghost
				boolean edible = game.getGhostEdibleTime(GHOST.BLINKY) > 0;
				int closestPowerPill = game.getClosestNodeIndexFromNodeIndex(pacmanNode, game.getActivePowerPillsIndices(),DM.PATH);
				double distancePowerPill = game.getDistance(pacmanNode, closestPowerPill, DM.PATH);
			
				if (edible || isPacmanHeadingToActivePowerPill(game, pacmanNode)) {
					move = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(GHOST.BLINKY), DM.PATH);
				} else {
				move = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode, lastMove, DM.PATH);
				}
			}
		return move;
	}
	private boolean isPacmanHeadingToActivePowerPill(Game game, int pacmanNode) {
	    MOVE pacmanLastMove = game.getPacmanLastMoveMade();
	    int[] activePowerPills = game.getActivePowerPillsIndices();

	    if (pacmanLastMove == null || pacmanLastMove == MOVE.NEUTRAL || activePowerPills.length == 0) {
	        return false;
	    }

	    int curr = game.getNeighbour(pacmanNode, pacmanLastMove);
	    while (curr != -1) {
	        for (int pp : activePowerPills) {
	            if (curr == pp) return true; // Detectada PowerPill en el camino
	        }
	        if (game.isJunction(curr)) break; // Si llega a un cruce, termina el tramo
	        
	        int[] neighbours = game.getNeighbouringNodes(curr, pacmanLastMove);
	        if (neighbours == null || neighbours.length != 1) {
	            curr = -1;
	        } else {
	            curr = neighbours[0];
	        }
	    }
	      }
	
	public String getName() {
		return "Blinky";
	}
}