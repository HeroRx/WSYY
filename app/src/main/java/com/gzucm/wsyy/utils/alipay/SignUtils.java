//package com.gzucm.wsyy.utils.alipay;
//
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.spec.PKCS8EncodedKeySpec;
//
///**
// *
// * ============================================================
// *
// * 版权 ：好莱信息科技有限公司 (c) 2014
// *
// * 作者 : mowen
// *
// * 版本 ：1.0
// *
// * 创建日期 ： 2014年12月9日 下午4:21:44
// *
// * 描述 ：支付宝
// *
// * 修订历史 ：
// *
// * ============================================================
// *
// */
//public class SignUtils {
//
//	private static final String ALGORITHM = "RSA";
//
//	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
//
//	private static final String DEFAULT_CHARSET = "UTF-8";
//
//	public static String sign(String content, String privateKey) {
//		try {
//
//			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
//					Base64.decode(privateKey));
//			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
//			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
//
//			java.security.Signature signature = java.security.Signature
//					.getInstance(SIGN_ALGORITHMS);
//
//			signature.initSign(priKey);
//			signature.update(content.getBytes(DEFAULT_CHARSET));
//			byte[] signed = signature.sign();
//			return Base64.encode(signed);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//}
