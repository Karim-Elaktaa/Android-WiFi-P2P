package kelaktaa.android_wifi_p2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.util.Log;

import java.util.Collection;
import java.util.Iterator;
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
    WifiP2pConfig config = new WifiP2pConfig();
    private boolean connected = false;

    public WiFiDirectBroadcastReceiver() {
        super();
    }
    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel, MainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;

        myPeerListListener = new PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                Collection<WifiP2pDevice> deviceList = peers.getDeviceList();
                Log.d(DEBUG_TAG, "PeerListListener "+ deviceList.size());
                Iterator<WifiP2pDevice> iterator = deviceList.iterator();
                while(iterator.hasNext()) {
                    WifiP2pDevice current = iterator.next();
                    Log.d("DEVICE deviceAddress", current.deviceAddress);
                    Log.d("DEVICE deviceName", current.deviceName);
                    Log.d("DEVICE primaryDeviceType", current.primaryDeviceType);

                    //connect
                    if(!connected) {
                        config.deviceAddress = current.deviceAddress;
                        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                            @Override
                            public void onSuccess() {
                                Log.d("CONNECT", "SUCCESS");
                                connected = true;
                            }

                            @Override
                            public void onFailure(int reason) {
                                Log.d("CONNECT", "FAIL");
                            }
                        });
                    }
                }
            }
        };
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(DEBUG_TAG, "============ "+action);

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            //Log.d(DEBUG_TAG, "WIFI_P2P_STATE_CHANGED_ACTION");
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            Log.d(DEBUG_TAG, "+++++++++++ WIFI_P2P_PEERS_CHANGED_ACTION");
            if(mManager != null) {
                mManager.requestPeers(mChannel, myPeerListListener);
            }
            else {
                Log.d(DEBUG_TAG, "MANAGER NULL");
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            //Log.d(DEBUG_TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION");
            //get ip address of GO
            

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            //Log.d(DEBUG_TAG, "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");
        }
    }
}
