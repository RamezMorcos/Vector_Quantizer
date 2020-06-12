package vector_quantizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class compression {
    ArrayList<int [][]>q=new ArrayList<>();
    ArrayList<block>finished=new ArrayList<block>();
    public static void writeImage(int[][] imagePixels,String outPath){

        BufferedImage image = new BufferedImage(imagePixels.length, imagePixels[0].length, BufferedImage.TYPE_INT_RGB);
        for (int y= 0; y < imagePixels.length; y++) {
            for (int x = 0; x < imagePixels[y].length; x++) {
                int value =-1 << 24;
                value= 0xff000000 | (imagePixels[y][x]<<16) | (imagePixels[y][x]<<8) | (imagePixels[y][x]);
                image.setRGB(x, y, value);

            }
        }

        File ImageFile = new File(outPath);
        try {
            ImageIO.write(image, "jpg", ImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void quanizer(int [][]array,int a ,int c){
        int l = 0;
        int w = 0;
        int length = array.length / c;
        length = length * c;
        int length2 = array[0].length / a;
        length2 = length2 * a;

        for (int s = 0; s < length; s += a) {
            for (int h = 0; h < length2; h += c) {
                int one = s + a;
                int two = h + c;
                int [][] b = new int[a][c];
                for (int i = s; i < one; i++) {
                    for (int j = h; j < two; j++) {
                        b[l][w] = array[i][j];
                        w++;
                    }
                    w = 0;
                    l++;
                }
                w = 0;
                l = 0;
                q.add(b);
            }
        }
//        int size=0;
//        size=pixels.length*pixels.length/(hieght*width);
//        for(int i=0;i<size;i++){
//            int [][]s=new int[hieght][width];
//            q.add(s);
//        }
//            int j=0;
//            int y=0;
//            int m=0;
//            int n=0;
//            int i=0;
//            int x=i;
//            int o=0;
//        int c_hieght=0,c_width=0;
//            for ( i = 0; i < pixels.length; i+=hieght) {
//                y=j;
//                x=i;
//
//                for ( j = 0; j < pixels.length; j++) {
//                        if (c_width == width-1&&c_hieght==hieght-1){
//                            q.get(o)[m][n]=pixels[i][j];
//                            o++;
//
//                            i=x;
//                            y=j+1;
//                            c_hieght=0;
//                            c_width=0;
//                            m=0;
//                            n=0;
//                    }
//                        else if (c_width == width-1 ) {
//                            q.get(o)[m][n]=pixels[i][j];
//                        c_hieght++;
//                        i++;
//
//                        j -=width;
//                        m++;
//                        n = 0;
//                        c_width=0;
//                    }
//                    else{
//                    q.get(o)[m][n]=pixels[i][j];
//                    n++;
//                    c_width++;
//                        }
//                }
//            }

    }


    public void compress(int [][] pixels,int hieght,int width,int codebook) throws FileNotFoundException {
        quanizer(pixels, hieght,width);
        ArrayList<node>spliting=new ArrayList<>();
        boolean x=true;
        int size=pixels.length*pixels.length/(hieght*width);
        int c=0;
        while(x){
            if(c==0){
                node n=new node();
                n.number=new Double[hieght][width];
                n.right=new node();
                n.right.number=new Double[hieght][width];
                n.left=new node();
                n.left.number=new Double[hieght][width];
                n.numbers=new ArrayList<>();
                n.right.numbers=new ArrayList<>();
                n.left.numbers=new ArrayList<>();
                for(int j=0;j<hieght;j++){
                for(int l=0;l<width;l++){
                    n.number[j][l]=0.0;
                    for(int i=0;i<q.size();i++){
                        n.number[j][l]+=q.get(i)[j][l];
                    }
                    n.number[j][l]=n.number[j][l]/size;

                }}
                for (int i=0;i<hieght;i++){
                    for (int j=0;j<width;j++){
                        double val=n.number[i][j].intValue();
                        n.right.number[i][j]=val+1;
                        n.left.number[i][j]=val;

                    }
                }

                for (int i=0;i<q.size();i++){
                    int right=0,left=0;
                    for(int j=0;j<hieght;j++){
                        for (int l=0;l<width;l++){
                            right+=(q.get(i)[j][l]-n.right.number[j][l])*(q.get(i)[j][l]-n.right.number[j][l]);
                            left+=(q.get(i)[j][l]-n.left.number[j][l])*(q.get(i)[j][l]-n.left.number[j][l]);
                        }
                    }
                    if(right<=left){
                         Integer [][]s=new Integer[hieght][width];
                        for(int j=0;j<hieght;j++){
                            for (int l=0;l<width;l++){
                                s[j][l]=q.get(i)[j][l];
                            }
                        }
                        n.right.numbers.add(s);
                    }
                    if(right>left){
                        Integer [][]s=new Integer[hieght][width];
                        for(int j=0;j<hieght;j++){
                            for (int l=0;l<width;l++){
                                s[j][l]=q.get(i)[j][l];
                            }
                        }
                        n.left.numbers.add(s);
                    }

                }
                spliting.add(n.left);
                spliting.add(n.right);
                c++;

            }

            else{
                int siz=spliting.size();
                for(int k=0;k<siz;k++){
    node n=new node();
    n.number=new Double[hieght][width];
    n.right=new node();
    n.right.number=new Double[hieght][width];
    n.left=new node();
    n.left.number=new Double[hieght][width];
    n.numbers=new ArrayList<>();
    n.right.numbers=new ArrayList<>();
    n.left.numbers=new ArrayList<>();
    for(int j=0;j<hieght;j++){
        for(int l=0;l<width;l++){
            n.number[j][l]=0.0;
            for(int i=0;i<spliting.get(k).numbers.size();i++){
                n.number[j][l]+=spliting.get(k).numbers.get(i)[j][l];

            }
            n.number[j][l]=n.number[j][l]/spliting.get(k).numbers.size();
            //System.out.println(n.number[j][l]);
        }}
    for (int i=0;i<hieght;i++){
        for (int j=0;j<width;j++){
            double val=n.number[i][j].intValue();
            n.right.number[i][j]=val+1;
            n.left.number[i][j]=val;
        }
    }

    for (int i=0;i<spliting.get(k).numbers.size();i++){
        int right=0,left=0;
        for(int j=0;j<hieght;j++){
            for (int l=0;l<width;l++){
                right+=(spliting.get(k).numbers.get(i)[j][l]-n.right.number[j][l])*(spliting.get(k).numbers.get(i)[j][l]-n.right.number[j][l]);
                left+=(spliting.get(k).numbers.get(i)[j][l]-n.left.number[j][l])*(spliting.get(k).numbers.get(i)[j][l]-n.left.number[j][l]);
            }
        }
        if(right<left){
            Integer [][]s=new Integer[hieght][width];
            for(int j=0;j<hieght;j++){
                for (int l=0;l<width;l++){
                    s[j][l]=spliting.get(k).numbers.get(i)[j][l];
                }
            }
            n.right.numbers.add(s);
        }
        if(right>left){
            Integer [][]s=new Integer[hieght][width];
            for(int j=0;j<hieght;j++){
                for (int l=0;l<width;l++){
                    s[j][l]=spliting.get(k).numbers.get(i)[j][l];
                }
            }
            n.left.numbers.add(s);
        }

    }
    spliting.add(n.left);
    spliting.add(n.right);


}

                for (int i=0;i<siz;i++){
                    spliting.remove(0);
                }
                if(spliting.size()==codebook){
                x=false;
                }
            }

        }


    /*    for(int i=0;i<spliting.size();i++){
            for (int j=0;j<hieght;j++){
                for(int k=0;k<width;k++){
                    spliting.get(i).number[hieght][width]=0.0;
                    for(int l=0;l<spliting.get(i).numbers.size();l++){
                        spliting.get(i).number[hieght][width]+=spliting.get(i).numbers.get(l)[hieght][width];
                    }
                    spliting.get(i).number[hieght][width]=spliting.get(i).number[hieght][width]/spliting.get(i).numbers.size();
                }
            }
        }*/


        for(int i=0;i<spliting.size();i++){
            spliting.get(i).t=i;
        }

for (int i=0;i<q.size();i++){
            for (int j=0;j<spliting.size();j++){
                for(int k=0;k<spliting.get(j).numbers.size();k++){
                    int count=0;
                    for(int row=0;row<hieght;row++){
                        for (int col=0;col<width;col++){
                            if(q.get(i)[row][col]==spliting.get(j).numbers.get(k)[row][col]){
                                count++;

                            }
                        }
                    }
                    int [][]xy=new int[hieght][width];
                    if(count==hieght*width){

                        block b=new block();
                        b.t=spliting.get(j).t;
                        b.q=new int[hieght][width];
                        for(int row=0;row<hieght;row++){
                            for (int col=0;col<width;col++){
                             b.q[row][col]=q.get(i)[row][col];

                            }
                        }
                        finished.add(b);
                        break;
                    }
                }
            }

}




        PrintWriter write = new PrintWriter("q.txt");
        PrintWriter write1 = new PrintWriter("r.txt");
        PrintWriter write2=new PrintWriter("width_length.txt");
        String text="";
        for (int i=0;i<finished.size();i++){
            System.out.println(finished.get(i).t);
            text+=finished.get(i).t;
            text+="|";
        }
        write.print(text);
        write.close();
        String text1="";
        for(int i=0;i<spliting.size();i++){
            for(int row=0;row<hieght;row++){
                for (int col=0;col<width;col++ ){
                    text1+=spliting.get(i).number[row][col];
                    text1+="|";
                }
            }
            text1+="#";
        }
        write1.print(text1);
        write2.print(hieght);
        write2.print('|');
        write2.print(width);
        write2.print('#');
        write2.close();
        write1.close();

//        for(int i=0;i<q.size();i++){
//           for(int j=0;j<spliting.size();j++){
//                for (int k=0;k<spliting.get(j).numbers.size();k++){
//                    int s=0;
//                    for(int row=0;row<hieght;row++){
//                        for(int col=0;col<width;col++){
//                            if(q.get(i)[row][col]==spliting.get(j).numbers.get(k)[row][col]){
//                                s++;
//                            }
//                        }
//                    }
//                    if((hieght*width)==s){
//                        for(int row=0;row<hieght;row++){
//                            for (int col=0;col<width;col++){
//                                q.get(i)[row][col]=spliting.get(j).t;
//                            }
//                        }
//                        i++;
//                        j=0;
//                        k=0;
//                    }
//                }
//            }
//        }


    }
    public void decompress() throws FileNotFoundException {
        ArrayList<block>code_book=new ArrayList<>();
        int width=0;
        int length=0;
        String text="";
        String t="";
        File f = new File("q.txt");
        try (Scanner in = new Scanner(f)) {


            while (in.hasNextLine()) {
                text += in.nextLine();
            }
        }

        for(int i=0;i<text.length();i++){
            if(text.charAt(i)=='|'){
                int x=Integer.parseInt(t);


                block b=new block();
                b.t=x;
                code_book.add(b);
                t="";

            }
            else{
                t+=text.charAt(i);
            }
        }
        text="";
        File f2 = new File("width_length.txt");
        try (Scanner in = new Scanner(f2)) {
            while (in.hasNextLine()) {
                text += in.nextLine();
            }
        }
        for(int i=0;i<text.length();i++){
            if(text.charAt(i)=='|'){
                length =Integer.parseInt(t);
                t="";

            }
            else if(text.charAt(i)=='#'){
                width =Integer.parseInt(t);
                t="";
            }
            else{
                t+=text.charAt(i);
            }
        }

text="";
        File f1 = new File("r.txt");
        try (Scanner in = new Scanner(f1)) {
            while (in.hasNextLine()) {
                text += in.nextLine();
            }
        }
     /*   String s="";
        for(int i=0;i<text.length();i++){

            if(text.charAt(i)=='#'){

//                System.out.println(s);
                block b=new block();
                b.q=new int [length][width];
                String k="";
                int j=0;
                for(int row=0;row<length;row++){
                    for (int col=0;col<width;col++){
                while(j<s.length()){
                    if(s.charAt(j)=='|'){
                        int x=Integer.parseInt(k);
                        b.q[row][col]=x;
                        k="";
                        j++;
                        break;
                    }
                    else{
                        k+=s.charAt(j);
                        j++;
                    }

                }}}
            }
            else{
                s+=text.charAt(i);
            }
        }*/


    }
    public static void main(String[] args) throws FileNotFoundException {
        int[][] pixels={ { 1, 2, 7, 9, 4, 11 }, { 3, 4, 6, 6, 12, 12 }, { 4, 9, 15, 14, 9, 9 }, { 10, 10, 20, 18, 8, 8 },
			{ 4, 3, 17, 16, 1, 4 }, { 4, 5, 18, 18, 5, 6 } };
compression x=new compression();
x.compress(pixels,2,2,4);
x.decompress();


    }
}
