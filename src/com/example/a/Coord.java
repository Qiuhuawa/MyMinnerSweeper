package com.example.a;

import java.util.ArrayList;
import java.util.Iterator;

// Coord类用于表示二维坐标系中的点
public class Coord {
	
	// 定义私有属性x和y，分别表示横坐标和纵坐标
	private int x;
	private int y;
	
	// 无参构造函数，将坐标点初始化为 (0,0)
	public Coord(){
		x=0;
		y=0;
	}
	
	// 有参构造函数，接受两个参数，分别设置横纵坐标
	public Coord(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	// 有参构造函数，接受一个参数，将横纵坐标都设置为这个值
	public Coord(int x){
		this.x=x;
		this.y=x;
	}
	
	// 复制构造函数，接受一个Coord类型的参数，用于复制一个坐标点
	public Coord(Coord a){
		this.x=a.getX();
		this.y=a.getY();
	}
	
	// 设置横坐标
	public void setX(int x){
		this.x=x;
	}
	
	// 设置纵坐标
	public void setY(int y){
		this.y=y;
	}	
	
	// 获取横坐标
	public int getX(){
		return this.x;
	}
	
	// 获取纵坐标
	public int getY(){
		return this.y;
	}
	
	// 设置坐标点的横纵坐标为参数坐标点的横纵坐标
	public void setCoord(Coord x){
		this.x=x.x;
		this.y=x.y;
	}
	
	// 比较当前坐标点是否与参数坐标点相等
	public boolean isEqual(Coord a){
		boolean res = false;
		if((getX()==a.getX())&&(getY()==a.getY())){
			res = true;
		}
		return res;
	}
	
	// 检查当前坐标点是否在某个范围内
	public boolean isGood(){
		boolean res=true;
		//表示坐标点的有效范围
		if((x<1)||(x>Board.SIZEX)||(y<1)||(y>Board.SIZEY)) res=false;
		return res;
	}
	
	// 检查当前坐标点是否与参数坐标点相邻
	public boolean isAdyacent(Coord pos){
		boolean res = false;
		int difx = 0;
		int dify = 0;
		if(this.isGood()){
			if(this.isEqual(pos)==false){
				difx = this.getX()-pos.getX();
				dify = this.getY()-pos.getY();
				// 横纵坐标之差都小于等于1时，认为两个坐标点相邻
				if((difx>=-1)&&(difx<=1))
					if((dify>=-1)&&(dify<=1))
						res=true;
			}
		}
		return res;
	}
	
	// 检查当前坐标点是否与列表中的任意一个坐标点相邻
	public boolean isAdyacent(ArrayList<Coord> list){
		boolean res = false;
		Iterator<Coord> iterator = list.iterator();
		while(iterator.hasNext())
			if(isAdyacent(iterator.next())) return true;
		return res;
	}
	
	// 检查当前坐标点是否在列表中
	public boolean isInList(ArrayList<Coord> list){
		boolean res=false;
		Iterator<Coord> iterator = list.iterator();
		while(iterator.hasNext())
			if(iterator.next().isEqual(this)) return true;
		return res;
	}
}