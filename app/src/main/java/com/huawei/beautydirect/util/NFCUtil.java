package com.huawei.beautydirect.util;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.util.Log;

import java.io.IOException;

import static com.huawei.beautydirect.util.Constants.PROJECT_TAG;

public class NFCUtil {

    private static final String TAG = PROJECT_TAG + "NFCUtil";

    private static Tag sTag;

    /**
     * 从NFC标签中读取payload数据
     *
     * @param intent
     * @return
     */
    public static byte[] readData(Intent intent) {
        if (intent.getAction() != NfcAdapter.ACTION_NDEF_DISCOVERED) {
            Log.w(TAG, "readData: no ACTION_NDEF_DISCOVERED");
            return null;
        }
        sTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (sTag == null) {
            Log.w(TAG, "readData: no EXTRA_TAG");
            return null;
        }
        Ndef ndef = Ndef.get(sTag);
        try {
            NdefMessage ndefMessage = ndef.getNdefMessage();
            NdefRecord ndefRecord = ndefMessage.getRecords()[0];
            return ndefRecord.getPayload();
        } catch (FormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
