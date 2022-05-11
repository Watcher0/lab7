public class Main{
	public static int search(short[] a, short b){
		int l = 0;
		int r = a.length - 1;
		while(r-l>=0){
			int m = (r+l)/2;
			if (a[m] == b) {
				return m;
			} else if(a[m]<b){
					l = m + 1;
			}
			else{
				r = m -1;
			}
		}
		return -1;
	}
	public static void main(String[] args) {
		short a[] = new short[16];
		float x[] = new float[17];
		for (short i = 0; i<16; i++){
			a[i]= (short)(20-i);
		}
		float L = -8F;
		float R = 7F;
		for (int i = 0; i<17; i++){
			x[i] = L + (float) java.lang.Math.random()*(R-L);
		}
		double A[][] = new double[16][17];
		short check[] = { 6, 9, 10, 11, 15, 16, 18, 19 };
		for (int i = 0; i<16; i++){
			for (int j = 0; j<17; j++){
				float X = x[j];
				if(a[i] == 17){
					A[i][j]=Math.pow(((Math.pow(Math.E, X)-1D)/12D), (0.3333333D));
				} else if(search(check, a[i])>=0){
					A[i][j] = Math.pow((2F*Math.asin((X - 0.5F)/15F)*(3+Math.pow((X/(X-1F)), 3F))), (0.25F/(X+0.5F)));
				} else{
					A[i][j] = Math.pow(0.75F/Math.pow((0.66666667D)/Math.pow(2*X, 2), 3), 2);
				}
			}
		}
		for (int i = 0; i<16; i++){
			System.out.println(a[i]);
			for (int j = 0; j<17; j++){
				String st = String.format(" %.4f ", A[i][j]);
				System.out.print(st);
			}
			System.out.println();
		}
	}
}
