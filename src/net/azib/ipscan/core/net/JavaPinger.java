package net.azib.ipscan.core.net;

import net.azib.ipscan.config.ScannerConfig;
import net.azib.ipscan.core.ScanningSubject;

import java.io.IOException;
import java.net.ConnectException;

import static java.lang.System.currentTimeMillis;

public class JavaPinger implements Pinger {
	private int timeout;

	public JavaPinger(ScannerConfig config) {
		this.timeout = config.pingTimeout;
	}

	@Override
	public PingResult ping(ScanningSubject subject, int count) throws IOException {
		PingResult result = new PingResult(subject.getAddress(), count);
		for (int i = 0; i < count; i++) {
			try {
				long start = currentTimeMillis();
				if (subject.getAddress().isReachable(timeout))
					result.addReply(currentTimeMillis() - start);
			}
			catch (ConnectException e) {
				// these happen on Mac
			}
		}
		return result;
	}
}
