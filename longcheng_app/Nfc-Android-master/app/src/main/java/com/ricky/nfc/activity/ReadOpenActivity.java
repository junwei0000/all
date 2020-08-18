package com.ricky.nfc.activity;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ricky.nfc.R;
import com.ricky.nfc.base.BaseNfcActivity;

import java.util.Calendar;


public class ReadOpenActivity extends BaseNfcActivity {
    TextView nfcContentTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_read);
        nfcContentTv = findViewById(R.id.tv_nfc_read_content);
        resolveIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        resolveIntent(intent);
    }

    /**
     * --------------------------------------读取卡片ID--------------------------------------
     *
     * @param intent
     */
    private void disposeIntent(Intent intent) {
        String cardId = getCardId(intent);
        if (cardId != null) {
            nfcContentTv.setText(String.format("NFC ID:%s", cardId));
        } else {
            Toast.makeText(this, "未读取到卡ID", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCardId(Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] bytesId = tagFromIntent.getId();// 获取id数组
        return byteArrayToHexString(bytesId);
    }

    private static String byteArrayToHexString(byte[] bytesId) {   //Byte数组转换为16进制字符串
        int i, j, in;
        String[] hex = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String output = "";
        for (j = 0; j < bytesId.length; ++j) {
            in = bytesId[j] & 0xff;
            i = (in >> 4) & 0x0f;
            output += hex[i];
            i = in & 0x0f;
            output += hex[i];
        }
        return output;
    }


    /**
     * --------------------------------------读取传递的Payload 信息--------------------------------------
     *
     * @param intent
     */
    //初次判断是什么类型的NFC卡
    private void resolveIntent(Intent intent) {
        NdefMessage[] msgs = getNdefMsg(intent); //重点功能，解析nfc标签中的数据
        if (msgs == null) {
            Toast.makeText(this, "非NFC启动", Toast.LENGTH_SHORT).show();
        } else {
            setNFCMsgView(msgs);
        }

    }

    /**
     * 显示扫描后的信息
     *
     * @param ndefMessages ndef数据
     */
    private void setNFCMsgView(NdefMessage[] ndefMessages) {
        if (ndefMessages == null || ndefMessages.length == 0) {
            return;
        }
        String Payload = new String(ndefMessages[0].getRecords()[0].getPayload());
        nfcContentTv.setText("读取NFC写入的数据:\n" + Payload);
    }

    //初次判断是什么类型的NFC卡
    public static NdefMessage[] getNdefMsg(Intent intent) {
        if (intent == null)
            return null;
        //nfc卡支持的格式
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String[] temp = tag.getTechList();
        for (String s : temp) {
            Log.i("getNdefMsg", "resolveIntent tag: " + s);
        }
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Parcelable[] rawMessage = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] ndefMessages = null;
            // 判断是哪种类型的数据 默认为NDEF格式
            if (rawMessage != null) {
                Log.i("getNdefMsg", "getNdefMsg: ndef格式 ");
                ndefMessages = new NdefMessage[rawMessage.length];
                for (int i = 0; i < rawMessage.length; i++) {
                    ndefMessages[i] = (NdefMessage) rawMessage[i];
                }
            } else {
                //未知类型 (公交卡类型)
                Log.i("getNdefMsg", "getNdefMsg: 未知类型");
                //对应的解析操作，在Github上有
            }
            return ndefMessages;
        }
        return null;
    }
}
