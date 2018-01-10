package com.KiwiSports.control.view.mapsynth;

import java.io.IOException;

import com.KiwiSports.control.view.MyApplication;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import android.app.Activity;
import android.util.Log;

public class MySpeechSynthesizer {

	public SpeechSynthesizer mSpeechSynthesizer;
	Activity mActivity;

	public MySpeechSynthesizer(Activity mActivity) {
		super();
		this.mActivity = mActivity;
		initSpeech();
	}
	 protected OfflineResource createOfflineResource(String voiceType) {
	        OfflineResource offlineResource = null;
	        try {
	            offlineResource = new OfflineResource(mActivity, voiceType);
	        } catch (IOException e) {
	            // IO 错误自行处理
	            e.printStackTrace();
	        }
	        return offlineResource;
	    }
	private void initSpeech() {
		this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
		this.mSpeechSynthesizer.setContext(mActivity);
		// 离线资源文件
		// 离线发音选择，VOICE_FEMALE即为离线女声发音。
	    // assets目录下bd_etts_speech_female.data为离线男声模型；bd_etts_speech_female.data为离线女声模型
	      String offlineVoice = OfflineResource.VOICE_FEMALE;
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                offlineResource.getModelFilename());
		// 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
		this.mSpeechSynthesizer.setAppId("10456864");
		// 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
		this.mSpeechSynthesizer.setApiKey("nPt2q3GqGF0kZOB2zEluovWQ1ku4mM5G", "P9tOMOGPNmxd6hGEWwjaP9e9N4SGSKxv");
		// 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
		this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
		// 设置Mix模式的合成策略
		this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
		// 设置音量
		this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
		// 初始化tts
		this.mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {

			@Override
			public void onSynthesizeStart(String arg0) {
				// TODO Auto-generated method stub
				Log.e("synth", arg0);
			}

			@Override
			public void onSynthesizeFinish(String arg0) {
				// TODO Auto-generated method stub
				Log.e("synth", arg0);
			}

			@Override
			public void onSynthesizeDataArrived(String arg0, byte[] arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.e("synth", arg0);
			}

			@Override
			public void onSpeechStart(String arg0) {
				// TODO Auto-generated method stub
				Log.e("synth", arg0);
			}

			@Override
			public void onSpeechProgressChanged(String arg0, int arg1) {
				// TODO Auto-generated method stub
				Log.e("synth", arg0);
			}

			@Override
			public void onSpeechFinish(String arg0) {
				// TODO Auto-generated method stub
				Log.e("synth", arg0);
			}

			@Override
			public void onError(String arg0, SpeechError arg1) {
				// TODO Auto-generated method stub
				Log.e("synth", arg0);

			}
		});
		int result = mSpeechSynthesizer.initTts(TtsMode.MIX);
		if(result!=0){
			Log.e("synth", "result="+result);
		}
	}

	public void speak(String text){
		if(mSpeechSynthesizer!=null)
		mSpeechSynthesizer.speak(text);
	}
	public void onDestroy() {
		this.mSpeechSynthesizer.release();
	}
}
