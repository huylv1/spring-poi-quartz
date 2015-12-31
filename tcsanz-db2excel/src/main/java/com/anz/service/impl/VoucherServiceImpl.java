/**
 * 
 */
package com.anz.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anz.dao.VoucherDao;
import com.anz.service.VoucherService;

/**
 * @author Administrator
 *
 */
@Service("voucherService")
public class VoucherServiceImpl implements VoucherService {
	
	@Autowired
	private VoucherDao voucherDao;

	/* (non-Javadoc)
	 * @see com.anz.service.VoucherService#getVoucherRef()
	 */
	@Override
	@Transactional
	public Map<String, Object[]> getVoucherRef() {
		return voucherDao.getVoucherRef();
	}

}
