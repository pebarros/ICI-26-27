package es.ucm.fdi.ici.c2627.practica0.grupoIndividual;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class InkyController implements GhostIndividualController{

    public MOVE getMove(Game game, int limit) {
        MOVE move = null;
        int pacmanNode = game.getPacmanCurrentNodeIndex(); // Posicion actual MsPacman
        int ghostNode = game.getGhostCurrentNodeIndex(GHOST.INKY); // Posicion actual Ghost
        MOVE lastMove = game.getGhostLastMoveMade(GHOST.INKY); // Ultimo movimiento Ghost
        boolean edible = game.getGhostEdibleTime(GHOST.INKY) > 0;
        int[] activePowerPills = game.getActivePowerPillsIndices();
        int closestPowerPill = -1;
        if (activePowerPills.length > 0) closestPowerPill = game.getClosestNodeIndexFromNodeIndex(pacmanNode, activePowerPills, DM.PATH);
        if (edible || isPacmanHeadingToActivePowerPill(game, pacmanNode, limit)) move = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode, lastMove, DM.PATH); 
        else if (activePowerPills.length > 0 && closestPowerPill != -1) move = game.getApproximateNextMoveTowardsTarget(ghostNode, closestPowerPill, lastMove, DM.PATH);
        else move = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode, lastMove, DM.PATH);
        return move;
    }
    
    private boolean isPacmanHeadingToActivePowerPill(Game game, int pacmanNode, int limit) {
	    MOVE pacmanLastMove = game.getPacmanLastMoveMade();
	    int[] activePowerPills = game.getActivePowerPillsIndices();
	    if (pacmanLastMove == null || pacmanLastMove == MOVE.NEUTRAL || activePowerPills.length == 0) return false;
	    int closestPowerPill = game.getClosestNodeIndexFromNodeIndex(pacmanNode, activePowerPills, DM.PATH);
	    double currentDistance = game.getDistance(pacmanNode, closestPowerPill, DM.PATH);
	    if (currentDistance > limit) return false;
	    int nextNode = game.getNeighbour(pacmanNode, pacmanLastMove);
	    if (nextNode == -1) return false;
	    double nextDistance = game.getDistance(nextNode, closestPowerPill, DM.PATH);
	    return nextDistance < currentDistance;
	}
    
    public String getName() {
        return "InkyController";
    }
}