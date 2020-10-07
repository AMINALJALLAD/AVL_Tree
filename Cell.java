
/**
 * @author a_aljall
 * 
 * 
 *
 */
public class Cell implements Comparable {
		private String bits; // a sting of digit binary
		private int num; // a number that is interpreted from binary digits 
		private int bit; // a binary digit either 0 or 1 
		public Cell(int num) {
		//	bits = K1.convertToBinary(num);
			this.num = num;
		}
		
		
		public Cell clone() {
			return new Cell(this);
		}
		
		public Cell(Cell c) {
			setBit(c.bit);
			setBits(c.bits);
			setNum(c.num);
		}
		
		public void setBits(String bits) {
			this.bits = bits;
		}


		public void setNum(int num) {
			this.num = num;
		}

		public int getNum() {
			return  num;
		}

		
		public void setBit(int bit) {
			this.bit = bit;
		}

		public String getBits() {
			return bits;
		}

		public int getBit() {
			return bit;
		}
		
		public boolean equals(Cell c) {
			return (bits.equals(c.bits) );
		}

		public int compareTo(Object a) {
			Cell c1 = (Cell) a;
			return this.num - c1.num;
		}
		public String toString() {
			return  Integer.toString(num) ;
			//return "valuse is " + bit + ", bits is " + bits + "\t" ;
		}
		
		public static void main(String[] args) {
			Avl_Gen<Cell> s = new Avl_Gen<Cell>();
			s.addKey(new Cell(2), true);
			s.addKey(new Cell(5), true);
			s.addKey(new Cell(1), true);
			s.addKey(new Cell(100), true);
			s.addKey(new Cell(1000), true);
			s.addKey(new Cell(20), true);
			s.addKey(new Cell(35), true);
			s.addKey(new Cell(70), true);
		}
	}
