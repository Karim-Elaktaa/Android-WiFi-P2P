package kelaktaa.android_wifi_p2p;

import android.content.BroadcastReceiver;
import android.net.wifi.p2p.WifiP2pManager;

import java.nio.channels.Channel;

/**
 * Created by Karim on 21/01/2015.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver{
    private WifiP2pManager mManager;
    private Channel mChannel;
    private MainActivity mActivity;

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel, MainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }
}
