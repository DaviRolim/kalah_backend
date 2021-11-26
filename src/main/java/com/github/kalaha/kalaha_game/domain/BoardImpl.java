package com.github.kalaha.kalaha_game.domain;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class BoardImpl implements Board {

    // TODO FIX capture when noUnits on your pit
    private static final Logger logger = LoggerFactory.getLogger(BoardImpl.class);

    public static final int NUMBER_OF_PITS = 6;
    public static final int START_UNITS = 4;

    private Players currentPlayer;
    private int numberOfPits;
    private int startUnits;
    private int[] pits;
    private int kalahIndexPlayer1;
    private int kalahIndexPlayer2;

    public BoardImpl() {
        this.numberOfPits = NUMBER_OF_PITS;
        this.startUnits = START_UNITS;
        // this.initialize();
    }

    @Override
    public void initialize() {
        this.currentPlayer = Players.PLAYER1;
        this.kalahIndexPlayer1 = numberOfPits;
        this.kalahIndexPlayer2 = numberOfPits * 2 + 1;
        initializePits();
    }

    @Override
    public int[] getPits() {
        return pits;
    }

 

    @Override
    public int getPlayer1Score() {
        return pits[kalahIndexPlayer1];
    }

    @Override
    public int getPlayer2Score() {
        return pits[kalahIndexPlayer2];
    }

    @Override
    public String getCurrentPlayerAsString() {
        if(currentPlayer.equals(Players.PLAYER1)) {
            return "Player1";
        } else {
            return "Player2";
        }
    }

    @Override
    public boolean isGameOver() {
        if(getTotalUnitsFromPlayer1Pit() == 0 || getTotalUnitsFromPlayer2Pit() == 0) {
            return true;
        }
        return false;
    }
    @Override
    public void executeMove(int index) {
        if(isMoveValid(index)){
            int lastPitIndex = moveUnits(index);

            if(canCaptureOppositeSide(lastPitIndex)) {
                executeCapture(lastPitIndex);
            }
            if(isGameOver()) {
                storeRemainingUnits();
            }
            decideNextPlayer(lastPitIndex);
        }
    }
   

    private void initializePits() {
        int totalPits = (numberOfPits + 1) * 2;
        pits = new int[totalPits];
        for(int i = 0; i < totalPits; i++) {
			if(i == kalahIndexPlayer1 || i == kalahIndexPlayer2) {
				pits[i] = 0;
			} else {
				pits[i] = startUnits;
			}
		}
    }

    private boolean isMoveValid(int index) {
        if(pits[index] > 0)
        if(hasUnitInPit(index) && 
            (currentPlayer == Players.PLAYER1 && isPlayer1Pit(index)) ||
            (currentPlayer == Players.PLAYER2 && isPlayer2Pit(index))) {
            return true;
        }
        return false;
    }

    @Override
    public int moveUnits(int index) {
        int units = pits[index];
        this.pits[index] = 0;
        int nextPitIndex = index + 1;
        for( ; units > 0 ; nextPitIndex++, units--) {
            if(nextPitIndex == pits.length) {
                nextPitIndex = 0;
            }
            pits[nextPitIndex]++;
        }

        int lastPitIndex = nextPitIndex - 1;
        return lastPitIndex;
    }

    private boolean canCaptureOppositeSide(int lastPitIndex) {
        if(lastPitIndex == kalahIndexPlayer1 || lastPitIndex == kalahIndexPlayer2) {
            return false;
        }
        if(currentPlayer.equals(Players.PLAYER1) && !isPlayer1Pit(lastPitIndex)) {
            return false;
        }
        if(currentPlayer.equals(Players.PLAYER2) && !isPlayer2Pit(lastPitIndex)) {
            return false;
        }
        return pits[lastPitIndex] == 1; // finishes the play with 1 unit in the pit (it had nothing before)
    }

    private void executeCapture(int lastPitIndex) {
        int opositeIndex = kalahIndexPlayer2 - lastPitIndex;
        int unitsToStore = pits[lastPitIndex] + pits[opositeIndex];
        this.pits[lastPitIndex] = 0;
        this.pits[opositeIndex] = 0;
        if(currentPlayer.equals(Players.PLAYER1)) {
            this.pits[kalahIndexPlayer1] += unitsToStore;
        } else {
            this.pits[kalahIndexPlayer2] += unitsToStore;
        }
    }

    private boolean hasUnitInPit(int index) {
        return pits[index] > 0;
    }

    private boolean isPlayer1Pit(int index) {
        return index < kalahIndexPlayer1;
    }

    private boolean isPlayer2Pit(int index) {
        return index > kalahIndexPlayer1 && index != kalahIndexPlayer2;
    }

    private void decideNextPlayer(int lastIndex) {
        if(shouldChangeCurrentPlayer(lastIndex)) {
            if(currentPlayer == Players.PLAYER1) {
                this.currentPlayer = Players.PLAYER2;
            } else if (currentPlayer == Players.PLAYER2) {
                this.currentPlayer = Players.PLAYER1;
            }
        }
    }

    @Override
    public boolean shouldChangeCurrentPlayer(int lastPitIndex) {
		if(currentPlayer.equals(Players.PLAYER1)) {
			if(lastPitIndex == kalahIndexPlayer1) {
				return false;
			}
			return true;
		} else {
			if(lastPitIndex == kalahIndexPlayer2) {
				return false;
			}
			return true;
		}
	}

    private int getTotalUnitsFromPlayer1Pit() {
        int totalPlayer1 = 0;
        for(int i = 0 ; i < numberOfPits; i++ ) {
            totalPlayer1 += pits[i];
        }
        return totalPlayer1;
    }

    private int getTotalUnitsFromPlayer2Pit() {
        int totalPlayer2 = 0;
        for(int i = numberOfPits + 1 ; i < numberOfPits * 2 + 1; i++ ) {
            totalPlayer2 += pits[i];
        }
        return totalPlayer2;
    }

    private void storeRemainingUnits() {
        if(getTotalUnitsFromPlayer1Pit() == 0) {
            pits[kalahIndexPlayer2] += getTotalUnitsFromPlayer2Pit();
        } else {
            pits[kalahIndexPlayer1] += getTotalUnitsFromPlayer1Pit();
        }
        setAllPitsToZero();

    }

    private void setAllPitsToZero() {
        int totalPits = (numberOfPits + 1) * 2;
        for(int i = 0; i < totalPits; i++) {
			if(i != kalahIndexPlayer1 && i != kalahIndexPlayer2) {
				pits[i] = 0;
			} 
		}
    }

}