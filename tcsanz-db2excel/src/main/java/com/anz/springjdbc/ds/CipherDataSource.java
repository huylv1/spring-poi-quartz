/**
 * 
 */
package com.anz.springjdbc.ds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.anz.cipher.CipherUtils;

/**
 * @author Administrator
 *
 */
public class CipherDataSource extends DriverManagerDataSource {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CipherDataSource.class);
	
	public CipherDataSource() {
        super();
    }
	
	@Override
	public void setPassword(String password) {
		try {
			super.setPassword(CipherUtils.decrypt(password));
		} catch (Exception e) {
			LOGGER.error("Unable to decrypt data source password: {}", password);
			System.exit(0);
		}
	}
}
