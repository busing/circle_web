package com.circle.utils;

import java.text.DecimalFormat;

public class MapUtil {
	public static Double getDistanceFromXtoY(double lat_a, double lng_a, double lat_b, double lng_b){
		double pk = 180 / 3.14169;
		double a1 = lat_a / pk;
		double a2 = lng_a / pk;
		double b1 = lat_b / pk;
		double b2 = lng_b / pk;
		double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
		double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
		double t3 = Math.sin(a1) * Math.sin(b1);
		double tt = Math.acos(t1 + t2 + t3);
		return 6366000 * tt;
	}
	
	public static String getMapDistance(Double dis){
		DecimalFormat format = new DecimalFormat("#");
		return format.format(dis);
	}
	
	public static void main(String args[]){
		double tt = MapUtil.getDistanceFromXtoY(32.078894, 118.77899, 32.082698, 118.790882);
		double tt1 = MapUtil.getDistanceFromXtoY(37.480563,121.467113, 37.480591,121.467926);
		System.out.println(tt);
		System.out.println(tt1);
		System.out.println(getMapDistance(1999.202));
	}
}