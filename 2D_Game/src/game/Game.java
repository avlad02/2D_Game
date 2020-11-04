package game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import game.display.Display;
import game.gfx.ImageLoader;
import game.gfx.Assets;

public class Game implements Runnable {

	private Display display;
	public int width, height;
	public String title;

	private Thread thread;
	private boolean running;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private final int fpsTarget = 60;

	private BufferedImage test;

	public Game(String title, int width, int height) {

		this.title = title;
		this.width = width;
		this.height = height;

	}

	private void init() {
		display = new Display(title, width, height);
		test = ImageLoader.loadImage("/textures/states/map2.bmp");
		Assets.init();
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
		

		g.drawImage(Assets.player[3][3],0 , 0 , null);

		
		bs.show();
		g.dispose();
	}

	public void run() {

		init();

		double timePerFrame = 1000000000 / fpsTarget;
		double deltaTime = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int fpsActual = 0;
		
		while (running) {
			
			now = System.nanoTime();
			deltaTime += (now - lastTime) / timePerFrame;
			timer += now - lastTime;
			lastTime = now;
			
			if(deltaTime >= 1) {
				frameSetup();
				render();
				fpsActual++;
				deltaTime--;
			}
			
			if(timer >= 1000000000) {
				System.out.println(fpsActual);
				fpsActual = 0;
				timer = 0;
			}

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
