/**
 * 
 */
package com.anz;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Administrator
 *
 */
public class Main {

	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("applicationContext.xml");
	}

}
