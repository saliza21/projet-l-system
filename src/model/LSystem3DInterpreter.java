package model;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LSystem3DInterpreter implements LSystelVisualInterpreter{
    List<Cylinder> cylinders;
    Group group;
    double angle;

    public LSystem3DInterpreter( double angle) {
        this.group = new Group();
        this.angle = angle;
    }

    @Override
    public void drawResults(String result) {
        cylinders = new ArrayList<>();
        group =  new Group();

        double totalHeight = 0;
        double totalWidth = 0;
        double length = 70.0;
        double thickness = 10.0;

        Stack<Point3D> positionStack = new Stack<>();
        Stack<Double> angleStack = new Stack<>();
        Stack<Double> randomAngleStack = new Stack<>();

        Point3D currentPosition = new Point3D(0, 0, 0);
        double currentAngle = 0;
        double randomAngle = 0;

        Color currentColor = Color.GREEN;

        for (char c : result.toCharArray()) {
            switch (c) {
                case 'F': // Draw forward
                    currentPosition = drawCylindreAndGetNewPosition(group, thickness, length, cylinders, currentColor, currentAngle, randomAngle, currentPosition);
                    break;
                case 'X': // Draw forward
                    currentAngle = 90;
                    randomAngle = 0;
                    currentPosition = drawCylindreAndGetNewPosition(group, thickness, length, cylinders, currentColor, currentAngle, randomAngle, currentPosition);
                    break;
                case 'Y': // Draw forward
                    currentAngle = 0;
                    randomAngle = 0;
                    currentPosition = drawCylindreAndGetNewPosition(group, thickness, length, cylinders, currentColor, currentAngle, randomAngle, currentPosition);
                    break;
                case 'Z': // Draw forward
                    currentAngle = 0;
                    randomAngle = 90;
                    currentPosition = drawCylindreAndGetNewPosition(group, thickness, length, cylinders, currentColor, currentAngle, randomAngle, currentPosition);
                    break;
                case '+': // Turn right
                    currentAngle += angle;
                    randomAngle += 45;

                    break;
                case '-': // Turn left
                    currentAngle -= angle;
                    randomAngle -= 90;
                    break;
                case '[': // Save current state
                    positionStack.push(currentPosition);
                    angleStack.push(currentAngle);
                    randomAngleStack.push(randomAngle);
                    currentColor = positionStack.size() < 2 ? Color.GREEN : Color.YELLOW;
                    break;
                case ']': // Restore previous state
                    if (!positionStack.isEmpty() && !angleStack.isEmpty()) {
                        currentPosition = positionStack.pop();
                        currentAngle = angleStack.pop();
                        randomAngle = randomAngleStack.pop();
                    }
                    currentColor = positionStack.size() < 2 ? Color.GREEN : Color.YELLOW;
                    break;
                default:
                    break;
            }
        }
    }

    private static Point3D drawCylindreAndGetNewPosition(Group group, double thickness, double length, List<Cylinder> cylinders, Color currentColor, double currentAngle, double randomAngle, Point3D currentPosition) {
        Cylinder line = new Cylinder(thickness, length);
        cylinders.add(line);
        line.setMaterial(new PhongMaterial(currentColor));
        group.getChildren().add(line);


        double translateX = (length / 2) * Math.sin(Math.toRadians(currentAngle));
        double translateY = -(length / 2) * Math.cos(Math.toRadians(currentAngle)) * Math.cos(Math.toRadians(randomAngle));
        double translateZ = -(length / 2) * Math.sin(Math.toRadians(randomAngle))*Math.cos(Math.toRadians(currentAngle));

        line.setTranslateX(currentPosition.getX() + translateX);
        line.setTranslateY(currentPosition.getY() + translateY);
        line.setTranslateZ(currentPosition.getZ() + translateZ);

        Rotate rotatex = new Rotate(randomAngle, Rotate.X_AXIS);
        Rotate rotatey = new Rotate(0, Rotate.Y_AXIS);
        Rotate rotatez = new Rotate(currentAngle, Rotate.Z_AXIS);

        line.getTransforms().addAll(
                rotatex,
                rotatey,
                rotatez
        );
        currentPosition = new Point3D(
                currentPosition.getX() + 2 * translateX,
                currentPosition.getY() + 2 * translateY,
                currentPosition.getZ() + 2 * translateZ
        );
        return currentPosition;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
