package game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import game.display.Display;

public class Game implements Runnable {

	private Display display;
	public int width, height;
	public String title;

	private Thread thread;
	private boolean running;
	
	private BufferStrategy bs;
	private Graphics g;

	public Game(String title, int width, int height) {

		this.title = title;
		this.width = width;
		this.height = height;

	}

	private void init() {
		display = new Display(title, width, height);
	}

	private void frameSetup() {

	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear
		g.clearRect(0, 0, width, height);
		//Draw
		
		
		
		bs.show();
		g.dispose();
	}

	public void run() {

		init();

		while (running) {
			frameSetup();
			render();
		}

		stop();

	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		if (!running)
			return;
		try {
			running = false;
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
