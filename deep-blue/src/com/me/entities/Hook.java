package com.me.entities;

import com.badlogic.gdx.math.Rectangle;
import com.me.deepblue.Images;

public class Hook extends SeaObjects{
	
	public boolean hooked;
	
	public Hook(){
		image = Images.hook_sprite;
		hooked = false;
		x = (float) (100 + (Math.random() * 1200)) + 1200;
		y = (float) (Math.random() * -300) - 200;
		boundingBox = new Rectangle (this.x, y, image.getWidth(), image.getHeight());
	}
	
	public void reset(float cameraX){
		if(x < (cameraX-600) || x > (cameraX+600)){
			hooked = false;
			x = cameraX + 600 + (float) (100 + (Math.random() * 1200));
			y = (float) (Math.random() * -300) - 200;
			boundingBox.x = x;
			boundingBox.y = y;
		}
	}
	
	public void pullUp() {
		y -= 10;
		boundingBox.y -= 10;
	}
}
