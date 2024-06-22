package com.example.a;

// Box类用于定义和操作扫雷游戏中的格子的状态以及详细信息
public class Box {
	
	// 定义静态常量，表示格子的不同状态
	public static final boolean HIDDEN=true; // 格子当前是隐藏的
	public static final boolean SHOWN=false; // 格子已经被揭示
	public static final boolean MINE=true; // 格子中有地雷
	public static final boolean NOMINE=false; // 格子中没有地雷
	public static final boolean FLAG=true; // 格子被标记为有地雷
	public static final boolean NOFLAG=false; // 格子没有被标记
	public static final int BADFLAGGED = 9; // 错误地标记了地雷
	public static final int NOTFLAGGED = 10; // 没有被标记的格子
	
	// 定义私有属性，存储格子的位置和状态
	private Coord pos = new Coord(); // 存储格子在游戏板上的位置
	private boolean state = HIDDEN; // 表示格子的当前状态（隐藏或显示）
	private boolean mine = NOMINE; // 表示格子是否包含地雷
	private boolean flag = NOFLAG; // 表示格子是否被玩家标记为地雷
	private boolean bad_flagged = false; // 表示格子是否被错误地标记为地雷
	private int num_mines = 0; // 表示周围格子中地雷的数量
	
	// 无参构造函数，将格子位置初始化为 (0,0)
	public Box(){
		pos.setX(0);
		pos.setY(0);
	}
	
	// 有参构造函数，接受两个参数，分别设置格子的横纵坐标
	public Box(int x,int y){
		pos.setX(x);
		pos.setY(y);
	}
	
	// 有参构造函数，接受一个参数，将格子的横纵坐标都设置为这个值
	public Box(int x){
		pos.setX(x);
		pos.setY(x);
	}
	
	// 复制构造函数，接受一个Box类型的参数，用于复制一个格子的状态和位置
	public Box(Box copy){
		pos.setX(copy.getPos().getX());
		pos.setY(copy.getPos().getY());
	}
	
	// 设置周围格子中地雷的数量
	public void setNumMines(int value){
		num_mines=value;
	}
	
	// 设置格子的位置
	public void setPos(Coord x){
		pos.setCoord(x);
	}
	
	// 将格子的状态设置为显示
	public void setToShown(){
		state=SHOWN;
	}
	
	// 将格子标记为错误地标记了地雷
	public void setToBadFlagged(){
		bad_flagged = true;
	}
	
	// 将格子标记为没有错误地标记地雷
	public void setToNotBadFlagged(){
		bad_flagged = false;
	}
	
	// 将格子标记为包含地雷
	public void setToMine(){
		mine=MINE;
	}
	
	// 将格子标记为有地雷
	public void setToFlag(){
		flag=FLAG;
	}
	
	// 将格子标记为没有地雷
	public void setToNotFlag(){
		flag=NOFLAG;
	}
	
	// 将格子标记为没有地雷
	public void removeFlag(){
		flag=NOFLAG;
	}
	
	// 设置格子的状态，可能是地雷数量或特殊状态（如错误标记）
	public void setStatus(int status){
		num_mines=status;
	}
	
	// 返回格子的位置
	public Coord getPos(){
		return pos;
	}
	
	// 检查格子是否被显示
	public boolean isShown(){
		boolean res = false;
		if (state==SHOWN) res=true;
		return res;
	}
	
	// 检查格子是否包含地雷
	public boolean hasMine(){
		return mine; 
	}
	
	// 检查格子是否被标记为地雷
	public boolean hasFlag(){
		return flag;
	}
	
	// 检查格子是否被错误地标记为地雷
	public boolean isBadFlagged(){
		return bad_flagged;
	}
	
	// 返回周围格子中地雷的数量
	public int getNumMines(){
		return num_mines;
	}
	
	// 增加周围格子中地雷的数量
	public void addMineAround(){
		num_mines++;
	}
}