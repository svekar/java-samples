package org.example.timer;

import java.util.TimerTask;

public final class SimpleTimerTask extends TimerTask {

	@Override
	public void run() {
		System.out.println(this + ": " + System.currentTimeMillis());
	}

}
