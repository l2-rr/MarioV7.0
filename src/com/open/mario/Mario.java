package com.open.mario;

import java.awt.image.BufferedImage;

//主人公
public class Mario implements Runnable{

		private int  x,y;//坐标
		private BufferedImage  showImage;//图片
		//横向移动的偏移量
		private  int  xmove;
		//纵向移动的偏移量
		private  int  ymove;
		
		//表示图片的字符串变量
		private String status;
		//持有场景
		private BackGround  bg;
		
		//定义Mario存活的变量(假设只有1条命)
		public  boolean  live;
		//定义Mario设置3条命
		public  int   life=3;
		
		public  void  setBg(BackGround  bg){
			this.bg=bg;
		}
		
		public Mario(int  x,int y){
			//默认是活的
			this.live=true;
			
			this.x=x;
			this.y=y;
			//指定第一张右站立的图片1.gif
			showImage=StaticValue.allMarioImage.get(0);
			//指定默认的状态"right-standing"
			status="right-standing";
			
			//开启线程完成mario的横向移动
			new Thread(this).start();
		}

		//定义出Mario横向移动的四个基本方法
		public  void  leftstop(){
			xmove=0;
			//初始化
			status="left-standing";
		}
		public  void  leftmove(){
			xmove=-5;
			status="left-moving";
		}
		public  void  rightstop(){
			xmove=0;
			status="right-standing";
		}
		public  void  rightmove(){
			xmove=5;
			status="right-moving";
		}
		
		//Mario纵向移动的方法
		//上升
		//定义上升的时间
		int  uptime=0;//默认没有上升
		public  void  jump(){
			if(status.indexOf("left")!=-1){
				//Mario打算从左边起跳
				status="left-jumping";
			}else{
				//Mario打算从右边起跳
				status="right-jumping";
			}
			ymove=-5;//ymove认为是速度0.1s(1上升50)
			//求出上升的时间t=s/v(Math.abs())
			//uptime从起始点给定上升的时间
			//但是随着线程的刷新我们程序员人为减少uptime
			//时间,直到uptime=0说明上升结束
			uptime=180/5;
		}
		
		//下降的方法(不需要给定下降的时间)
		public  void   down(){
			if(status.indexOf("left")!=-1){
				//PS:上升和下降对应的图片是一致的
				status="left-jumping";
			}else{
				status="right-jumping";
			}
			ymove=5;
		}
		
		
		
		//独立线程Thread-A
		//定义：朝一个方向走的步子(0-1逐个逐个走)
		private  int  moving=0;
		public void run() {
			//不断监听玩家操作键盘-->xmove
			while(true){
				//处理Mario游戏通过过程
				if(x>520 && bg.isFlag()){
					//游戏正在通关
					//1.改变状态
					status="right-jumping";
					//2.mario下落到砖块上面
					if(y<420){
						y+=5;
					}
					if(y==420){
						y=420;
						status="right-standing";
					//3.通知旗帜开始下落
						bg.旗帜下落=true;
					}
					
					//4.当降旗完毕后，再来通关
					if(bg.降旗完毕){
						if(x<580){
							x+=5;
							status="right-moving";
						}
						if(x == 580){
							if(y<480){
								y+=5;
								status="right-jumping";
							}
							if(y == 480){
								//落到地面
								status="right-standing";
							}
						}
						
						if(y == 480){
							//朝右走
							if(x<800){
								x+=10;
								status="right-moving";
							}
							if(x == 750){
								//游戏通关结束
								bg.通关=true;
							}
						}
					}
					
				}else{
					//游戏还在继续
				//处理Mario遇到敌人
				for(int i=0;i<bg.getAllenemys().size();i++){
					 Enemy  e=bg.getAllenemys().get(i);
					 /*
					  * 1.通过类型在区分横向和纵向
					  * 2.通过横向和纵向再来类型
					  */
					 //横向
					 if(x>e.getX()-60&&x<e.getX()+60
						 && y>e.getY()-60&& y<e.getY()+60){
						 //Mario死亡
						 this.dead();
					 }
					 //纵向
					 if(y+60==e.getY() && x<=e.getX()+60
								&& x>=e.getX()-60){
						 //根据敌人类型做出死亡判断
						 if(e.getType() == 1){
							 //普通敌人
							 e.dead();
						 }
						 if(e.getType() == 2){
							 //食人花
							 this.dead();
						 }
					 }
					 
				}
				
				
				
				
				
				//分析：Mario纵向行为
				//1.找出是否在障碍物上面
				boolean  onload=false;
				//2.找出Mario上升的高度 180
				//3.整理上升和下降的方法
				
				//分析:Mario横向遇到障碍物逻辑
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
					//在障碍物上面
					if(y+60==obs.getY() && x<=obs.getX()+60
								&& x>=obs.getX()-60){
						onload=true;
					}
					//顶到障碍物
					if(y-60==obs.getY() && x<=obs.getX()+60
							&& x>=obs.getX()-60){
						//根据障碍物的类型处理对应的结果
						//普通的砖块
						if(obs.getType() == 0){
							//干掉障碍物
							bg.getAllobs().remove(obs);
							//为游戏的重置做准备
							bg.getRemoveobs().add(obs);
						}
						//问号砖块
						if(obs.getType() == 4){
							//直接置换类型
							obs.setType(2);
							//uptime=5;
						}
						uptime=0;
				  }
				}
				
				//通过onload操作ymove进一步控制Y
				if(onload && uptime == 0){
					 //我在障碍物上面(1.定在障碍物上面 2.打算去跳)
					 //分析:standing下来|moving下来的
					 if(status.indexOf("left")!=-1){
						 if(status.indexOf("moving")!=-1){
							  status="left-moving";
						 }else{
							  status="left-standing";
						 }
					 }else{
						 if(status.indexOf("moving")!=-1){
							  status="right-moving";
						 }else{
							  status="right-standing";
						 }
					 }
					
					
					 MyFrame.跳跃=true;
				}else{
					//我不在障碍物上面(空中:上升或者下降)
					if(uptime!=0){
						//说明还在上升
						uptime--;
					}else{
						//说明上升最高点,只能是下降
						this.down();
					}
					
					y+=ymove;
					
					MyFrame.跳跃=false;
				}
				
				//根据canLeft和canRight进行x的控制
				if(xmove<0&&canLeft || xmove>0&&canRight){
					x+=xmove;
					//处理最左边
					if(x<=0) x=0;
				}
		}//else结束符号
				//分析：Mario横向移动图片逻辑
				//1:朝一个方向走图片切换最多是3个
				//2.朝左=朝右+5
				
				//定义：allMarioImges集合图片的索引
				//右: 0  1  2  3
				//左: 5  6  7  8
				int index=0;//默认是第0张
				if(status.indexOf("left")!=-1){
					index+=5;
				}
				//走和停止
				//standing
				//1.standing--->standing(ERROR)
				//2.standing--->moving(移动)
				//3.moving--->moving(移动)
				//4.moving--->standing(移动|停止)
				if(status.indexOf("moving")!=-1){
					index+=moving;//2
					moving++;
				 //切换完毕之后的判断结果
					if(moving == 4){
						moving=0;
					}
				}
				
				//指定图片的切换利用index
				if(status.indexOf("left-jumping")!=-1){
					showImage=StaticValue.allMarioImage.get(9);
				}else if(status.indexOf("right-jumping")!=-1){
					showImage=StaticValue.allMarioImage.get(4);
				}else{
					showImage=StaticValue.allMarioImage.get(index);
				}
				
				try {
					Thread.sleep(20);//参数 long(毫秒)
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		//三目童子
		private void dead() {
			life--;
			if(life == 0){
				//Mario所有的命都没有了
				live=false;
			}else{
				//让Mario和敌人以及障碍物全部重置
				this.reset();
				bg.reset();
			}
		}

		public  void  reset(){
			x=0;
			y=480;
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

		public void setX(int i) {
			// TODO Auto-generated method stub
			this.x=i;
		}

}
