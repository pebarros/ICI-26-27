package es.ucm.fdi.ici.c2627.practica0.grupoIndividual;

import java.awt.Color;
import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class MsPacManP extends PacmanController {
private static final int limit = 20;
private static final boolean pintar = true;

@Override
public MOVE getMove(Game game, long timeDue) {
	int pacmanNode = game.getPacmanCurrentNodeIndex();
	GHOST nearestChasingGhost = getNearestChasingGhost(game, pacmanNode, limit);
	if (nearestChasingGhost != null) {
			int ghostNode = game.getGhostCurrentNodeIndex(nearestChasingGhost);
			if (pintar) GameView.addLines(game, Color.RED, pacmanNode, ghostNode);
			return game.getApproximateNextMoveAwayFromTarget(pacmanNode, ghostNode, game.getPacmanLastMoveMade(), DM.PATH);
	}
	GHOST nearestEdibleGhost = getNearestEdibleGhost(game, pacmanNode, limit);
	if (nearestEdibleGhost != null) {
		int ghostNode = game.getGhostCurrentNodeIndex(nearestEdibleGhost);
		if (pintar) GameView.addLines(game, Color.GREEN, pacmanNode, ghostNode);
		return game.getApproximateNextMoveTowardsTarget(pacmanNode, ghostNode, game.getPacmanLastMoveMade(), DM.PATH);
	}
	int dangerousGhosts = getNumberOfDangerousGhosts(game, pacmanNode);
    if (dangerousGhosts >= 3) {
        int nearestPowerPill = getNearestPowerPill(game, pacmanNode);
        if (nearestPowerPill != -1) return game.getApproximateNextMoveTowardsTarget(pacmanNode, nearestPowerPill, game.getPacmanLastMoveMade(), DM.PATH);
    }
    int nearestPill = getNearestNormalPill(game, pacmanNode);
    if (nearestPill != -1) return game.getApproximateNextMoveTowardsTarget(pacmanNode, nearestPill, game.getPacmanLastMoveMade(), DM.PATH);
    return MOVE.NEUTRAL;
}


private GHOST getNearestChasingGhost(Game game, int pacmanNode, int limit) {
GHOST nearest = null;
double minDistance = Double.MAX_VALUE;
	for (GHOST ghost : GHOST.values()) {
		if (game.getGhostLairTime(ghost) > 0 || game.isGhostEdible(ghost)) continue;
		int ghostNode = game.getGhostCurrentNodeIndex(ghost);
		double distance = game.getDistance(pacmanNode, ghostNode, DM.PATH);
		if (distance >= 0 && distance <= limit && distance < minDistance) {
			minDistance = distance;
			nearest = ghost;
		}
	}
	return nearest;
	}

private GHOST getNearestEdibleGhost(Game game, int pacmanNode, int limit) {
	GHOST nearest = null;
	double minDistance = Double.MAX_VALUE;
	for (GHOST ghost : GHOST.values()) {
	if (game.getGhostLairTime(ghost) > 0 || !game.isGhostEdible(ghost)) continue;
	int ghostNode = game.getGhostCurrentNodeIndex(ghost);
	double distance = game.getDistance(pacmanNode, ghostNode, DM.PATH);
	if (distance >= 0 && distance <= limit && distance < minDistance) {
		minDistance = distance;
		nearest = ghost;
			}
		}
	return nearest;
	}

private int getNearestNormalPill(Game game, int pacmanNode) {
    int[] pills = game.getActivePillsIndices();
    if (pills.length == 0) return -1;
    int nearestPill = -1;
    double minDistance = Double.MAX_VALUE;
    for (int pill : pills) {
        double distance = game.getDistance(pacmanNode, pill, DM.PATH);
        if (distance < minDistance && !isPowerPillBefore(game, pacmanNode, pill)) {
            minDistance = distance;
            nearestPill = pill;
        }
    }
    return nearestPill;
}


private boolean isPowerPillBefore(Game game, int pacmanNode, int pill) {
    int[] powerPills = game.getActivePowerPillsIndices();
    if (powerPills.length == 0) return false;
    MOVE pacmanLastMove = game.getPacmanLastMoveMade();
    if (pacmanLastMove == null || pacmanLastMove == MOVE.NEUTRAL) return false;
    int currentNode = pacmanNode;
    int nextNode = game.getNeighbour(currentNode, pacmanLastMove);
    while (nextNode != -1) {
        if (nextNode == pill) return false;
        for (int powerPill : powerPills) {
            if (nextNode == powerPill) return true;
        }
        if (game.isJunction(nextNode)) break;
        int[] neighbours = game.getNeighbouringNodes(nextNode, pacmanLastMove);
        if (neighbours == null || neighbours.length != 1) break;
        currentNode = nextNode;
        nextNode = neighbours[0];
    }
    return false;
}

private int getNearestPowerPill(Game game, int pacmanNode) {
    int[] powerPills = game.getActivePowerPillsIndices();
    if (powerPills.length == 0) return -1;
    return game.getClosestNodeIndexFromNodeIndex(pacmanNode, powerPills, DM.PATH);
}

private int getNumberOfDangerousGhosts(Game game, int pacmanNode) {
    int numberOfGhosts = 0;
    for (GHOST ghost : GHOST.values()) {
        if (game.getGhostLairTime(ghost) > 0 || game.isGhostEdible(ghost)) continue;
        int ghostNode = game.getGhostCurrentNodeIndex(ghost);
        double distance = game.getDistance(pacmanNode, ghostNode, DM.PATH);
        if (distance >= 0 && distance <= limit) numberOfGhosts++;
    }
    return numberOfGhosts;
}


public String getName() {
	return "MsPacMan";
	}
}
