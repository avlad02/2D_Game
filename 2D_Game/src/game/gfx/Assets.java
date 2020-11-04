package game.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	private final static int imageWidth = 128;
	private final static int imageHeight = 128;

	public static BufferedImage[][] player;

	private static SpriteSheet aux;

	public static void init() {
		
		player = new BufferedImage[4][4];
		init_entity(4, 4, "/textures/sprite_sheets/hero_walk.png");
		
	}

	private static void init_entity(int sheetWidht, int sheetHeight, String path) {
		
		aux = new SpriteSheet(ImageLoader.loadImage(path));
		
		for (int i = 0; i < sheetWidht; i++) {
			for (int j = 0; j < sheetHeight; j++) {
				player[i][j] = aux.crop(j * imageWidth, i * imageHeight, imageWidth, imageHeight);
			}
		}
	}
}
