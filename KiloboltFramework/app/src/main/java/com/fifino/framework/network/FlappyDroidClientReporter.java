//package com.fifino.framework.network;
//import com.fifino.flappydroid.FlappyDroidGame;
//
//import android.util.Log;
//import fifis.network.client.ClientReporter;
//
//public class FlappyDroidClientReporter implements ClientReporter {
//
//	@Override
//	public void clientReport(String arg0) {
//		System.out.println(arg0);
//		Log.i("FlappyDroidClientReporter", "Server Message: " + arg0 );
//		FlappyDroidGame.HIGH_SCORE = Integer.parseInt(arg0);
//	}
//
//	@Override
//	public void clientReportError(String arg0) {
//		System.out.println(arg0);
//		Log.i("FlappyDroidClientReporter", "Server error: " + arg0 );
//	}
//
//}
