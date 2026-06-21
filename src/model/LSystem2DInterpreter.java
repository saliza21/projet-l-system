package model;

import java.awt.*;
import java.util.Stack;

public class LSystem2DInterpreter implements LSystelVisualInterpreter{
    private Graphics g;
    private int steps;

    public LSystem2DInterpreter(Graphics g, int steps, int adjustAngle, int width, int height, Rectangle bounds, Boolean isStandard) {
        this.g = g;
        this.steps = steps;
        this.adjustAngle = adjustAngle;
        this.width = width;
        this.height = height;
        this.bounds = bounds;
        this.isStandard = isStandard;
    }

    private int adjustAngle;
    private int width;
    private int height;
    Rectangle bounds;
    Boolean isStandard;
    @Override
    public void drawResults(String result) {
        g.clearRect(0, 0, width, height);

        int centerX = bounds.x + bounds.width / 2;     //  width / 2; // Center of the canvas
        int bottomY = bounds.y + bounds.height;      //  -step-50; // Bottom of the canvas
        int x = isStandard ? 0 : centerX;               // Initial position
        int y = isStandard ? 0 : bottomY;             // Initial position
        double angle = 0;                           // Initial angle (in degrees)

        Stack<BranchState> branchStack = new Stack<>();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.BLACK);             // Select panel background color
        g2d.setColor(Color.GREEN);                  // Select the color for drawing result

        for (char symbol : result.toCharArray()) {
            if (symbol == 'F') {
                // Move forward and draw a line
                int newX = x + (int) (steps * Math.sin(Math.toRadians(angle)));
                int newY = y - (int) (steps * Math.cos(Math.toRadians(angle)));
                g2d.drawLine(x, y, newX, newY);
                x = newX;
                y = newY;
            } else if (symbol == '+') {
                // Turn right
                angle += adjustAngle;
            } else if (symbol == '-') {
                // Turn left
                angle -= adjustAngle;
            } else if (symbol == '[') {
                // Save current state to the stack (position and angle)
                branchStack.push(new BranchState(x, y, angle));
            } else if (symbol == ']') {
                // Restore state from the stack
                BranchState branchState = branchStack.pop();
                x = branchState.getX();
                y = branchState.getY();
                angle = branchState.getAngle();
            }
        }
    }
}
