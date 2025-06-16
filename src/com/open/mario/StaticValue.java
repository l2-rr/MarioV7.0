package com.open.mario;

import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


//将所有的图片加载到集合或者变量属性中，帮助开发
public class StaticValue {
	
	public static List<BufferedImage> allMarioImage=new ArrayList<BufferedImage>();
	public static BufferedImage startImage = null;
	public static BufferedImage endImage = null;
	public static BufferedImage bgImage = null;
	
	public static List<BufferedImage> allFlowerImage=new ArrayList<BufferedImage>();
	public static List<BufferedImage> allTriangleImage=new ArrayList<BufferedImage>();
	public static List<BufferedImage> allTurtleImage=new ArrayList<BufferedImage>();
	public static List<BufferedImage> allObstructionImage=new ArrayList<BufferedImage>();
	public static BufferedImage marioDeadImage = null;

	public static String imagePath=System.getProperty("user.dir")+"/bin/";
	// C:\Users\Administrator\
	// workspace\ssh\j1910_MarioV1.0/bin/

	//将全部图片初始化 注解模式（单元测试:public void test()）

	public static void init(){
		System.out.println(imagePath);
		//玛丽的10张图片
		for(int i=1;i<=10;i++){		
			try {//read or  write  mkdirs  createNewFile()
				//	allMarioImage.add(ImageIO.read(new File(System.getProperty("user.dir")+"/bin"+i+".jpg")));
				allMarioImage.add(ImageIO.read(new File(imagePath+i+".gif")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		try {
			startImage=ImageIO.read(new File(imagePath+"start.gif"));
			endImage=ImageIO.read(new File(imagePath+"firststageend.gif"));
			bgImage=ImageIO.read(new File(imagePath+"firststage.gif"));
			marioDeadImage=ImageIO.read(new File(imagePath+"over.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=1;i<=5;i++){
			try {
				if(i<=2){
					allFlowerImage.add(ImageIO.read(new File(imagePath+"flower"+i+".gif")));
				}
				
				if(i<=3){
					allTriangleImage.add(ImageIO.read(new File(imagePath+"triangle"+i+".gif")));
				}
				
				allTurtleImage.add(ImageIO.read(new File(imagePath+"Turtle"+i+".gif")));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int i=1;i<=12;i++){
			try {
				allObstructionImage.add(ImageIO.read(new File(imagePath+"ob"+i+".gif")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
	}
}
