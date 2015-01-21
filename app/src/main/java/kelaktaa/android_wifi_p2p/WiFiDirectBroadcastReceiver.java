package kelaktaa.android_wifi_p2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.util.Log;
import java.util.List;

/**
 * Created by Karim on 21/01/2015.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver{
    private WifiP2pManager mManager;
    private Channel mChannel;
    private MainActivity mActivity;
    final private static String DEBUG_TAG = "WiFiDirectBroadcastReceiver";
    private PeerListListener myPeerListListener;
    private WifiP2pDeviceList list;

    public WiFiDirectBroadcastReceiver() {
        super();
    }
    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel, MainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(DEBUG_TAG, "============ "+action);

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            Log.d(DEBUG_TAG, "WIFI_P2P_STATE_CHANGED_ACTION");
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Log.d(DEBUG_TAG, "+++++++++++ WIFI_P2P_PEERS_CHANGED_ACTION");
            if(mManager != null) {
                mManager.requestPeers(mChannel, myPeerListListener);
                myPeerListListener.onPeersAvailable(list);
                //Log.d(DEBUG_TAG, "LISTE => "+ list.toString() + list.getDeviceList().size());
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.d(DEBUG_TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION");
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            Log.d(DEBUG_TAG, "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");
        }
    }
}
