package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.stream.Stream;

/**
 * Find the final position of a mower
 * Inputs :
 *  - Size of the grid (or the map)
 *  - Inputs for each mower : initial position, initial orientation
 *  - list of instructions to mover the mower in the grid
 */

public class MowItNow {

    private static final String STRING_SPACE = " ";

    public static void main(String[] args) throws Exception {

        // Read file instructions
        File file = new File("src/main/resources/instructions.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        // Read the size of the grid (the coordinates of the top right point)
        String firstLine = br.readLine();
        int[] gridSize = Stream.of(firstLine.trim().split(STRING_SPACE)).mapToInt(Integer::parseInt).toArray();

        String line = br.readLine();
        while (line != null) {
            String[] values = line.trim().split(STRING_SPACE);
            int x = Integer.parseInt(values[0]);
            int y = Integer.parseInt(values[1]);
            String orientation = values[2];
            Mower mower = new Mower(x, y, Orientation.valueOf(orientation));
            String positionLine = br.readLine();
            try {
                mower.getFinalPosition(positionLine, gridSize[0], gridSize[1]);
            } catch (Exception e) {
                System.out.println("An error has occurred :" + e.getMessage());
                System.out.println("Move to the next mower");
            }
            System.out.println(mower.x + STRING_SPACE + mower.y + STRING_SPACE + mower.orientation);
            line = br.readLine();
        }

    }

    enum Orientation {
        N,
        S,
        E,
        O
    }

    public static class Mower {

        int x;
        int y;
        Orientation orientation;

        public Mower(int x, int y, Orientation orientation) {
            this.x = x;
            this.y = y;
            this.orientation = orientation;
        }

        public void getFinalPosition(String line, int limitX, int limitY) throws Exception {
            char[] instructions = line.toCharArray();
            for (int i = 0; i < instructions.length; i++) {
                switch (instructions[i]) {
                    case 'D' -> turnRight();
                    case 'G' -> turnLeft();
                    case 'A' -> move(limitX, limitY);
                    default -> throw new Exception("This instruction is unknown");
                }
            }

        }

        /**
         *  this function change the orientation of the mower to the right whatever was the orientation
         *  turns the mower 90 degree clockwise without moving it
         */
        private void turnRight() {
            switch (orientation) {
                case N -> orientation = Orientation.E;
                case S -> orientation = Orientation.O;
                case E -> orientation = Orientation.S;
                case O -> orientation = Orientation.N;
            }


        }
        /**
         * this function change the orientation of the mower to the left whatever was the orientation
         * turns the mower 90 degree counter-clockwise without moving it
         */
        private void turnLeft() {
            switch (orientation) {
                case N -> orientation = Orientation.O;
                case S -> orientation = Orientation.E;
                case E -> orientation = Orientation.N;
                case O -> orientation = Orientation.S;
            }

        }

        /**
         * this function change the position of the mower
         * move one step forward in the last orientation
         * @param limitX the width of the grid
         * @param limitY the length of the grid
         */
        private void move(int limitX, int limitY) {
            switch (orientation) {
                case N:
                    if (y < limitY) y ++ ;
                    break;
                case S:
                    if (y > 0) y-- ;
                    break;
                case E:
                    if (y < limitX) x++ ;
                    break;
                case O:
                    if (x > 0) x-- ;
                    break;
            }
        }
    }
}