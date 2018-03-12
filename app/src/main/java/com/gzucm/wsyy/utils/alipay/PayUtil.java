//package com.gzucm.wsyy.utils.alipay;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.widget.Toast;
//
//import com.alipay.sdk.app.PayTask;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//@SuppressLint("HandlerLeak")
//public class PayUtil {
//	private Activity mActivity;
//	private final int payMessageWhat = 1;
//	private Handler mHandler;
//	private PayListener mPayListener;
//
//	public void setPayListener(PayListener payListener) {
//		this.mPayListener = payListener;
//	}
//
//	public interface PayListener {
//		void paySuccess();
//
//		void payFail();
//	}
//
//	public PayUtil(Activity activity) {
//		this.mHandler = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//				switch (msg.what) {
//				case payMessageWhat:
//					Result resultObj = new Result((String) msg.obj);
//					String resultStatus = resultObj.resultStatus;
//					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//					if (TextUtils.equals(resultStatus, "9000")) {
//						if (mPayListener != null) {
//							mPayListener.paySuccess();
//						}
//					} else {
//						// 判断resultStatus 为非“9000”则代表可能支付失败
//						// “8000”
//						// 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//						if (TextUtils.equals(resultStatus, "8000")) {
//							Toast.makeText(mActivity, "支付结果确认中",
//									Toast.LENGTH_SHORT).show();
//						} else {
//							if (mPayListener != null) {
//								mPayListener.payFail();
//							}
//						}
//					}
//					break;
//				}
//			}
//		};
//		this.mActivity = activity;
//	}
//
//	/**
//	 * 调用支付宝的接口去支付
//	 */
//	public void alipay(String goodsName, String goodsNumber, String order_id,
//			String allPrice) {
//		String orderInfo = getOrderInfo(goodsName, "购买数量：" + goodsNumber,
//				allPrice, order_id);
//
//		String sign = sign(orderInfo);
//		// 仅需对sign 做URL编码
//		try {
//			sign = URLEncoder.encode(sign, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
//				+ getSignType();
//		Runnable payRunnable = new Runnable() {
//			@Override
//			public void run() {
//				// 构造PayTask 对象
//				PayTask alipay = new PayTask(mActivity);
//				// 调用支付接口
//				String result = alipay.pay(payInfo);
//				Message msg = new Message();
//				msg.what = payMessageWhat;
//				msg.obj = result;
//				mHandler.sendMessage(msg);
//			}
//		};
//		Thread payThread = new Thread(payRunnable);
//		payThread.start();
//	}
//
//	/**
//	 * 调用支付宝的接口去支付
//	 */
//	public void alipay(final String payInfo) {
//		Runnable payRunnable = new Runnable() {
//			@Override
//			public void run() {
//				// 构造PayTask 对象
//				PayTask alipay = new PayTask(mActivity);
//				// 调用支付接口
//				String result = alipay.pay(payInfo);
//				Message msg = new Message();
//				msg.what = payMessageWhat;
//				msg.obj = result;
//				mHandler.sendMessage(msg);
//			}
//		};
//		Thread payThread = new Thread(payRunnable);
//		payThread.start();
//	}
//
//	/**
//	 * 创建订单信息
//	 */
//	public String getOrderInfo(String subject, String body, String price,
//			String order_id) {
//		// 合作者身份ID
//		String orderInfo = "partner=" + "\"" + ConstValue.PARTNER + "\"";
//
//		// 卖家支付宝账号
//		orderInfo += "&seller_id=" + "\"" + ConstValue.SELLER + "\"";
//
//		// 商户网站唯一订单号
//		orderInfo += "&out_trade_no=" + "\"" + order_id + "\"";
//
//		// 商品名称
//		orderInfo += "&subject=" + "\"" + subject + "\"";
//
//		// 商品详情
//		orderInfo += "&body=" + "\"" + body + "\"";
//
//		// 商品金额
//		orderInfo += "&total_fee=" + "\"" + price + "\"";
//
//		orderInfo += "&app_id=" + "\"1\"";
//
//		// 服务器异步通知页面路径
//		orderInfo += "&notify_url=" + "\"" + "http://api.ffu365.com/index.php?m=Api&c=Payment&a=alipayGateway"
//				+ "\"";
//
//		// 接口名称， 固定值
//		orderInfo += "&service=\"mobile.securitypay.pay\"";
//
//		// 支付类型， 固定值
//		orderInfo += "&payment_type=\"1\"";
//
//		// 参数编码， 固定值
//		orderInfo += "&_input_charset=\"utf-8\"";
//
//		// 设置未付款交易的超时时间
//		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
//		// 取值范围：1m～15d。
//		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
//		// 该参数数值不接受小数点，如1.5h，可转换为90m。
//		orderInfo += "&it_b_pay=\"15d\"";
//
//		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		//orderInfo += "&return_url=\"m.alipay.com\"";
//
//		// 调用银行卡支付，需配置此参数，参与签名， 固定值
//		// orderInfo += "&paymethod=\"expressGateway\"";
//
//		return orderInfo;
//	}
//
//	/**
//	 * 对订单信息进行签名 待签名订单信息
//	 */
//	public String sign(String content) {
//		return SignUtils.sign(content, ConstValue.RSA_PRIVATE);
//	}
//
//	/**
//	 * get the sign type we use. 获取签名方式
//	 *
//	 */
//	public String getSignType() {
//		return "sign_type=\"RSA\"";
//	}
//}
