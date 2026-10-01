package es.ucm.fdi.ici.c2627.practica0.grupoIndividual;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class InkyController implements GhostIndividualController {

    public MOVE getMove(Game game, int limit) {
        MOVE move = null;

            int pacmanNode = game.getPacmanCurrentNodeIndex(); // Posicion actual MsPacman
            int ghostNode = game.getGhostCurrentNodeIndex(GHOST.INKY); // Posicion actual Ghost
            MOVE lastMove = game.getGhostLastMoveMade(GHOST.INKY); // Ultimo movimiento Ghost
            
            boolean edible = game.getGhostEdibleTime(GHOST.INKY) > 0;
            int[] activePowerPills = game.getActivePowerPillsIndices();
            
            boolean closePowerPill = false;
            int closestPowerPill = -1;

            if (activePowerPills.length > 0) {
                closestPowerPill = game.getClosestNodeIndexFromNodeIndex(pacmanNode, activePowerPills, DM.PATH);
                double distancePowerPill = game.getDistance(pacmanNode, closestPowerPill, DM.PATH);
                closePowerPill = distancePowerPill < limit;
            }

            if (edible || closePowerPill) {
                move = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode, lastMove, DM.PATH);
            } 
            else if (activePowerPills.length > 0 && closestPowerPill != -1) {
                move = game.getApproximateNextMoveTowardsTarget(ghostNode, closestPowerPill, lastMove, DM.PATH);
            } 
            else {
                move = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode, lastMove, DM.PATH);
            }
        return move;
    }

    public String getName() {
        return "InkyController";
    }
}