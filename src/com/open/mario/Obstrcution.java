package com.open.mario;

import java.awt.image.BufferedImage;

//障碍物
public class Obstrcution implements Runnable{
		
		private  int  x,y;//坐标
		private  int  type;//类型
		private  BufferedImage  showImage;//图片
		
		//定义保存原来障碍物类型的变量
		private  int startType;
		
		
		//相互持有
		BackGround  bg;
		
		public  int  getType(){
			return type;
		}
		
		public Obstrcution(int x, int y, int type,BackGround  bg) {
			super();
			this.x = x;
			this.y = y;
			this.startType=this.type = type;
			this.bg=bg;
			//根据type确定showImage
			setType(type);
			//如果是旗帜
			if(11 == type)
				new Thread(this).start();
		}
		
		
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public BufferedImage getShowImage() {
			return showImage;
		}

		public void setType(int i) {
			this.type=i;
			showImage=StaticValue.allObstructionImage.get(type);
		}

		public void run() {
			// TODO Auto-generated method stub
			while(true){
				
				//如果场景通知旗帜下落
				if(bg.旗帜下落){
					if(y<420){
						y+=5;
					}
					if(y == 420){
						y=420;
						//降旗完毕
						bg.降旗完毕=true;
					}
				}
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void reset() {
			//setType(startType);
			this.type=startType;
			setType(type);
		}
		
		
}
