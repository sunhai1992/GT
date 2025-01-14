/*
 * Tencent is pleased to support the open source community by making
 * Tencent GT (Version 2.4 and subsequent versions) available.
 *
 * Notwithstanding anything to the contrary herein, any previous version
 * of Tencent GT shall not be subject to the license hereunder.
 * All right, title, and interest, including all intellectual property rights,
 * in and to the previous version of Tencent GT (including any and all copies thereof)
 * shall be owned and retained by Tencent and subject to the license under the
 * Tencent GT End User License Agreement (http://gt.qq.com/wp-content/EULA_EN.html).
 * 
 * Copyright (C) 2015 THL A29 Limited, a Tencent company. All rights reserved.
 * 
 * Licensed under the MIT License (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://opensource.org/licenses/MIT
 * 
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.tencent.wstt.gt.utils;

import com.tencent.wstt.gt.GTApp;

import android.content.Intent;
import oicq.wlogin_sdk.request.Ticket;
import oicq.wlogin_sdk.request.WUserSigInfo;
import oicq.wlogin_sdk.request.WtloginHelper;
import oicq.wlogin_sdk.request.WtloginHelper.SigType;

/**
 * Wtlogin登录的简单封装
 */
public class WtloginUtil {
	private static WtloginHelper mLoginHelper = null; // 在app启动时初始化
	private static String uin = null;

	public final static int REQ_QLOGIN = 0x100; // 快速登录 requestCode
	public final static int REQ_VCODE = 0x2; // 验证码

	private static final long appid = 1600000371;
	private static final int subid = 0x1;//369;369快速登录不好用啊
	/*
	 * 待获取业务票据置位表，根据需要填写需要获取的票据 具体支持的票据请查看 SigType 类中对应的常量定义
	 */
	public static int mMainSigMap = SigType.WLOGIN_PSKEY | SigType.WLOGIN_LSKEY;

	public static WtloginHelper getHelper()
	{
		if (mLoginHelper == null)
		{
			mLoginHelper = new WtloginHelper(GTApp.getContext());
		}
		return mLoginHelper;
	}

	public static String getUin() {
		return uin;
	}

	public static void setUin(String uin) {
		WtloginUtil.uin = uin;
	}

	public static String getSKey(String uin) {
		if (uin == null) return null;
		Ticket ticketLsk = WtloginUtil.getHelper().GetLocalTicket(
				uin, appid, SigType.WLOGIN_SKEY);
		return new String(ticketLsk._sig);
	}

	public static String getPsKey(String uin) {
		if (uin == null) return null;
		Ticket ticketLsk = WtloginUtil.getHelper().GetLocalTicket(
				uin, appid, SigType.WLOGIN_PSKEY);
		return new String(ticketLsk._sig);
	}

	public static String getLsKey(String uin) {
		if (uin == null) return null;
		Ticket ticketLsk = WtloginUtil.getHelper().GetLocalTicket(
				uin, appid, SigType.WLOGIN_LSKEY);
		return new String(ticketLsk._sig);
	}

	public static Intent getIntent()
	{
		return WtloginUtil.getHelper().PrepareQloginIntent(appid, subid, "1");
	}

	public static WUserSigInfo getSigInfo(Intent data)
	{
		return getHelper().ResolveQloginIntent(data);
	}

	public static void getStWithPasswd(WUserSigInfo sigInfo)
	{
		getHelper().GetStWithPasswd(sigInfo.uin, appid, subid, mMainSigMap, "", sigInfo);
	}
}
