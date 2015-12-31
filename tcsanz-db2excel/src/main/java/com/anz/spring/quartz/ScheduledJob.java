package com.anz.spring.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.anz.excel.Db2Excel;
import com.anz.service.VoucherService;

public class ScheduledJob extends QuartzJobBean{
	
	private VoucherService voucherService;
	
	private Db2Excel db2Excel;
	
	/**
	 * @param voucherService the voucherService to set
	 */
	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	/**
	 * @param db2Excel the db2Excel to set
	 */
	public void setDb2Excel(Db2Excel db2Excel) {
		this.db2Excel = db2Excel;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
        try {
			db2Excel.toExcel(voucherService.getVoucherRef());
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
