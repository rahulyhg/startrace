package org.nobibi.startrace;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CanvasAstro {
	public static void main(String[] args) {
		Group root = new Group();
		Scene s = new Scene(root, 300, 300, Color.BLACK);

		final Canvas canvas = new Canvas(250,250);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		 
		gc.setFill(Color.BLUE);
		gc.fillRect(75,75,100,100);
		 
		root.getChildren().add(canvas);
	}
}
