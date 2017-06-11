
public class Test {
	
	private class pc {
		int m1 = 90;
	}
	
	public void dick() {
		pc s2 = new pc();
	}
	
	new myClass() {
		public void f1() { }
	}.f1();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		pc s = new Test().new pc();
		System.out.println(s.m1);
		Object o = (Object)s;
		System.out.println(((pc)o).m1);
		pc ss = (pc)o;
		System.out.println(ss.m1);
	}

}
