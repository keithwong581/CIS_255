
class Yes { 
   public static boolean inRange(int myArray[], int min, int max) { 
      int range = max - min; 
        
      for (int i = 0; i < range; i++) { 
         if (Math.abs(myArray[i]) >= min && Math.abs(myArray[i]) <= max) {      
            int z = Math.abs(myArray[i]) - min; 
               if (myArray[z] > 0) { 
            	   myArray[z] = myArray[z] * -1; 
              } 
          } 
      } 
        
      int count=0; 
  
         for (int i = 0; i <= range; i++) { 
            if (myArray[i] > 0) {
               return false; 
            }
            else {
               count++; 
            }
      } 
  
      if(count!= (range+1)) {
         return false;
      }
      return true; 
   }
}