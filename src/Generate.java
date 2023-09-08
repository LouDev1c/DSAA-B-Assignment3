import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Generate {
    public static void main(String[] args) {
        String file = "src/TestData.txt";
        generate(file);
    }

    public static void generate(String file) {
        int[] tree;
        try (BufferedWriter rn = new BufferedWriter(new FileWriter((file)))) {
            Random random = new Random();
            // 生成随机长度的数组
            int n = random.nextInt(1,10000);
            System.out.println(n);
            rn.write(Integer.toString(n));
            rn.newLine();
            tree = new int[n];
            // 创建一个空树，也就是先将所有位置都赋予-1
            Arrays.fill(tree, -1);
            // 使用insert方法在不同的位置上插入值
            for (int i = 1; i <= n; i++) {
                int value = random.nextInt(100000);
                insert(tree, value, 0);
                rn.write(Integer.toString(tree[i-1]));
                rn.write(" ");
            }
            rn.newLine();
            int k = random.nextInt(1,100);
            rn.write(Integer.toString(k));
            int[] key = new int[k];
            for(int i = 0; i < k ;i++){
                key[i] = random.nextInt(1,10000000);
                rn.write(Integer.toString(key[i]));
                rn.newLine();
            }
            System.out.println("Succeeded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //用递归的方法进行插值操作
    public static void insert(int[] tree, int value, int index){
        if (index >= tree.length) {
            return;
        }
        if (tree[index] == -1) {
            // 如果当前索引处的值为-1，表示这个位置是空的，可以插入值
            tree[index] = value;
        }
        if (value < tree[index]) {
            // 如果要插入的值小于当前索引处的值，将值插入到左子节点
            insert(tree, value, 2 * index + 1);
        }
        if(value > tree[index]) {
            // 如果要插入的值大于等于当前索引处的值，将值插入到右子节点
            insert(tree, value, 2 * index + 2);
        }
    }
}
