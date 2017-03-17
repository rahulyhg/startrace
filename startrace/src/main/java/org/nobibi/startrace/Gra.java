package org.nobibi.startrace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.nobibi.startrace.util.GraphicsUtil;

import swisseph.SweDate;
import swisseph.SwissEph;

public class Gra {
	public static void main(String[] args) {
		int height = 1000;
		int width = 1000;
		int outerDiameter = 800; //外部圆直径
		int mediumDiameter = 700; //中间圆直径
		int innerDiameter = 300; //内圆直径
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = image.createGraphics();
		// 开启抗锯齿
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setBackground(Color.white);
		g2d.fillRect(0, 0, width, height);
		g2d.setPaint(Color.GRAY);
		g2d.setStroke(new BasicStroke(2));
		
		Ellipse2D outerCircle = new Ellipse2D.Double();
		int outerX = (width-outerDiameter)/2;
		int outerY = (height-outerDiameter)/2;
		outerCircle.setFrame(outerX, outerY, outerDiameter, outerDiameter);
		g2d.draw(outerCircle);
		
		Ellipse2D mediumCircle = new Ellipse2D.Double();
		int mediumX = (width-mediumDiameter)/2;
		int mediumY = (height-mediumDiameter)/2;
		mediumCircle.setFrame(mediumX, mediumY, mediumDiameter, mediumDiameter);
		g2d.draw(mediumCircle);
		
		Ellipse2D innerCircle = new Ellipse2D.Double();
		int innerX = (width-innerDiameter)/2;
		int innerY = (height-innerDiameter)/2;
		innerCircle.setFrame(innerX, innerY, innerDiameter, innerDiameter);
		g2d.draw(innerCircle);
		
		
		//计算宫位
		SweDate sweDate = new SweDate();
		SwissEph swiss = new SwissEph("F:\\common-lib\\swisseph\\data");
		
		double[] cups = new double[13];
		double[] ascmc = new double[10];
		
		swiss.swe_houses(sweDate.getJulDay(), 0, 39.90,116.30, 'B', cups, ascmc);

		
		List<Point2D> oPoints = GraphicsUtil.divideCircle(outerCircle.getCenterX(), outerCircle.getCenterY(), outerDiameter/2, 12, ascmc[0]);
		List<Point2D> iPoints = GraphicsUtil.divideCircle(mediumCircle.getCenterX(), mediumCircle.getCenterY(), mediumDiameter/2, 12, ascmc[0]);
		
		// 画12等分线
		int size = oPoints.size();
		for (int i = 0; i < size;i++) {
			Point2D op = oPoints.get(i);
			Point2D ip = iPoints.get(i);
			Line2D line = new Line2D.Double(op, ip);
			g2d.draw(line);
		}
		
		g2d.setPaint(Color.GREEN);
		for (int k = 1; k <= 12; k++) {
			Point2D p1 = GraphicsUtil.calPositionByAngle(innerCircle.getCenterX(), innerCircle.getCenterY(), innerDiameter/2, cups[k]-ascmc[0]);
			Point2D p2 = GraphicsUtil.calPositionByAngle(mediumCircle.getCenterX(), mediumCircle.getCenterY(), mediumDiameter/2, cups[k]-ascmc[0]);
			Point2D next = GraphicsUtil.calPositionByAngle(innerCircle.getCenterX(), innerCircle.getCenterY(), innerDiameter/2, cups[k==12 ? 1 : k + 1]);
			Point2D middle = new Point2D.Double((p2.getX() + next.getX())/2, (p2.getY() + next.getY())/2);
			
			Line2D houseLine = new Line2D.Double(p1, p2);
			g2d.draw(houseLine);
			g2d.drawString(String.valueOf(k), (float)middle.getX(),(float)middle.getY());
			
		}
		
		
		try {
			ImageIO.write(image, "jpg", new File("chart.jpg"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Swiss
	}
}
