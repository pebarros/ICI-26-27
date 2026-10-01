package es.ucm.fdi.ici.c2627.practica0.grupoIndividual;

import java.util.Random;

import pacman.game.Game;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;

public class SueController implements GhostIndividualController {
	private Random rnd = new Random();
	private MOVE[] allMoves = MOVE.values();
	private int lastRandomMove;
			
	public MOVE getMove(Game game, int limit) {
		int pacmanNode = game.getPacmanCurrentNodeIndex(); 
		MOVE move = null;
		if (game.getCurrentLevelTime() - lastRandomMove >= 100) {
		    move = allMoves[rnd.nextInt(allMoves.length)];
		    lastRandomMove = game.getCurrentLevelTime();
		} else {
				int ghostNode = game.getGhostCurrentNodeIndex(GHOST.SUE);
				MOVE lastMove = game.getGhostLastMoveMade(GHOST.SUE); 
				boolean edible = game.getGhostEdibleTime(GHOST.SUE) > 0;
				int closestPowerPill = game.getClosestNodeIndexFromNodeIndex(pacmanNode, game.getActivePowerPillsIndices(),DM.PATH);
				double distancePowerPill = game.getDistance(pacmanNode, closestPowerPill, DM.PATH);
				boolean closePowerPill = distancePowerPill <limit;
				if (edible || closePowerPill) {
					move = game.getApproximateNextMoveAwayFromTarget(ghostNode, pacmanNode, game.getGhostLastMoveMade(GHOST.SUE), DM.PATH);
				} else {
				move = game.getApproximateNextMoveTowardsTarget(ghostNode, pacmanNode, lastMove, DM.PATH);
				}
			}
		return move;
	}
	
	public String getName() {
		return "Sue";
	}
}
