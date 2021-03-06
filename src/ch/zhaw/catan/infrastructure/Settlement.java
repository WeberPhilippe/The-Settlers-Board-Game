package ch.zhaw.catan.infrastructure;

import ch.zhaw.catan.board.SettlersBoard;
import ch.zhaw.catan.player.Player;

import java.awt.*;

import static ch.zhaw.catan.infrastructure.Structure.SETTLEMENT;

/**
 * Class that contains the logic regarding a settlement.
 *
 * @author weberph5
 * @version 1.0.0
 */
public class Settlement extends AbstractInfrastructure {

    /**
     * Settlements may only be built with the build method. Therefore, constructor is private.
     *
     * @param owner    player owner
     * @param position position where the settlement is being set to.
     * @author weberph5
     */
    private Settlement(Player owner, Point position) {
        super(owner, position);
    }

    /**
     * Method for the initial settlement builds in the founding phase where conditions differ from the main phase
     *
     * @param owner       player to whom the building should be assigned to.
     * @param coordinates Position where the settlement is being set to.
     * @param board       the current board to place the settlements on.
     * @return true if building was successful, false if not.
     * @author weberph5
     */
    public static boolean initialSettlementBuild(Player owner, Point coordinates, SettlersBoard board) {
        if (board.hasCorner(coordinates) && board.getCorner(coordinates) == null && board.getNeighboursOfCorner(coordinates).isEmpty() && board.isCornerNotSurroundedByWater(coordinates)) {
            final Settlement settlement = new Settlement(owner, coordinates);
            buildSettlement(board, settlement);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Build method for building a new Settlement.
     *
     * @param owner       player to whom the settlement should be assigned to.
     * @param coordinates position where the settlement is being set to.
     * @param board       the current board to place the settlements on.
     * @return true if successfully built, false if not.
     * @author weberph5
     */
    public static boolean build(Player owner, Point coordinates, SettlersBoard board) {
        final Settlement settlement = new Settlement(owner, coordinates);
        if (settlement.canBuild(board)) {
            buildSettlement(board, settlement);
            owner.payForStructure(settlement.getStructureType());
            return true;
        } else {
            return false;
        }
    }

    private static void buildSettlement(SettlersBoard board, Settlement settlement) {
        board.buildSettlement(settlement);
        settlement.finalizeBuild();
    }

    /**
     * Checks if settlement can be built.
     *
     * @param board the board to check it upon
     * @return true if it can be built, false otherwise
     */
    @Override
    protected boolean canBuild(SettlersBoard board) {
        final Player owner = getOwner();
        return board.hasCorner(getPosition())
                && board.getCorner(getPosition()) == null
                && board.getNeighboursOfCorner(getPosition()).isEmpty()
                && hasOwnRoadAdjacent(getPosition(), board)
                && owner.hasEnoughLiquidityFor(SETTLEMENT)
                && owner.hasEnoughInStructureStock(SETTLEMENT)
                && board.isCornerNotSurroundedByWater(getPosition());
    }

    /**
     * Increments winning points and structure count
     */
    @Override
    protected void finalizeBuild() {
        super.finalizeBuild();
        getOwner().incrementWinningPoints();
    }

    /**
     * Get Structure Type as Enum
     *
     * @return the structure type as enum
     */
    @Override
    protected Structure getStructureType() {
        return SETTLEMENT;
    }
}
