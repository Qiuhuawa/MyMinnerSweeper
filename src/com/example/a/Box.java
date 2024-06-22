package com.example.a;

// Box�����ڶ���Ͳ���ɨ����Ϸ�еĸ��ӵ�״̬�Լ���ϸ��Ϣ
public class Box {
	
	// ���徲̬��������ʾ���ӵĲ�ͬ״̬
	public static final boolean HIDDEN=true; // ���ӵ�ǰ�����ص�
	public static final boolean SHOWN=false; // �����Ѿ�����ʾ
	public static final boolean MINE=true; // �������е���
	public static final boolean NOMINE=false; // ������û�е���
	public static final boolean FLAG=true; // ���ӱ����Ϊ�е���
	public static final boolean NOFLAG=false; // ����û�б����
	public static final int BADFLAGGED = 9; // ����ر���˵���
	public static final int NOTFLAGGED = 10; // û�б���ǵĸ���
	
	// ����˽�����ԣ��洢���ӵ�λ�ú�״̬
	private Coord pos = new Coord(); // �洢��������Ϸ���ϵ�λ��
	private boolean state = HIDDEN; // ��ʾ���ӵĵ�ǰ״̬�����ػ���ʾ��
	private boolean mine = NOMINE; // ��ʾ�����Ƿ��������
	private boolean flag = NOFLAG; // ��ʾ�����Ƿ���ұ��Ϊ����
	private boolean bad_flagged = false; // ��ʾ�����Ƿ񱻴���ر��Ϊ����
	private int num_mines = 0; // ��ʾ��Χ�����е��׵�����
	
	// �޲ι��캯����������λ�ó�ʼ��Ϊ (0,0)
	public Box(){
		pos.setX(0);
		pos.setY(0);
	}
	
	// �вι��캯�������������������ֱ����ø��ӵĺ�������
	public Box(int x,int y){
		pos.setX(x);
		pos.setY(y);
	}
	
	// �вι��캯��������һ�������������ӵĺ������궼����Ϊ���ֵ
	public Box(int x){
		pos.setX(x);
		pos.setY(x);
	}
	
	// ���ƹ��캯��������һ��Box���͵Ĳ��������ڸ���һ�����ӵ�״̬��λ��
	public Box(Box copy){
		pos.setX(copy.getPos().getX());
		pos.setY(copy.getPos().getY());
	}
	
	// ������Χ�����е��׵�����
	public void setNumMines(int value){
		num_mines=value;
	}
	
	// ���ø��ӵ�λ��
	public void setPos(Coord x){
		pos.setCoord(x);
	}
	
	// �����ӵ�״̬����Ϊ��ʾ
	public void setToShown(){
		state=SHOWN;
	}
	
	// �����ӱ��Ϊ����ر���˵���
	public void setToBadFlagged(){
		bad_flagged = true;
	}
	
	// �����ӱ��Ϊû�д���ر�ǵ���
	public void setToNotBadFlagged(){
		bad_flagged = false;
	}
	
	// �����ӱ��Ϊ��������
	public void setToMine(){
		mine=MINE;
	}
	
	// �����ӱ��Ϊ�е���
	public void setToFlag(){
		flag=FLAG;
	}
	
	// �����ӱ��Ϊû�е���
	public void setToNotFlag(){
		flag=NOFLAG;
	}
	
	// �����ӱ��Ϊû�е���
	public void removeFlag(){
		flag=NOFLAG;
	}
	
	// ���ø��ӵ�״̬�������ǵ�������������״̬��������ǣ�
	public void setStatus(int status){
		num_mines=status;
	}
	
	// ���ظ��ӵ�λ��
	public Coord getPos(){
		return pos;
	}
	
	// �������Ƿ���ʾ
	public boolean isShown(){
		boolean res = false;
		if (state==SHOWN) res=true;
		return res;
	}
	
	// �������Ƿ��������
	public boolean hasMine(){
		return mine; 
	}
	
	// �������Ƿ񱻱��Ϊ����
	public boolean hasFlag(){
		return flag;
	}
	
	// �������Ƿ񱻴���ر��Ϊ����
	public boolean isBadFlagged(){
		return bad_flagged;
	}
	
	// ������Χ�����е��׵�����
	public int getNumMines(){
		return num_mines;
	}
	
	// ������Χ�����е��׵�����
	public void addMineAround(){
		num_mines++;
	}
}