import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class tree {
    public static void main(String[] args) {
        //处理数据的标准输入
        int n = StdIn.readInt();              //数组的大小
        int max = 0;
        int l = 1;
        for(int w = 0; w < 14; w++){
            if(Math.pow(2,w-1) < n && n < Math.pow(2,w)){
                max = w;
            }
        }
        int a1 = (int)Math.pow(2,max-1);
        int a2 = (int)Math.pow(2,max);
        int true_n = (int) (Math.pow(2,max)-1); //补完之后树一共有多少元素
        int[] tree = new int[true_n];
        for(int i = 0; i < n; i++){
            tree[i] = StdIn.readInt();
        }                                     //树的元素录入
        for(int a = n; a < true_n; a++ ){
            tree[a] = -1;
        }                                     //将树补完

        int k = StdIn.readInt();              //删除的结点数量
        int[] key = new int[k];               //结点的数组
        for(int i = 0; i < k; i++){           //数组当中的内容
            key[i] = StdIn.readInt();
        }

        //判断树是否是二叉搜索树
        int counter = 0;
        for(int j = 1; 2*j+1 <= n; j++){
            if(tree[j * 2] != -1){
                if(tree[j * 2 - 1] < tree[j - 1] && tree[j * 2] > tree[j - 1]){
                    counter++;
                }
            } else{
                counter++;
            }
        }
        if(counter == (n-1)/2){
            StdOut.println("Yes");
        } else{
            StdOut.println("No");
            System.exit(0);
        }

        remove(tree, key, true_n, k, l);
        //将删改后的树作为数组输出
        int count = 0;
        for(int m = a1-1; m < a2-1; m++){

            if(tree[m] == -1){
                count++;
            }
            if(count == a2-a1){
                max--;
            }
        }
        if(n<(int)Math.pow(2,max)){
            for(int q = 0; q < n; q++){
                StdOut.print(tree[q] + " ");
            }
        } else{
            for(int q = 0; q < (int)Math.pow(2,max)-1; q++){
                StdOut.print(tree[q] + " ");
            }
        }
    }

    //进行删除节点的操作，考虑到删除结点可能子子孙孙无穷尽，所以要用递归来写，创建一个函数
    public static void remove(int[] tree, int[] key, int true_n, int k, int l){
        for(int m = 0; m < k;m++){
            int var = key[m];
            //树中不包含此key就不用删除，直接跳过
            if(var > true_n){
                continue;
            }
            //树中包含key时的操作
            for(l = 1; l <= true_n; l++){
                if(tree[l-1] == var){
                    //第一种情况，没有子结点
                    if(2*l > true_n){
                        tree[l-1] =-1;
                    }
                    //第二种情况，一个子结点，分左右
                    else if(tree[2*l-1] != -1 && tree[2*l] == -1){
                        int plus = 1;
                        while(2*l<true_n){
                            for(int i = 1; i <= plus; i++){
                                tree[l+i-2] = tree[2*l+i-2];
                                tree[2*l+i-2] = -1;
                            }
                            l = 2*l;
                            plus = plus*2;
                        }
                        //左儿子接下来的操作要确保删除后仍是二叉搜索树
                    }
                    else if(tree[2*l-1] == -1 && tree[2*l] != -1){
                        tree[l-1] = tree[2*l];
                        tree[2*l] = -1;
                        l = l * 2;
                        int plus = 1;
                        while(2*l<true_n){
                            for(int i = 1; i <= plus; i++){
                                tree[l+i-2] = tree[2*l+i-2];
                                tree[2*l+i-2] = -1;
                            }
                            l = 2*l;
                            plus = plus*2;
                        }
                        //右儿子接下来的操作要确保删除后仍是二叉搜索树
                    }
                    //第三种情况，左右都有子结点
                    else{
                        int b = 2*l;
                        while( b*2+1 < true_n && tree[b*2+1] != -1){
                            b = b*2+1;
                        }
                        tree[l-1] = tree[b];
                        tree[b] = -1;
                        //接下来的操作要确保删除后仍是二叉搜索树
                        if(2*b+2<true_n && tree[2*b+2]!=-1){
                            tree[b] = tree[2*b+2];
                            tree[2*b+2] = -1;
                        }
                    }
                }
            }
        }
    }
}

