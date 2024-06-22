package com.example.a;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class Board {
	
	public static final int SIZEX = 10;
	public static final int SIZEY = 15;
	public static final int NUMMINESDEFAULT = 25;
	public static final String msg_won = "!!!恭喜!!!\n 你完成了挑战。用时： ";
	public static final String msg_explod = "GAME OVER\n 地雷爆炸了！";
	public static final String msg_wrong = "GAME OVER\n 你把旗子放错地方了！";
	public static final String msg_start = "长按来翻开格子!";
	
	public boolean winflag;
	public long timeUsed;
	public ImageView views[][] = new ImageView[SIZEX][SIZEY];
	private Box boxes[][] = new Box[SIZEX][SIZEY];
	private int mines = 0;
	private int flags = 0;
	private Date start;
	
	public Board(){
		//初始化存储格子状态的变量
		for (int x=0;x<SIZEX;x++){
			for (int y=0;y<SIZEY;y++){
				boxes[x][y] = new Box(x+1,y+1);
			}
		}
		winflag = false;
		//初始化格子的Image步骤不在此处，而在gameactivity中，这里只有底层逻辑的初始化
	}
	
	public void initBoard(int nummines){
		  
		start = new Date();
		//随机设置地雷
		int ramdx,ramdy;
		Random randomGenerator = new Random();
		while(mines<nummines){
			ramdx = randomGenerator.nextInt(SIZEX); if(ramdx==0) ramdx=1;
			ramdy = randomGenerator.nextInt(SIZEY); if(ramdy==0) ramdy=1;
			if(boxes[ramdx-1][ramdy-1].hasMine()==false){
				boxes[ramdx-1][ramdy-1].setToMine();
				mines++;
			}
		}
		
		//设置周围格子的数字（即显示地雷多少的数字
		for (int x=0;x<SIZEX;x++){
			for (int y=0;y<SIZEY;y++){
				if(boxes[x][y].hasMine()){
					boxes[x][y].setNumMines(11);
				}
				else{
					setNumMinesAround(boxes[x][y]);
				}
			}
		}
	}
	//获取给定位置附近的所有box对象！便于后续的统一处理。
	public ArrayList<Coord> getBoxesAround(Coord pos){
		ArrayList<Coord> res = new ArrayList<Coord>();
		if((getBox(new Coord(pos.getX()-1,pos.getY()+1)))!=null) res.add(new Coord(pos.getX()-1,pos.getY()+1));
		if((getBox(new Coord(pos.getX(),pos.getY()+1)))!=null) res.add(new Coord(pos.getX(),pos.getY()+1));
		if((getBox(new Coord(pos.getX()+1,pos.getY()+1)))!=null) res.add(new Coord(pos.getX()+1,pos.getY()+1));
		if((getBox(new Coord(pos.getX()-1,pos.getY())))!=null) res.add(new Coord(pos.getX()-1,pos.getY()));
		if((getBox(new Coord(pos.getX()+1,pos.getY())))!=null) res.add(new Coord(pos.getX()+1,pos.getY()));
		if((getBox(new Coord(pos.getX()-1,pos.getY()-1)))!=null) res.add(new Coord(pos.getX()-1,pos.getY()-1));
		if((getBox(new Coord(pos.getX(),pos.getY()-1)))!=null) res.add(new Coord(pos.getX(),pos.getY()-1));
		if((getBox(new Coord(pos.getX()+1,pos.getY()-1)))!=null) res.add(new Coord(pos.getX()+1,pos.getY()-1));
		return res;	
	}
	//用于获取box对象的方法，同时防止越界
	public Box getBox(Coord pos){
		if((pos.getX()<1)||(pos.getY()<1)||(pos.getX()>SIZEX)||(pos.getY()>SIZEY)) return null;
		else return boxes[pos.getX()-1][pos.getY()-1];
	}
	//获取格子上的图像视图的方法，也要防止越界
	public ImageView getView(Coord pos){
		if((pos.getX()<1)||(pos.getY()<1)||(pos.getX()>SIZEX)||(pos.getY()>SIZEY)) return null;
		else return views[pos.getX()-1][pos.getY()-1];
	}
	//为每个box对象设置周围地雷数量
	public void setNumMinesAround(Box box){
		ArrayList<Coord> around = getBoxesAround(box.getPos());
		Iterator<Coord> iterator = around.iterator();
		Coord i = new Coord();
		while (iterator.hasNext()){
			i = iterator.next();
			if(getBox(i).hasMine())
				box.addMineAround();
		}
	}
	//返回旗子数量
	public int numFlagsAround(Box box){
		int res = 0;
		ArrayList<Coord> around = getBoxesAround(box.getPos());
		Iterator<Coord> iterator = around.iterator();
		Coord i = new Coord();
		while (iterator.hasNext()){
			i = iterator.next();
			if(getBox(i).hasFlag())
				res++;
		}
		return res;
	}
	
	public String click(Coord pos, int type){
		
		String msg = "";//用于存通知消息
		//短点击具体判断过程
		if (type==GameActivity.CLICK){
			if(getBox(pos)!=null){
				if(getBox(pos).isShown()){//若点击的格子已经翻开，则尝试清除其周围未翻开的格子
					msg=clean(pos);
				}
				else{//若格子未翻开，则设置旗子
					if(getBox(pos).hasFlag()){//若格子上有旗子，移除旗子（再次点击移除旗子的操作）
						flags--;
						getBox(pos).removeFlag();
						getView(pos).setImageResource(R.drawable.free);
					}
					else{
						if(flags<=mines){//若移除旗子和设置旗子的位置在同一个地方，则提醒长按翻开格子
							if(noBoxIsShown()) msg = msg_start;
							flags++;
							getBox(pos).setToFlag();
							getView(pos).setImageResource(R.drawable.flag);
						}
					}
				}
			}
		}
		if(type==GameActivity.LONGCLICK){//长按判断过程
			
			if(getBox(pos).hasMine()){//若是地雷，炸！ -> Game Over!
				msg = msg_explod;
				getBox(pos).setStatus(Box.NOTFLAGGED);
				uncover(pos);
				deleteListeners();
			}
			else{
				uncover(pos);
			}
		}
		//检查游戏是否结束（即没有更多格子需要翻开或设置旗子）
		if (isFinished())
			if((msg.compareTo("")==0)||(msg.compareTo("clean")==0)){
				//因为此时剩余的格子若是只剩地雷数目，那么其上必有旗子或是未被翻开，若不足地雷数目，则表示有错误旗子或者有格子没被翻开
				if(this.getFlags()==this.getMines()){
					Date end = new Date();
					timeUsed = (end.getTime() - start.getTime())/1000;
					msg = msg_won + timeUsed + " 秒!";
					winflag = true;
					deleteListeners();//结束时立即删除监听，确保不会在结束后又点击到地雷导致爆炸
				}
		}
		return msg;
	}
	//清除方块的判断方法！最佳搭档之一！
	public String clean(Coord pos){
		
		String msg = "";
		if(getBox(pos).getNumMines()==numFlagsAround(getBox(pos))){	
			//若格子上的数字与其周围的旗子数量一致，那么翻开周围剩下的所有格子
			ArrayList<Coord> around = getBoxesAround(pos);//
			Iterator<Coord> iterator = around.iterator();
			Coord i = new Coord();
			//判断旗子是否设置错误
			if(badFlagged(pos)){ 
				msg = msg_wrong;
				while(iterator.hasNext()){
					i = iterator.next();
					uncover(i);
				} 
				deleteListeners();//象征游戏结束，须删除监听
			}
			else{
				while(iterator.hasNext()){
					i = iterator.next();
					uncover(i);
					if(getBox(i).getNumMines()==0) msg="clean";
				}
			}
		}
		return msg;
	}
	//自动连锁清除
	public void autoUncover(Coord pos){
		Coord aux = new Coord(pos);
    	ArrayList<Coord> array = new ArrayList<Coord>();
    	boolean more=true;
    	array.add(pos);
    	while(more){
    		more=false;
    		for (int x=0;x<SIZEX;x++){
      			for (int y=0;y<SIZEY;y++){
      				aux = new Coord(x+1,y+1);
      				if(aux.isAdyacent(array)) if(getBox(aux).getNumMines()==0) if(aux.isInList(array)==false){
      					array.add(aux);
      					more=true;
      				}
      			}
    		}
    	}
    	
    	Iterator<Coord> iterator=array.iterator();
    	while(iterator.hasNext()){
    		aux = iterator.next();
    			uncover(aux);
    			click(aux,GameActivity.CLICK);
    	}
    }
	//检测翻开的格子和插旗的格子总和是否与总格子数量一致
	public boolean isFinished(){
		if ((getNumBoxesShown()+getFlags())==(Board.SIZEX*Board.SIZEY)) return true;
		else return false;
	}
	//获取翻开的格子数目
	public int getNumBoxesShown(){
		int res=0;
		for (int x=0;x<SIZEX;x++){
			for (int y=0;y<SIZEY;y++){
				if(boxes[x][y].isShown()) res++;
			}
		}
		return res;
	}
	//检测方法，检测游戏是否已经开始
	public boolean noBoxIsShown(){
		boolean res=true;
		for (int x=0;x<SIZEX;x++){
			for (int y=0;y<SIZEY;y++){
				if(boxes[x][y].isShown()) return false;
			}
		}
		return res;
	}
	//检测插旗错误（只有两种情况
	public boolean badFlagged(Coord pos){
		
		boolean res = false;
		ArrayList<Coord> around = getBoxesAround(pos);
		Iterator<Coord> iterator = around.iterator();
		Coord i = new Coord();
		while(iterator.hasNext()){
			i = iterator.next();
			if((getBox(i).hasFlag())&&(!getBox(i).hasMine())){//若有旗子但没有地雷
				getBox(i).setStatus(Box.BADFLAGGED);
				getBox(i).setToNotFlag();
				res=true;
			}
			if((!getBox(i).hasFlag())&&(getBox(i).hasMine())){//若没有旗子但有地雷
				getBox(i).setStatus(Box.NOTFLAGGED);
				getBox(i).setToNotFlag();
				res=true;
			}
		}
		return res;
		
	}
		
	//增加旗子数量
	public void AddFlag(){
		flags++;
	}
	//删除监听方法
	public void deleteListeners(){
		for (int x=0;x<SIZEX;x++){
    		for (int y=0;y<SIZEY;y++){
				views[x][y].setOnClickListener(null);
				views[x][y].setOnLongClickListener(null);
				if(boxes[x][y].hasMine()) uncover(new Coord(x+1,y+1));
			}
		}
	}
	//翻开格子的正式执行方法！
	public void uncover(Coord pos){
		
		if (getBox(pos)!=null){
			
			if (getBox(pos).hasFlag()==false){
				getBox(pos).setToShown();
				
				switch(getBox(pos).getNumMines()){
				case 0: 
					getView(pos).setImageResource(R.drawable.c0);
					return;
				case 1:
					getView(pos).setImageResource(R.drawable.c1);
					return;
				case 2:
					getView(pos).setImageResource(R.drawable.c2);
					return;
				case 3:
					getView(pos).setImageResource(R.drawable.c3);
					return;
				case 4:
					getView(pos).setImageResource(R.drawable.c4);
					return;
				case 5:
					getView(pos).setImageResource(R.drawable.c5);
					return;
				case 6:
					getView(pos).setImageResource(R.drawable.c6);
					return;
				case 7:
					getView(pos).setImageResource(R.drawable.c7);
					return;
				case 8:
					getView(pos).setImageResource(R.drawable.c8);
					return;
				case 9:
					getView(pos).setImageResource(R.drawable.flag_wrong);//没插对旗子
					return;
				case 10:
					getView(pos).setImageResource(R.drawable.mine_wrong);//炸了
					return;
				case 11:
					getView(pos).setImageResource(R.drawable.mine);
					return;
				}
			}
		}
	}
	
	public int getMines(){
		return mines;
	}
	public int getFlags(){
		return flags;
	}
}
