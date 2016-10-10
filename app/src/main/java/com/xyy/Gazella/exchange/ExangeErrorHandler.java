package com.xyy.Gazella.exchange;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.exchange.android.engine.IErrorHandle;
import com.juts.framework.vo.OVO;

public class ExangeErrorHandler implements IErrorHandle {
	private Context context;

	@Override
	public void handle(Context context, OVO uoo) {
		this.context = context;
		Message msg = new Message();
		msg.obj = uoo.iCode + ":" + uoo.sMsg;
		mHandler.sendMessage(msg);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//ToastUtils.showTextToast(context, String.valueOf(msg.obj));
		}
	};
}
