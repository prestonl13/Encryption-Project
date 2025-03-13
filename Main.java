
class Main {
  public static void main(String[] args) {
    (new Main()).init();
  }
  void print(Object o){ System.out.println(o);}
  void printt(Object o){ System.out.print(o);}

  void init(){

    // This example we are substituting all lower case 
    // letters to another lower case letter.
    char[] sub = new char[26];
    sub[0] = 'a';
    sub[1] = 'b';
    sub[2] = 'c';
    sub[3] = 'd';
    sub[4] = 'e';
    sub[5] = 'f';
    sub[6] = 'g';
    sub[7] = 'h';
    sub[8] = 'i';
    sub[9] = 'j';
    sub[10] = 'k';
    sub[11] = 'l';
    sub[12] = 'm';
    sub[13] = 'n';
    sub[14] = 'o';
    sub[15] = 'p';
    sub[16] = 'q';
    sub[17] = 'r';
    sub[18] = 's';
    sub[19] = 't';
    sub[20] = 'u';
    sub[21] = 'v';
    sub[22] = 'w';
    sub[23] = 'x';
    sub[24] = 'y';
    sub[25] = 'z';
	
    char[] sub2 = new char[26];
    sub2[0] = '▀';
    sub2[1] = '▁';
    sub2[2] = '▂';
    sub2[3] = '▃';
    sub2[4] = '▄';
    sub2[5] = '▅';
    sub2[6] = '▆';
    sub2[7] = '▇';
    sub2[8] = '█';
    sub2[9] = '▉';
    sub2[10] = '▊';
    sub2[11] = '▋';
    sub2[12] = '▌';
    sub2[13] = '▍';
    sub2[14] = '▎';
    sub2[15] = '▏';
    sub2[16] = '▐';
    sub2[17] = '▖';
    sub2[18] = '▗';
    sub2[19] = '▘';
    sub2[20] = '▙';
    sub2[21] = '▚';
    sub2[22] = '▛';
    sub2[23] = '▜';
    sub2[24] = '▝';
    sub2[25] = '▞';
    
    // Encoding message
    String file = Input.readFile("Original.txt");

    //reverse
    String encodedMsg1 = reverse(file);
    Input.writeFile("Encode1.txt", encodedMsg1);

    //atbash
    String encodedMsg2 = atbash(encodedMsg1);
    Input.writeFile("Encode2.txt", encodedMsg2);

    //block
    String encodedMsg3 = block(encodedMsg2, sub, sub2);
    Input.writeFile("Encode3.txt",encodedMsg3);

    //adding
    String encodedMsg4 = adding(encodedMsg3, sub2);
    Input.writeFile("Encode4.txt",encodedMsg4);
    
    //decoding message
    String file2 = Input.readFile("Encode4.txt");
    
    String decodedMsg1 = unadd(file2);
    Input.writeFile("Decode1.txt", decodedMsg1);
    
    String decodedMsg2 = unblock(decodedMsg1, sub, sub2);
    Input.writeFile("Decode2.txt", decodedMsg2);
    
    String decodedMsg3 = atbash(decodedMsg2);
    Input.writeFile("Decode3.txt", decodedMsg3);

    String decodedMsg4 = reverse(decodedMsg3);
    Input.writeFile("Decode4.txt", decodedMsg4);
    
    
  }  

 //reverse
  String reverse(String txt) {
    String[] words = txt.split("\\s+"); 
    String result = "";

    for (String word : words) {
        String bld = "";
        int half = word.length() / 2;

        // reverse the first half 
        for (int x = 0; x < half; x++) {
          bld = word.charAt(x) + bld;
        }

        // leave the second half
        for (int x = half; x < word.length(); x++) {
          bld += word.charAt(x);
        }

        // if nothing there add space
        if (result.length() > 0) {
          result += " ";
        }
        result += bld;
    }

    return result; 
  }
  
  //atbash
  String atbash(String txt) {
    String bld = "";
    for (int x = 0; x < txt.length(); x++) {
        char ch = txt.charAt(x);
        char newcode;
        if (Character.isUpperCase(ch)) {
            newcode = (char) ('Z' - (ch - 'A')); //  uppercase
        } else if (Character.isLowerCase(ch)) {
            newcode = (char) ('z' - (ch - 'a')); //  lowercase
        } else {
            newcode = ch; 
        }
        bld = bld + newcode; 
    }
    return bld;
}

  // block encoding
  String block(String txt, char[] sub, char[] sub2){
    String txtignore = txt.toLowerCase();
    String bld="";
    char ch ='\0';
    int index=0;
    for(int x=0; x < txtignore.length(); x++){
      ch = txtignore.charAt(x);
      index = indexOf(ch,sub);
      if(index!=-1){
        bld += sub2[index]; //if character is found replace with sub2 array
      }
      else{
        bld += ch; //if not found replace with regular character
      }
    }
    return bld;
  }
  
  String unblock(String txt, char[] sub, char[] sub2){
    String txtignore = txt.toLowerCase();
    String bld="";
    char ch ='\0';
    int index=0;
    for(int x=0; x < txtignore.length(); x++){
      ch = txtignore.charAt(x);
      index = indexOf(ch,sub2);
      if(index!=-1){
        bld += sub[index]; //if character is found replace with sub2 array
      }
      else{
        bld += ch; //if not found replace with regular character
      }
    }
    return bld;
  }
  

  // adding blocks
  String adding(String txt, char[] sub2){
    String[] words = txt.split("\\s+"); 
    String result = "";

    for (String word : words) {
        String bld = "";
        int half = word.length() % 2;

        // if even add one to front
        if (half == 0) {
          bld += sub2[randInt(0,25)];
          bld += word;
        } 
        // else odd add 2 to back
        else { 
          bld += word;
          bld += sub2[randInt(0,25)];
          bld += sub2[randInt(0,25)];
        }

        // if nothing there add space
        if (result.length() > 0) {
          result += " ";
        }
        result += bld;
    }

    return result; 
  }

  String unadd(String txt){
    String[] words = txt.split("\\s+"); 
    String result = "";

    for (String word : words) {
        String bld = "";
        int half = word.length() % 2;

        // if even remove one from front
        if (half == 0) {
          bld += word.substring(1);
        } 
        // else odd remove 2 from back
        else { 
          bld = word.substring(0, word.length()-2);
        }

        // if nothing there add space
        if (result.length() > 0) {
          result += " ";
        }
        result += bld;
    }

    return result; 
  }
  

  int indexOf(char ch, char[] arry){
    for(int x=0; x<=arry.length-1; x++){
      if(arry[x]==ch){
        return x;
      }
    }
    return -1;
  }
  int randInt(int lower, int upper){
    int range = upper - lower;
    return (int)(Math.random()*range+lower);
  }

}
