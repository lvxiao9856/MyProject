package Thread;

class MyThreadRunnable implements Runnable{
	private int ticket = 5;
	public void run(){
		for (int i=0;i<10;i++)
		{
			if(ticket > 0){
				System.out.println("ticket = " + ticket--);
			}
		}
	}
}

public class RunnableDemo{
	public static void main(String[] args){
		MyThreadRunnable my = new MyThreadRunnable();
		new Thread(my).start();
		new Thread(my).start();
		new Thread(my).start();
	}
}