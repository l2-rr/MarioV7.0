package com.open.mario;

//awt:abstract  window tools
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

//场景类 关《TankClient:背景 主人公  敌人  障碍物 etc》
public class   BackGround {
		//当前的关数
		private int  sort;
		//是不是最后一关的变量
		private boolean  flag;
		//背景图片
		private BufferedImage  showImage;
		//开始下落的变量
		public  boolean 旗帜下落;
		
		//定义存储每一关障碍物的集合
		private List<Obstrcution> allobs=new ArrayList<Obstrcution>();
		//定义存储每一关死亡的障碍物集合
		private List<Obstrcution> removeobs=new ArrayList<Obstrcution>();

		//定义存储每一关敌人集合
		private List<Enemy>  allenemys=new ArrayList<Enemy>();
		//定义存储每一关死亡的敌人集合
		private List<Enemy>  removeenemys=new ArrayList<Enemy>();
		
		public boolean 降旗完毕;
		
		public boolean 通关;

		
		
		public List<Enemy> getAllenemys() {
			return allenemys;
		}

		public List<Enemy> getRemoveenemys() {
			return removeenemys;
		}

		public List<Obstrcution> getRemoveobs() {
			return removeobs;
		}

		//alt+shift+s(Source源码)
		public BackGround(int sort, boolean flag) {
			super();
			this.sort = sort;
			this.flag = flag;
			
			//根据flag确定showImage
			if(flag){//最后一关
				 showImage=StaticValue.endImage;
			}else{   //还在继续
				 showImage=StaticValue.bgImage;
			}
			//根据sort的值确定allobs内容
			if(1 == sort){
				 //地面
				 for(int i=0;i<15;i++){
					 allobs.add(new Obstrcution(60*i,540,9,this));
				 }
				 //第一层
				 allobs.add(new Obstrcution(120,360,4,this));
				 allobs.add(new Obstrcution(300,360,0,this));
				 allobs.add(new Obstrcution(360,360,4,this));
				 allobs.add(new Obstrcution(420,360,0,this));
				 allobs.add(new Obstrcution(480,360,4,this));
				 allobs.add(new Obstrcution(540,360,0,this));
				 //第二层
				 allobs.add(new Obstrcution(420,180,4,this));
				 //花盆
				 allobs.add(new Obstrcution(660,480,8,this));
				 allobs.add(new Obstrcution(720,480,7,this));
				 allobs.add(new Obstrcution(660,540,6,this));
				 allobs.add(new Obstrcution(720,540,5,this));
			
				 //敌人
				 allenemys.add(new Enemy(600,480,1,true,this));
				 allenemys.add(new Enemy(690,520,2,540,360,true,this));
			}
			//处理第二关
			if(sort == 2){
				 //地面
				 for(int i=0;i<15;i++){
					 allobs.add(new Obstrcution(60*i,540,9,this));
				 }
				 //第一个花盆
				 allobs.add(new Obstrcution(160,480,8,this));
				 allobs.add(new Obstrcution(220,480,7,this));
				 allobs.add(new Obstrcution(160,540,6,this));
				 allobs.add(new Obstrcution(220,540,5,this));
				 
				 allenemys.add(new Enemy(190,520,2,540,360,true,this));
				 
				 //从左到右设计
				 allenemys.add(new Enemy(280,480,1,false,this));
				 //从右到左设计
				 allenemys.add(new Enemy(600,480,1,true,this));
				 
				//第二个花盆
				 allobs.add(new Obstrcution(660,420,8,this));
				 allobs.add(new Obstrcution(720,420,7,this));
				 allobs.add(new Obstrcution(660,480,6,this));
				 allobs.add(new Obstrcution(720,480,5,this));
				 allobs.add(new Obstrcution(660,540,6,this));
				 allobs.add(new Obstrcution(720,540,5,this));
				 allenemys.add(new Enemy(690,480,2,540,360,true,this));
				 allenemys.add(new Enemy(690,520,2,540,360,true,this));
			}
			//处理第三关
			if(sort == 3){
				 //地面
				 for(int i=0;i<15;i++){
					 allobs.add(new Obstrcution(60*i,540,9,this));
				 }
				 //画出地面的砖块
				 allobs.add(new Obstrcution(520,480,2,this));
				 //画出旗帜
				 allobs.add(new Obstrcution(550,180,11,this));
			}
			
		}

		public BufferedImage getShowImage() {
			return showImage;
		}

		public List<Obstrcution> getAllobs() {
			return allobs;
		}

		public int getSort() {
			// TODO Auto-generated method stub
			return sort;
		}

		public boolean isFlag() {
			return flag;
		}
		
		//解冻方法
		public  void  resume(){
			for(Enemy  e:allenemys){
				 e.resume();
			}
		}

		public void reset() {
			this.allobs.addAll(removeobs);
			this.allenemys.addAll(removeenemys);
			
			//分别取出敌人和障碍物将对应数据回传给他们 
			for(Obstrcution  obs:allobs){
				obs.reset();
			}
			
			for(Enemy   e:allenemys){
				e.reset();
			}
		}
		
		
		
}
