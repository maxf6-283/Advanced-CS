public class Runner {
    public static void main(String[] args) {
        int n = 16;
		int pass = 0;
		for(int i=n; i>1; i/=2){
			pass++;
			System.out.println("pass count: " + pass);
		}
        System.out.println();
        
        n = 4;
		pass = 0;
		for(int i=1; i<n; i*=2){
            for(int j=0; j<n; j++)
			{
                pass++;
				System.out.println("pass count: " + pass);
			}
		}
        System.out.println();
        
        n = 3;
		pass = 0;
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				for(int k=0; k<n; k++){
                    
                    pass++;
					System.out.println("pass count: " + pass);
				}
			}
		}
        System.out.println();

    }
}
