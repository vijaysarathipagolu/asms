
package com.csu.asms.deployment;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

/**
 * @author Vijay Sarathi Pagolu
 *
 */
public class DeploymentListener implements ServletContextListener {

	private static final Logger log = LoggerFactory.getLogger(DeploymentListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * this method is load the database details form the properties file and run the 
	 * liquibase databse refactoring logic.
	 * */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		log.debug("In Servlet Context for database deployment");
		String asmsHome = System.getenv("ASMS_HOME");
		if (asmsHome == null) {
			asmsHome = System.getProperty("ASMS_HOME");
		}

		Properties props = new Properties();
		try {
			String fileSeperator = System.getProperty("file.separator");
			if (asmsHome != null)
				props.load(new FileInputStream(asmsHome + fileSeperator + "jdbc.properties"));
			else
				props.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));

			Class.forName(props.getProperty("jdbc.driverClassName"));

			System.out.println("jdbc url is---" + props.getProperty("jdbc.url"));
			System.out.println("jdbc username----" + props.getProperty("jdbc.username"));
			System.out.println("jdbc password----" + props.getProperty("jdbc.password"));

			Connection c = DriverManager.getConnection(props.getProperty("jdbc.url"),
					props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));

			System.out.println(c);

			Liquibase liquibase = null;

			try {

				Database database = DatabaseFactory.getInstance()
						.findCorrectDatabaseImplementation(new JdbcConnection(c));
				liquibase = new Liquibase("db.changelog.xml", new ClassLoaderResourceAccessor(), database);
				/*
				 * if(props.getProperty("dropFirst").trim().equalsIgnoreCase(
				 * "true")) liquibase.dropAll();
				 */
				liquibase.update(null);
				log.debug("--------Database is created-----");

			} catch (Exception e) {
				throw new DatabaseException(e);
			} finally {
				if (c != null) {
					try {
						// c.rollback();
						c.close();
					} catch (SQLException e) {
						// nothing to do
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
