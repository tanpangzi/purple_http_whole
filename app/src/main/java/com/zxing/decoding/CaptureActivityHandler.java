/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zxing.decoding;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zxing.activity.CaptureActivity;
import com.zxing.camera.CameraManager;
import com.zxing.util.ScanCodeKey;
import com.zxing.view.ViewfinderResultPointCallback;

import java.util.Vector;

/**
 * CaptureActivityHandler类是一个针对扫描任务的Handler，可接收的message有启动扫描（restart_preview）、扫描成功（decode_succeeded）、扫描失败（decode_failed）等等。
 * 
 * <br> Author: 叶青
 * <br> Version: 1.0.0
 * <br> Date: 2016年12月11日
 * <br> Copyright: Copyright © 2016 xTeam Technology. All rights reserved.
 */
public final class CaptureActivityHandler extends Handler {

	private static final String TAG = CaptureActivityHandler.class.getSimpleName();

	private final CaptureActivity activity;
	private final DecodeThread decodeThread;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(CaptureActivity activity, Vector<BarcodeFormat> decodeFormats, String characterSet) {
		this.activity = activity;
		// 1. 启动扫描线程
		decodeThread = new DecodeThread(activity, decodeFormats, characterSet, new ViewfinderResultPointCallback(activity.getViewfinderView()));
		decodeThread.start();
		state = State.SUCCESS;
		// 2. 开启相机预览界面
		CameraManager.get().startPreview();
		// 3. 将preview回调函数与decodeHandler绑定、调用viewfinderView
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		switch (message.what) {
		case ScanCodeKey.SCAN_AUTO_FOCUS:
			// Log.d(TAG, "Got auto-focus message");
			// When one auto focus pass finishes, start another. This is the
			// closest thing to
			// continuous AF. It does seem to hunt a bit, but I'm not sure what
			// else to do.
			if (state == State.PREVIEW) {
				CameraManager.get().requestAutoFocus(this, ScanCodeKey.SCAN_AUTO_FOCUS);
			}
			break;
		case ScanCodeKey.SCAN_RESTART_PREVIEW:
			Log.d(TAG, "Got restart preview message");
			restartPreviewAndDecode();
			break;
		case ScanCodeKey.SCAN_DECODE_SUCCEEDED:
			Log.d(TAG, "Got SCAN_DECODE succeeded message");
			state = State.SUCCESS;
			Bundle bundle = message.getData();

			/***********************************************************************/
			Bitmap barcode = bundle == null ? null : (Bitmap) bundle.getParcelable(DecodeThread.BARCODE_BITMAP);// ���ñ����߳�

			activity.handleDecode((Result) message.obj, barcode);// ���ؽ��??
																	// /***********************************************************************/
			break;
		case ScanCodeKey.SCAN_DECODE_FAILED:
			// We're decoding as fast as possible, so when one SCAN_DECODE fails,
			// start another.
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), ScanCodeKey.SCAN_DECODE);
			break;
		case ScanCodeKey.SCAN_RETURN_SCAN_RESULT:
			Log.d(TAG, "Got return scan result message");
			activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
			activity.finish();
			break;
		case ScanCodeKey.SCAN_LAUNCH_PRODUCT_QUERY:
			Log.d(TAG, "Got product query message");
			String url = (String) message.obj;
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			activity.startActivity(intent);
			break;
		}
	}

	public void quitSynchronously() {
		state = State.DONE;
		CameraManager.get().stopPreview();
		Message quit = Message.obtain(decodeThread.getHandler(), ScanCodeKey.SCAN_QUIT);
		quit.sendToTarget();
		try {
			decodeThread.join();
		} catch (InterruptedException e) {
			// continue
		}

		// Be absolutely sure we don't send any queued up messages
		removeMessages(ScanCodeKey.SCAN_DECODE_SUCCEEDED);
		removeMessages(ScanCodeKey.SCAN_DECODE_FAILED);
	}

	/**
	 * 将preview回调函数与decodeHandler绑定、调用viewfinderView
	 * 
	 * <br> Version: 1.0.0
	 * <br> CreateTime: 2016-1-14,上午10:33:26
	 * <br> UpdateTime: 2016-1-14,上午10:33:26
	 * <br> CreateAuthor: 叶青
	 * <br> UpdateAuthor: 叶青
	 * <br> UpdateInfo: (此处输入修改内容,若无修改可不写.)
	 */
	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), ScanCodeKey.SCAN_DECODE);
			CameraManager.get().requestAutoFocus(this, ScanCodeKey.SCAN_AUTO_FOCUS);
			// 从相册选取图片，取景框没有bitmap
			activity.drawViewfinder();
		}
	}

}