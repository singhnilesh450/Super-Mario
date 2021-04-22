package com.singh.nilesh;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

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
	Rectangle manRectangle;
	int score=0;
	BitmapFont font;

	ArrayList<Integer> coinX=new ArrayList<>();
	ArrayList<Rectangle> coinRectangle=new ArrayList<>();
	ArrayList<Integer> coinY=new ArrayList<>();
	int coincount=0;
	Texture coin;

	ArrayList<Integer> bombX=new ArrayList<>();
	ArrayList<Rectangle> bombRectangle=new ArrayList<>();
	ArrayList<Integer> bombY=new ArrayList<>();
	int bombcount=0;
	Texture bomb;
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
		bomb=new Texture("bomb.png");
		random=new Random();
		manRectangle=new Rectangle();
		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
	}

	public  void  makeCoin()
	{
     float height=  random.nextFloat() *Gdx.graphics.getHeight();
     coinY.add((int)height);
     coinX.add(Gdx.graphics.getWidth());
	}

	public  void  makeBomb()
	{
		float height=  random.nextFloat() *Gdx.graphics.getHeight();
		bombY.add((int)height);
		bombX.add(Gdx.graphics.getWidth());
	}
	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(bombcount<250){
			bombcount++;
			/*Gdx.app.log("sdsdf",String.valueOf(coincount));*/
		}else{
			bombcount=0;
			makeBomb();
			/*Gdx.app.log("sdsdf","haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");*/
		}
		bombRectangle.clear();
		for(int i=0;i<bombX.size();i++){
			batch.draw(bomb,bombX.get(i),bombY.get(i));
			bombX.set(i,bombX.get(i)-8);
			bombRectangle.add(new Rectangle(bombX.get(i),bombY.get(i),bomb.getWidth(),bomb.getHeight()));
		}



		if(coincount<100){
			coincount++;
			/*Gdx.app.log("sdsdf",String.valueOf(coincount));*/
		}else{
			coincount=0;
			makeCoin();
			/*Gdx.app.log("sdsdf","haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");*/
		}
		coinRectangle.clear();
		for(int i=0;i<coinX.size();i++){
			batch.draw(coin,coinX.get(i),coinY.get(i));
			coinX.set(i,coinX.get(i)-4);
			coinRectangle.add(new Rectangle(coinX.get(i),coinY.get(i),coin.getWidth(),coin.getHeight()));
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
        manRectangle.set(Gdx.graphics.getWidth()/2-man[manstate].getWidth()/2,manY,man[manstate].getWidth(),man[manstate].getHeight());

        for(int i=0;i<coinRectangle.size();i++){
        	if(Intersector.overlaps(manRectangle,coinRectangle.get(i))){
             score++;

             coinRectangle.remove(i);
             coinX.remove(i);
             coinY.remove(i);
             break;
			}
		}

		for(int i=0;i<bombRectangle.size();i++){
			if(Intersector.overlaps(manRectangle,bombRectangle.get(i))){

			}
		}

		font.draw(batch,String.valueOf(score),100,200);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		/*background.dispose();*/
	}
}
