package com.singh.nilesh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class Supermario extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] man;
	int manstate=0;
	int gameState=0;
	float velocity;
	float manY;
	float gravity = 0.2f;
	int pause=0;
	Random random;

	ArrayList<Integer> coinX=new ArrayList<>();
	ArrayList<Integer> coinY=new ArrayList<>();
	int coincount=0;
	Texture coin;
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		man=new Texture[4];
		man[0]=new Texture("frame-1.png");
		man[1]=new Texture("frame-2.png");
		man[2]=new Texture("frame-3.png");
		man[3]=new Texture("frame-4.png");
		manY=Gdx.graphics.getHeight()/2-man[0].getHeight()/2;

		coin=new Texture("coin.png");
		random=new Random();
	}

	public  void  makeCoin()
	{
     float height=  random.nextFloat() *Gdx.graphics.getHeight();
     coinY.add((int)height);
     coinX.add(Gdx.graphics.getWidth());
	}
	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(coincount<100){
			coincount++;
			/*Gdx.app.log("sdsdf",String.valueOf(coincount));*/
		}else{
			coincount=0;
			makeCoin();
			/*Gdx.app.log("sdsdf","haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");*/
		}
		for(int i=0;i<coinX.size();i++){
			batch.draw(coin,coinX.get(i),coinY.get(i));
			coinX.set(i,coinX.get(i)-4);
		}
        if(pause<8)
        	pause++;
        else {
			pause = 0;
			if (manstate < 3)
				manstate++;
			else
				manstate = 0;
		}
		if(gameState==0) {
			if (Gdx.input.justTouched())
				gameState = 1;
		}
		else if(gameState==1){
			if (Gdx.input.justTouched())
			velocity=-10;

			velocity = velocity + gravity;
			manY -= velocity;
			if(manY<=0 ) {
				manY=0;
			}/*else{
			gameState=2;
		}*/
		}

		batch.draw(man[manstate], Gdx.graphics.getWidth()/2-man[manstate].getWidth()/2,manY);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		/*background.dispose();*/
	}
}
