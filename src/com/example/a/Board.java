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
	public static final String msg_won = "!!!��ϲ!!!\n ���������ս����ʱ�� ";
	public static final String msg_explod = "GAME OVER\n ���ױ�ը�ˣ�";
	public static final String msg_wrong = "GAME OVER\n ������ӷŴ�ط��ˣ�";
	public static final String msg_start = "��������������!";
	
	public boolean winflag;
	public long timeUsed;
	public ImageView views[][] = new ImageView[SIZEX][SIZEY];
	private Box boxes[][] = new Box[SIZEX][SIZEY];
	private int mines = 0;
	private int flags = 0;
	private Date start;
	
	public Board(){
		//��ʼ���洢����״̬�ı���
		for (int x=0;x<SIZEX;x++){
			for (int y=0;y<SIZEY;y++){
				boxes[x][y] = new Box(x+1,y+1);
			}
		}
		winflag = false;
		//��ʼ�����ӵ�Image���費�ڴ˴�������gameactivity�У�����ֻ�еײ��߼��ĳ�ʼ��
	}
	
	public void initBoard(int nummines){
		  
		start = new Date();
		//������õ���
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
		
		//������Χ���ӵ����֣�����ʾ���׶��ٵ�����
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
	//��ȡ����λ�ø���������box���󣡱��ں�����ͳһ����
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
	//���ڻ�ȡbox����ķ�����ͬʱ��ֹԽ��
	public Box getBox(Coord pos){
		if((pos.getX()<1)||(pos.getY()<1)||(pos.getX()>SIZEX)||(pos.getY()>SIZEY)) return null;
		else return boxes[pos.getX()-1][pos.getY()-1];
	}
	//��ȡ�����ϵ�ͼ����ͼ�ķ�����ҲҪ��ֹԽ��
	public ImageView getView(Coord pos){
		if((pos.getX()<1)||(pos.getY()<1)||(pos.getX()>SIZEX)||(pos.getY()>SIZEY)) return null;
		else return views[pos.getX()-1][pos.getY()-1];
	}
	//Ϊÿ��box����������Χ��������
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
	//������������
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
		
		String msg = "";//���ڴ�֪ͨ��Ϣ
		//�̵�������жϹ���
		if (type==GameActivity.CLICK){
			if(getBox(pos)!=null){
				if(getBox(pos).isShown()){//������ĸ����Ѿ������������������Χδ�����ĸ���
					msg=clean(pos);
				}
				else{//������δ����������������
					if(getBox(pos).hasFlag()){//�������������ӣ��Ƴ����ӣ��ٴε���Ƴ����ӵĲ�����
						flags--;
						getBox(pos).removeFlag();
						getView(pos).setImageResource(R.drawable.free);
					}
					else{
						if(flags<=mines){//���Ƴ����Ӻ��������ӵ�λ����ͬһ���ط��������ѳ�����������
							if(noBoxIsShown()) msg = msg_start;
							flags++;
							getBox(pos).setToFlag();
							getView(pos).setImageResource(R.drawable.flag);
						}
					}
				}
			}
		}
		if(type==GameActivity.LONGCLICK){//�����жϹ���
			
			if(getBox(pos).hasMine()){//���ǵ��ף�ը�� -> Game Over!
				msg = msg_explod;
				getBox(pos).setStatus(Box.NOTFLAGGED);
				uncover(pos);
				deleteListeners();
			}
			else{
				uncover(pos);
			}
		}
		//�����Ϸ�Ƿ��������û�и��������Ҫ�������������ӣ�
		if (isFinished())
			if((msg.compareTo("")==0)||(msg.compareTo("clean")==0)){
				//��Ϊ��ʱʣ��ĸ�������ֻʣ������Ŀ����ô���ϱ������ӻ���δ�������������������Ŀ�����ʾ�д������ӻ����и���û������
				if(this.getFlags()==this.getMines()){
					Date end = new Date();
					timeUsed = (end.getTime() - start.getTime())/1000;
					msg = msg_won + timeUsed + " ��!";
					winflag = true;
					deleteListeners();//����ʱ����ɾ��������ȷ�������ڽ������ֵ�������׵��±�ը
				}
		}
		return msg;
	}
	//���������жϷ�������Ѵ֮һ��
	public String clean(Coord pos){
		
		String msg = "";
		if(getBox(pos).getNumMines()==numFlagsAround(getBox(pos))){	
			//�������ϵ�����������Χ����������һ�£���ô������Χʣ�µ����и���
			ArrayList<Coord> around = getBoxesAround(pos);//
			Iterator<Coord> iterator = around.iterator();
			Coord i = new Coord();
			//�ж������Ƿ����ô���
			if(badFlagged(pos)){ 
				msg = msg_wrong;
				while(iterator.hasNext()){
					i = iterator.next();
					uncover(i);
				} 
				deleteListeners();//������Ϸ��������ɾ������
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
	//�Զ��������
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
	//��ⷭ���ĸ��ӺͲ���ĸ����ܺ��Ƿ����ܸ�������һ��
	public boolean isFinished(){
		if ((getNumBoxesShown()+getFlags())==(Board.SIZEX*Board.SIZEY)) return true;
		else return false;
	}
	//��ȡ�����ĸ�����Ŀ
	public int getNumBoxesShown(){
		int res=0;
		for (int x=0;x<SIZEX;x++){
			for (int y=0;y<SIZEY;y++){
				if(boxes[x][y].isShown()) res++;
			}
		}
		return res;
	}
	//��ⷽ���������Ϸ�Ƿ��Ѿ���ʼ
	public boolean noBoxIsShown(){
		boolean res=true;
		for (int x=0;x<SIZEX;x++){
			for (int y=0;y<SIZEY;y++){
				if(boxes[x][y].isShown()) return false;
			}
		}
		return res;
	}
	//���������ֻ���������
	public boolean badFlagged(Coord pos){
		
		boolean res = false;
		ArrayList<Coord> around = getBoxesAround(pos);
		Iterator<Coord> iterator = around.iterator();
		Coord i = new Coord();
		while(iterator.hasNext()){
			i = iterator.next();
			if((getBox(i).hasFlag())&&(!getBox(i).hasMine())){//�������ӵ�û�е���
				getBox(i).setStatus(Box.BADFLAGGED);
				getBox(i).setToNotFlag();
				res=true;
			}
			if((!getBox(i).hasFlag())&&(getBox(i).hasMine())){//��û�����ӵ��е���
				getBox(i).setStatus(Box.NOTFLAGGED);
				getBox(i).setToNotFlag();
				res=true;
			}
		}
		return res;
		
	}
		
	//������������
	public void AddFlag(){
		flags++;
	}
	//ɾ����������
	public void deleteListeners(){
		for (int x=0;x<SIZEX;x++){
    		for (int y=0;y<SIZEY;y++){
				views[x][y].setOnClickListener(null);
				views[x][y].setOnLongClickListener(null);
				if(boxes[x][y].hasMine()) uncover(new Coord(x+1,y+1));
			}
		}
	}
	//�������ӵ���ʽִ�з�����
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
					getView(pos).setImageResource(R.drawable.flag_wrong);//û�������
					return;
				case 10:
					getView(pos).setImageResource(R.drawable.mine_wrong);//ը��
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
