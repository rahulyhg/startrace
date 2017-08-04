package com.nobibi.startrace.anylize.earthquake;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.nobibi.startrace.swisseph.SwissEphHelper;

import swisseph.SweConst;

import com.alibaba.druid.pool.DruidDataSource;

public class CalPlanet {
	
	private static final String QUERY_E = "select * from n_earthquake e where e.mag >= 6";

	public static void main(String[] args) {
		
		SwissEphHelper swissEph = SwissEphHelper.getInstance();
		
		DruidDataSource ds = new DruidDataSource();
		ds.setDbType("mysql");
		
		ds.setUrl("jdbc:mysql://182.92.79.240:3306/astro");
		ds.setUsername("root");
		ds.setPassword("zhaokc1104");
		
		try {
			Connection conn = ds.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement(QUERY_E);
			
			ResultSet rs = stmt.executeQuery();
			
			conn.setAutoCommit(false);
			while (rs.next()) {
				int id = rs.getInt("id");
				Date date = rs.getTimestamp("time");
				
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(date.getTime());
				
				double[] positions = new double[12];
				for (int i = SweConst.SE_SUN; i <= SweConst.SE_TRUE_NODE; i++) {
					//if (i == SweConst.SE_MEAN_NODE) continue; 
					double[] pos = swissEph.calPlanetPos(cal, i); //行星位置
					positions[i] = pos[0];
				}
				
				stmt = conn.prepareStatement("insert into p_earthquake_planet_pos value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				stmt.setInt(1, id);
				for (int i = 0; i < positions.length; i++) {
					stmt.setDouble(i+2, positions[i]);
				}
				stmt.setDouble(14, 0.0);
				stmt.setTimestamp(15, new Timestamp(new Date().getTime()));
				stmt.execute();
				conn.commit();
				stmt.close();
				
				System.out.println("insert : "+ id);
			}
			
			conn.close();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void insertPos() {
		
	}

}
