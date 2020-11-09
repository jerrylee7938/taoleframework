package com.taole.member.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.taole.member.manager.RptSalesByGoodsManager;
import com.taole.member.manager.RptSalesByPayTypeManager;
import com.taole.member.manager.RptSalesByTradeTypeManager;

@Service
public class ReportStatJob {

	@Autowired
	private RptSalesByGoodsManager rptSalesByGoodsManager;
	
	@Autowired
	private RptSalesByTradeTypeManager rptSalesByTradeTypeManager;
	
	@Autowired
	private RptSalesByPayTypeManager rptSalesByPayTypeManager;

	@Scheduled(cron = "0 10 0 * * ?")
	public void execute() throws Exception {
		System.out.println("=====statics start...=====");
		rptSalesByGoodsManager.statSalesByGoods(null, 0, 10);
		
		rptSalesByTradeTypeManager.statSalesByBillType(null, 0, 10);
		
		rptSalesByPayTypeManager.statSalesByPayType(null, 0, 10);
		System.out.println("=====statics end...=====");
	}
}
