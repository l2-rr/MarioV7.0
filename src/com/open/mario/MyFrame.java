package com.open.mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//1.画出游戏的窗口 2.画出窗口默认的背景  3.画出障碍物
//4.画出主人公Mario  5.处理Mario的横向移动  6.切换图片
//7.处理Mario横向遇到障碍物
//8.完成Mario的基本跳跃  9.处理障碍物死亡问题  10.画出敌人
//11.处理敌人移动    12.完成Mario遇到敌人的情况
//13.处理切换场景    14.完成Mario多条生命
//15.处理开机画面	   16.处理线程挂载（冻结起来）
public class MyFrame  extends JFrame{
	
	//定义所有的场景的集合
	List<BackGround>  allbg=new ArrayList<BackGround>();
	//定义当前的场景
	BackGround  nowbg;
	//定义当前的主人公
	Mario  m;
	//操作Jump的boolean变量 $￥
	public static  boolean  跳跃=true;
	//定义游戏是否开始
	public static  boolean 开始=false;
	
	public  MyFrame(){
		//初始化图片
		StaticValue.init();
		//初始化场景
		for(int i=1;i<=3;i++){
			allbg.add(new BackGround(i,i==3?true:false));
		}
		//初始化当前的场景【默认关数=1】
		nowbg=allbg.get(0);
		//初始化Mario
		m=new Mario(0,480);
		//告诉mario在哪一个场景中
		m.setBg(nowbg);
		
		//窗口基本的设置
		//标题
		this.setTitle("超级玛丽");
		//大小
		this.setSize(900,600);
		//固定大小
		this.setResizable(false);
		//关闭
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//显示
		this.setVisible(true);
		
		//完成键盘的监听
		this.addKeyListener(new MyListener());
		//实现界面的刷新
		new Thread(new MyThread()).start();
	}
	
	//内部类（成员、静态、局部、匿名）
	private class MyListener extends KeyAdapter
		implements  KeyListener{//F4
		
		//压下
		public void keyPressed(KeyEvent e) {
			int  key=e.getKeyCode();//ASCII
	if(开始){	
			if(key == KeyEvent.VK_LEFT){
				//调用mario朝左移动方法
				m.leftmove();
			}
			if(key == KeyEvent.VK_RIGHT){
				//调用mario朝右移动方法
				m.rightmove();
			}
			if(key == KeyEvent.VK_SPACE){
				//调用mario上升的方法
			 if(跳跃)
				m.jump();
			}
	}else{
			if(key == KeyEvent.VK_SPACE){
				//说明游戏开始
				开始=true;
				//解冻
				nowbg.resume();
			}
		
	}
		}
		 
		//松开
		public void keyReleased(KeyEvent e) {
			int  key=e.getKeyCode();
	if(开始){		
			if(key == KeyEvent.VK_LEFT){
				//调用mario朝左停止方法
				m.leftstop();
			}
			if(key == KeyEvent.VK_RIGHT){
				//调用mario朝右停止方法
				m.rightstop();
			}
	}
		}
	}
	
	private class MyThread implements Runnable{
		public void run() {
			while(true){
				//处理游戏通关
				if(nowbg.通关){
					//显示对话框
					JOptionPane.showMessageDialog(null, "恭喜你，大内低手");
					//关闭JVM 
					System.exit(-1);
				}
				
				//切换场景
				if(m.getX()>=840){
					//1.获取当前的场景
					int  n=nowbg.getSort();
					//2.设置游戏下一个场景
					nowbg=allbg.get(n+1-1);
					//3.重置m的横坐标
					m.setX(0);
					//4.告诉Mario你也需要切换场景
					m.setBg(nowbg);
					
					//解冻
					nowbg.resume();
				}
				
				//根据Mario的live判断游戏是否结束
				if(!m.live){
					//显示对话框
					JOptionPane.showMessageDialog(null, "mario死亡,游戏结束");
					//关闭JVM 
					System.exit(-1);
				}	
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//repaint/update
				repaint();//重绘reset+paint
			}
		}
	}
	
	//绘制方法(main函数系统会刷新调用)
	public void paint(Graphics g) {
		// super.paint(g);
		//定义临时变量(没有内存的 和方法)帮助我们解决闪烁问题(桌布)
		BufferedImage image=new BufferedImage(900,600,BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2 = image.getGraphics();
	if(开始){	
		//绘制背景(参数：图片 坐标  当前对象)
		g2.drawImage(nowbg.getShowImage(),0, 0, this);
		//绘制障碍物
		Iterator<Obstrcution> its = nowbg.getAllobs().iterator();
		while(its.hasNext()){
			Obstrcution  obs=its.next();
			g2.drawImage(obs.getShowImage(),obs.getX(),obs.getY(),this);
		}
		//绘制Mario
		g2.drawImage(m.getShowImage(),m.getX(),m.getY(),this);
	
		//绘制敌人	擦式类型
		Iterator<Enemy> it2 = nowbg.getAllenemys().iterator();
		while(it2.hasNext()){
			 Enemy e=it2.next();//返回值是Object
			 g2.drawImage(e.getShowImage(), e.getX(), e.getY(), this);
		}
	}else{
		//绘制开机的画面
		g2.drawImage(StaticValue.startImage,0,0,this);
	}
		
		//将image(桌布)整体和g发生关系
		g.drawImage(image,0,0,this);
	}
	
	public static void main(String[] args) {
		new MyFrame();
	}
}
