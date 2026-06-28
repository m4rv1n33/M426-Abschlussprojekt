package nusextended.m426.game.rendering;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import nusextended.m426.game.GameState;
import nusextended.m426.game.rendering.PaintHelper;

public class ShapeRenderer {
    private final Point2D shapeOrigin = new Point2D(407.5, 250);
    private final double shapeRadius = 225;
    private final double vertexSize = 16;
    private final double lineWidth = 6;
    private final double spinSpeed = 0.1; // radii per second

    private final Canvas canvas;
    private final GraphicsContext g2d;
    private double spinOffset;
    private GameState gameState;

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public ShapeRenderer (Canvas canvas, GameState gameState) {
        g2d = canvas.getGraphicsContext2D();
        g2d.setLineWidth(lineWidth);
        g2d.setStroke(PaintHelper.WHITE);
        g2d.setFill(PaintHelper.BLACK);
        g2d.fillRect(0, 0, 5, canvas.getHeight());
        this.canvas = canvas;
        this.gameState = gameState;
    }

    public void renderShape(double delta) {
        if (gameState == null) return;
        int vertexCount = gameState.getActiveShape().getVertices();
        double anglePerVertex = Math.TAU / vertexCount;
        Point2D[] points = new Point2D[vertexCount];

        spinOffset += Math.TAU * spinSpeed * delta / 1000.0;

        if (vertexCount > 1) {
            for (int i = 0; i < vertexCount; i++) {
                Point2D p = new Point2D(
                        Math.cos((anglePerVertex * i) + spinOffset),
                        Math.sin((anglePerVertex * i) + spinOffset)
                ).multiply(shapeRadius);

                points[i] = p.add(shapeOrigin);
            }
        } else {
            points[0] = shapeOrigin;
        }

        g2d.setFill(PaintHelper.WHITE);
        for (Point2D p : points) {
            g2d.fillOval(
                    p.getX() - vertexSize / 2, p.getY() - vertexSize / 2,
                    vertexSize, vertexSize);
        }

        for (int i = 0; i < vertexCount; i++) {
            Point2D p1 = points[i];
            Point2D p2;

            if (i == vertexCount - 1) { p2 = points[0]; }
            else { p2 = points[i + 1]; }

            g2d.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }
    }
    
    public void clearCanvas() {
        g2d.clearRect(4, 0, canvas.getWidth(), canvas.getHeight());
        g2d.setFill(PaintHelper.GREY);
        g2d.fillRect(4, 0, canvas.getWidth(), canvas.getHeight());
    }
}