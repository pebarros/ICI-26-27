package es.ucm.fdi.ici.c2627.practica0.grupoIndividual;

import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class PinkyController implements GhostIndividualController {


    public MOVE getMove(Game game, int limit) {
        MOVE move = null;

        if (game.doesGhostRequireAction(GHOST.PINKY)) {
            int pacmanNode = game.getPacmanCurrentNodeIndex(); // Posicion actual MsPacman
            int ghostNode = game.getGhostCurrentNodeIndex(GHOST.PINKY); // Posicion actual Ghost
            MOVE lastMove = game.getGhostLastMoveMade(GHOST.PINKY); // Ultimo movimiento Ghost
            
            boolean edible = game.getGhostEdibleTime(GHOST.PINKY) > 0;
            
            int closestPowerPill = game.getClosestNodeIndexFromNodeIndex(pacmanNode, game.getActivePowerPillsIndices(), DM.PATH);
            double distancePowerPill = game.getDistance(pacmanNode, closestPowerPill, DM.PATH);

            if (edible || isPacmanHeadingToActivePowerPill(game, pacmanNode, limit)) {
                move = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode, lastMove, DM.PATH);
            } else {
                // --- CÁLCULO DEL PRÓXIMO CRUCE (sin función auxiliar) ---
                MOVE pacmanLastMove = game.getPacmanLastMoveMade();
                int targetJunction = pacmanNode;

                if (pacmanLastMove != null && pacmanLastMove != MOVE.NEUTRAL) {
                    int currentNode = pacmanNode;
                    int nextNode = game.getNeighbour(currentNode, pacmanLastMove);

                    // Avanzar por el pasillo en la dirección de Pac-Man hasta encontrar un cruce
                    while (nextNode != -1) {
                        currentNode = nextNode;

                        if (game.isJunction(currentNode)) {
                            targetJunction = currentNode;
                            break;
                        }
                        // Continuar por el pasillo sin dar la vuelta
                        int[] neighbours = game.getNeighbouringNodes(currentNode, pacmanLastMove);
                        if (neighbours == null || neighbours.length == 0) {
                            targetJunction = currentNode;
                            break;
                        }
                        if (neighbours.length == 1) {
                            nextNode = neighbours[0];
                        } else {
                            targetJunction = currentNode;
                            break;
                        }
                    }
                }
                // Dirigirse hacia el cruce interceptado
                move = game.getApproximateNextMoveTowardsTarget(ghostNode, targetJunction, lastMove, DM.PATH);
            }
        }
        return move;
    }
    
    private boolean isPacmanHeadingToActivePowerPill(Game game, int pacmanNode, int limit) {
	    MOVE pacmanLastMove = game.getPacmanLastMoveMade();
	    int[] activePowerPills = game.getActivePowerPillsIndices();

	    if (pacmanLastMove == null || pacmanLastMove == MOVE.NEUTRAL || activePowerPills.length == 0) {
	        return false;
	    }

	    int closestPowerPill = game.getClosestNodeIndexFromNodeIndex(
	        pacmanNode,
	        activePowerPills,
	        DM.PATH
	    );

	    double currentDistance = game.getDistance(
	        pacmanNode,
	        closestPowerPill,
	        DM.PATH
	    );

	    if (currentDistance > limit) {
	        return false;
	    }

	    int nextNode = game.getNeighbour(pacmanNode, pacmanLastMove);

	    if (nextNode == -1) {
	        return false;
	    }

	    double nextDistance = game.getDistance(
	        nextNode,
	        closestPowerPill,
	        DM.PATH
	    );

	    return nextDistance < currentDistance;
	}

    public String getName() {
        return "Pinky";
    }
}