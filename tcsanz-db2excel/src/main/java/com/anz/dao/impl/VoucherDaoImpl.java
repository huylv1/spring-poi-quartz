/**
 * 
 */
package com.anz.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import com.anz.dao.VoucherDao;

import oracle.jdbc.OracleTypes;

/**
 * @author Administrator
 *
 */
@Repository
public class VoucherDaoImpl implements VoucherDao {
	
	@Autowired
	private DataSource dataSource;

	/* (non-Javadoc)
	 * @see com.anz.dao.VoucherDao#getVoucherRef()
	 */
	@Override
	public Map<String, Object[]> getVoucherRef() {
		Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();
		data.put("1", new Object[] {"Date", "Ref TDPA/GCP", "GDT voucher ref", "Status voucher"});
		
		ArrayList<Object[]> res = new VoucherStoredProcedure(dataSource).execute();
		
		//Put from 2th element 
		int k = 2;
		for (int i = 0; i < res.size(); i++) {
			int col = res.get(i).length;
			Object[] tmp = new Object[col];
			for (int j = 0; j < col; j++) {
				tmp[j] = res.get(i)[j];
			}
			//add row data
			data.put( k++ + "", tmp);
		}
		
		return data;
	}
	
	class VoucherStoredProcedure extends StoredProcedure {
		private static final String SPROC_NAME = "TCS_PCK_VOUCHERXLS.prc_export_tdpa";
	    
	    public VoucherStoredProcedure(DataSource dataSource) {
	    	super(dataSource, SPROC_NAME);
	        declareParameter(new SqlOutParameter("p_cur", OracleTypes.CURSOR, new TpdaMapper()));
	        compile();
	    }
	    
	    @SuppressWarnings("unchecked")
		public ArrayList<Object[]> execute() {
	        Map<String, Object> results = super.execute();
	        return (ArrayList<Object[]>) results.get("p_cur");
	    }
	}
	
	private final class TpdaMapper implements RowMapper<Object[]>{

		@Override
		public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
			String status = rs.getString("trang_thai"); 
			String respThue = rs.getString("resp_thue"); 
			
			switch (status) {
				case "00":
					status = "00: CHUA KIEM SOAT"; 
					break;
				case "01": {
					if ("01".equals(respThue)) {
						status = "01: DA KIEM SOAT.GUI THUE THANH CONG";
					} else if ("00".equals(respThue)) {
						status = "01: DA KIEM SOAT.GUI KIEM SOAT THUE LOI";
					} else if (null == respThue) {
						status = "01: DA KIEM SOAT.CHUA GUI KIEM SOAT THUE";
					}
					break;
				}
				case "02":
					status = "02: LOI";
					break;
				case "03":
					status = "03: HUY";
					break;
			}
			
			return new Object[]{
						rs.getString("ngay_nthue"),
						rs.getString("so_gcp_tdpa"),
						rs.getString("so_ct"),
						status
			};
		}
		
	}

}
