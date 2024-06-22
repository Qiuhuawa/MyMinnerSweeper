package com.example.a;

import java.util.ArrayList;
import java.util.Iterator;

// Coord�����ڱ�ʾ��ά����ϵ�еĵ�
public class Coord {
	
	// ����˽������x��y���ֱ��ʾ�������������
	private int x;
	private int y;
	
	// �޲ι��캯������������ʼ��Ϊ (0,0)
	public Coord(){
		x=0;
		y=0;
	}
	
	// �вι��캯�������������������ֱ����ú�������
	public Coord(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	// �вι��캯��������һ�����������������궼����Ϊ���ֵ
	public Coord(int x){
		this.x=x;
		this.y=x;
	}
	
	// ���ƹ��캯��������һ��Coord���͵Ĳ��������ڸ���һ�������
	public Coord(Coord a){
		this.x=a.getX();
		this.y=a.getY();
	}
	
	// ���ú�����
	public void setX(int x){
		this.x=x;
	}
	
	// ����������
	public void setY(int y){
		this.y=y;
	}	
	
	// ��ȡ������
	public int getX(){
		return this.x;
	}
	
	// ��ȡ������
	public int getY(){
		return this.y;
	}
	
	// ���������ĺ�������Ϊ���������ĺ�������
	public void setCoord(Coord x){
		this.x=x.x;
		this.y=x.y;
	}
	
	// �Ƚϵ�ǰ������Ƿ��������������
	public boolean isEqual(Coord a){
		boolean res = false;
		if((getX()==a.getX())&&(getY()==a.getY())){
			res = true;
		}
		return res;
	}
	
	// ��鵱ǰ������Ƿ���ĳ����Χ��
	public boolean isGood(){
		boolean res=true;
		//��ʾ��������Ч��Χ
		if((x<1)||(x>Board.SIZEX)||(y<1)||(y>Board.SIZEY)) res=false;
		return res;
	}
	
	// ��鵱ǰ������Ƿ���������������
	public boolean isAdyacent(Coord pos){
		boolean res = false;
		int difx = 0;
		int dify = 0;
		if(this.isGood()){
			if(this.isEqual(pos)==false){
				difx = this.getX()-pos.getX();
				dify = this.getY()-pos.getY();
				// ��������֮�С�ڵ���1ʱ����Ϊ�������������
				if((difx>=-1)&&(difx<=1))
					if((dify>=-1)&&(dify<=1))
						res=true;
			}
		}
		return res;
	}
	
	// ��鵱ǰ������Ƿ����б��е�����һ�����������
	public boolean isAdyacent(ArrayList<Coord> list){
		boolean res = false;
		Iterator<Coord> iterator = list.iterator();
		while(iterator.hasNext())
			if(isAdyacent(iterator.next())) return true;
		return res;
	}
	
	// ��鵱ǰ������Ƿ����б���
	public boolean isInList(ArrayList<Coord> list){
		boolean res=false;
		Iterator<Coord> iterator = list.iterator();
		while(iterator.hasNext())
			if(iterator.next().isEqual(this)) return true;
		return res;
	}
}