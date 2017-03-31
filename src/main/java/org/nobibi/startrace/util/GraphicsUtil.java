package org.nobibi.startrace.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class GraphicsUtil {
	
	/**
	 * N等分圆
	 * @param originx	原点坐标-x
	 * @param originy	原点坐标-y
	 * @param radius	半径
	 * @param divideNum	N等分
	 * @return	各等分点坐标
	 */
	public static List<Point2D> divideCircle(double originx,double originy,double radius,int num) {
		
		double angle = 360/num;	//角度
		List<Point2D> points = new ArrayList<Point2D>();	//存放等分点坐标集合
		
		for (int i = 0; i < num; i++) {
			points.add(calPositionByAngle(originx, originy, radius, i*angle));						
		}
		return points;
	}
	
	public static List<Point2D> divideCircle(double originx,double originy,double radius,int num, double offset) {
		
		double angle = 360/num;	//角度
		List<Point2D> points = new ArrayList<Point2D>();	//存放等分点坐标集合
		
		for (int i = 0; i < num; i++) {
			points.add(calPositionByAngle(originx, originy, radius, offset+(i*angle)));						
		}
		return points;
	}
	
	/**
	 * 根据指定圆周角的点坐标
	 * @param originx
	 * @param originy
	 * @param radius
	 * @param angle
	 * @return 点坐标
	 */
	public static Point2D calPositionByAngle(double originx, double originy, double radius, double angle ) {
		Point2D origin = new Point2D.Double(originx, originy);	//原点
		Point2D point = new Point2D.Double();
		angle = angle % 360;
		if (angle < 90) {
			point.setLocation(origin.getX() - (Math.cos(Math.toRadians(angle)) * radius), origin.getY() + (Math.sin(Math.toRadians(angle)) * radius));
		} else if (angle < 180) {
			angle = angle - 90;
			point.setLocation(origin.getX() + (Math.sin(Math.toRadians(angle)) * radius), origin.getY() + (Math.cos(Math.toRadians(angle)) * radius));
		} else if (angle < 270) {
			angle = angle - 180;
			point.setLocation(origin.getX() + (Math.cos(Math.toRadians(angle)) * radius), origin.getY() - (Math.sin(Math.toRadians(angle)) * radius));

		} else if (angle < 360) {
			angle = angle - 270;
			point.setLocation(origin.getX() - (Math.sin(Math.toRadians(angle)) * radius), origin.getY() - (Math.cos(Math.toRadians(angle)) * radius));
		}
		return point;
	}
	
}
