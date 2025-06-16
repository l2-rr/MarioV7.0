package com.open.mario;

import java.awt.image.BufferedImage;

//敌人类
public class Enemy  implements Runnable{
			//坐标
			private  int  x,y,startX,startY;
			//类型
			private  int  type;
			//图片
			private  BufferedImage  showImage;
			//食人花的上下限范围
			private  int  downmax,upmax;
			//默认朝向
			private  boolean isUporLeft;
			
			//持有场景对象
			private  BackGround   bg;
			
			//创建有名对象
			Thread  t=new Thread(this);
			
			//普通敌人
			public Enemy(int x, int y, int type,
					boolean isUporLeft,BackGround   bg) {
				super();
				this.bg=bg;
				this.startX=this.x = x;
				this.startY=this.y = y;
				this.type = type;
				this.isUporLeft = isUporLeft;
				//根据type来划分是蘑菇还是乌龟还是其他的
				showImage=StaticValue.allTriangleImage.get(0);
			
				t.start();
				t.suspend();
			}

			
			//食人花
			public Enemy(int x, int y, int type, int downmax, int upmax,
					boolean isUporLeft,BackGround   bg) {
				super();
				this.bg=bg;
				this.startX=this.x = x;
				this.startY=this.y = y;
				this.type = type;
				this.downmax = downmax;
				this.upmax = upmax;
				this.isUporLeft = isUporLeft;
				showImage=StaticValue.allFlowerImage.get(0);
			
				t.start();
				t.suspend();
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

			//定义切换步子
			private  int  imagetype=0;
			public void run() {
			     while(true){
			    	    boolean  canLeft=true;
						boolean  canRight=true;
						//【对集合数组同时进行大批量的增加和删除,建议采用基本for】
						for(int i=0;i<bg.getAllobs().size();i++){
							Obstrcution  obs=bg.getAllobs().get(i);
							//不能向右
							if(x+60 == obs.getX()&& y>=obs.getY()-40
										&& y<=obs.getY()+40){
								canRight=false;
							}
							//不能向左
							if(x-60 == obs.getX()&& y>=obs.getY()-40
										&& y<=obs.getY()+40){
								canLeft=false;
							}
						}
			    	 
			    	 //根据定义type找出食人花和普通敌人
			    	 if(1 == type){
			    		 //普通敌人
			    		
			    		 
			    		 if(imagetype == 0){
			    			 imagetype=1;
			    		 }else{
			    			 imagetype=0;
			    		 }
			    		 //正在朝左走但是不能朝左走
			    		 if(isUporLeft&&!canLeft || x<=0){
			    			 //朝右走
			    			 isUporLeft=false;
			    		 }
			    		 
			    		 //正在朝右走但是不能朝右走
			    		 if(!isUporLeft&&!canRight || x>=840){
			    			 //朝左走
			    			 isUporLeft=true;
			    		 }
			    		 
			    		 if(isUporLeft)
			    			 x-=2;//默认朝左
			    		 else
			    			 x+=2;
			    		 
			    		showImage=StaticValue.allTriangleImage.get(imagetype);
			    	 }
			    	 
			    	 if(type == 2){
			    		 //食人花
			    		
			    		 
			    		 if(imagetype == 0){
			    			 imagetype=1;
			    		 }else{
			    			 imagetype=0;
			    		 }
			    		 
			    		 //正在朝上走但是不能朝上（小于最大值）
			    		 if(isUporLeft&&y<=upmax){
			    			 isUporLeft=false;
			    		 }
			    		 //正在朝下走但是不能朝下(大于最小值)
			    		 if(!isUporLeft&&y>=downmax){
			    			 isUporLeft=true;
			    		 }
			    		 
			    		 if(isUporLeft)
			    			 y-=2;//默认朝上
			    		 else
			    			 y+=2;
			    		 
			    		showImage=StaticValue.allFlowerImage.get(imagetype);
			    	 }
			    	 
			    	 
			    	 try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			     }
			}


			public int getType() {
				// TODO Auto-generated method stub
				return type;
			}


			public void dead() {
				bg.getAllenemys().remove(this);
				bg.getRemoveenemys().add(this);
			}


			public void reset() {
				x=startX;
				y=startY;
			}


			public void resume() {
				t.resume();
			}
			
			
			
			
			
			
}
