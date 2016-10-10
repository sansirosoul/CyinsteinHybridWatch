package com.xyy.Gazella.exchange;

import com.exchange.android.engine.ExchangeProxy;
import com.exchange.android.engine.IExchangeCallBack;
import com.exchange.android.engine.Uoi;
import com.exchange.android.engine.Uoo;
import com.juts.android.FragmentBase;
import com.juts.framework.exp.JException;
import com.juts.framework.vo.DataSet;
import com.juts.framework.vo.Row;
public class ServicesBase {

	public static Uoo connectService(Object context, Uoi uoi, boolean isAsynch) {
		boolean ISDEBUG = false;
		Uoo uoo = null;
		if (ISDEBUG) {// 调试模式
			uoo = connectTestData(context, uoi);
			IExchangeCallBack iec = (IExchangeCallBack) context;
			iec.callbackByExchange(uoi, uoo);
		} else {
			ExchangeProxy ep = new ExchangeProxy(uoi);
//			if (context instanceof BaseFragment) {
//				BaseFragment bd = (BaseFragment) context;
//				ep.setContext(bd.getActivity());
//				ep.setHandler(bd.handler);
//			} else {
				ep.setContext(context);
//		    }
			ep.setAsynchronous(isAsynch);
			uoo = ep.excute();
		}
		return uoo;
	}

	public static Uoo connectTestData(Object context, Uoi uoi) {
		Uoo uoo = new Uoo();
		try {
			if (uoi.sService.equals("getLevels")) {
				DataSet data = new DataSet();
				Row row = new Row();
				row.put("LEVEL_ID", "1");
				row.put("LEVEL_NAME", "金卡");
				row.put("MIN_SCORE", "1000");
				row.put("MAX_SCORE", "10000");
				row.put("DISCOUNT_RATE", "0.7");
				row.put("SPENDING", "10");
				row.put("INTEGRAL", "30");
				data.add(row);
				Row row1 = new Row();
				row1.put("LEVEL_ID", "2");
				row1.put("LEVEL_NAME", "银卡");
				row1.put("MIN_SCORE", "100");
				row1.put("MAX_SCORE", "1000");
				row1.put("DISCOUNT_RATE", "0.8");
				row1.put("SPENDING", "10");
				row1.put("INTEGRAL", "20");
				data.add(row1);
				Row row2 = new Row();
				row2.put("LEVEL_ID", "3");
				row2.put("LEVEL_NAME", "铜卡");
				row2.put("MIN_SCORE", "10");
				row2.put("MAX_SCORE", "100");
				row2.put("DISCOUNT_RATE", "0.9");
				row2.put("SPENDING", "10");
				row2.put("INTEGRAL", "10");
				data.add(row2);
				uoo.set("level", data);
			} else if (uoi.sService.equals("/user!getUser.action")) {
				DataSet ds = new DataSet();
				Row row = new Row();
				row.put("service", "/user!getUser.action");
				row.put("code", 0);

				row.put("rankImgPath", "/api/demo/images/");
				row.put("zanTotal", 34);
				row.put("creditTotal", 20);
				row.put("giftTotal", 20);
				row.put("notesTotal", -1);
				row.put("subTotal", -1);
				row.put("contactsTotal", -1);
				row.put("grownTotal", -1);
				row.put("questionTotal", -1);
				row.put("collectTotal", -1);
				ds.add(row);
				uoo.set("user", ds);
			}
		} catch (JException e) {
			e.printStackTrace();
		}
		return uoo;
	}
}
