package com.xyy.Gazella.exchange;

import com.exchange.android.engine.IExchangeEngineProxy;
import com.exchange.android.engine.UoAction;
import com.exchange.android.engine.Uoi;
import com.exchange.android.engine.Uoo;
import com.exchange.android.engine.data.RemoteActionJSonObjectExchange;
import com.juts.framework.exp.JException;
import com.juts.framework.vo.OVO;
import com.juts.utility.NetUtil;

public class GazellaEngineActionProxy implements IExchangeEngineProxy {

	@Override
	public Uoo action(Uoi uoi, String sRemoteActionUrl, String sEncoding) {
		Uoo uoo = new Uoo();
		try {
			sRemoteActionUrl += uoi.sService;
			System.out.println("请求服务：" + sRemoteActionUrl);
			String sJson = NetUtil.getNetResponse(sRemoteActionUrl, uoi.oForm,
					sEncoding, 30000, 30000);
			RemoteActionJSonObjectExchange raj = new RemoteActionJSonObjectExchange();
			UoAction._LAST_REQUEST_CONTENT = raj.toSTRING(uoi, "utf8");// 最后一次请求的内容
			UoAction._LAST_RESPONSE_CONTENT = sJson;
			OVO ovo = raj.toOVO(sJson);
			uoo.iCode = ovo.iCode;
			uoo.sMsg = ovo.sMsg;
			uoo.sExp = ovo.sExp;
			uoo.oForm = ovo.oForm;
		} catch (JException e) {
			uoo.iCode = e.getCode();
			uoo.sMsg = e.getMessage();
			uoo.sExp = e.getExp();
		} catch (Exception e) {
			uoo.iCode = -100;
			uoo.sMsg = "项目交换接口数据出现异常！";
			uoo.sExp = e.getMessage();
		}
		return uoo;
	}
}
