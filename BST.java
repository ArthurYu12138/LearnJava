import java.util.*;

public class BST{
    public static void main(String[] args){
        int[] data = {47, 11, 70, 3, 29, 51, 92, 19, 34, 59, 81, 96, 16, 24, 54, 68, 87, 52, 63};
        TreeNode test = new TreeNode();
        TreeNode a = new TreeNode(90);
        
        BSTTools.newBST(test, data);
        BSTTools.show(test);
        /*
        BSTTools.erase(test, 29);
        BSTTools.show(test);
        System.out.println(BSTTools.height(test));*/
        System.out.println(BSTTools.nextLarge(test, 71));
    }
}

class TreeNode{
    protected Integer value;
    protected TreeNode leftchild;
    protected TreeNode rightchild;
    protected TreeNode parent;
   
    public TreeNode(int value){
        this.value = value;
    }

    public TreeNode(){

    }

    @Override
    public String toString(){
        String v = this.value == null ? null : this.value.toString();
        String l = this.leftchild == null ? null : this.leftchild.value.toString();
        String r = this.rightchild == null ? null : this.rightchild.value.toString();
        String p = this.parent == null ? null : this.parent.value.toString();
        return "Current: " + v + " Parent: " + p + "  Left Child: " + l + "  Right Child: " + r;
    }
}

class BSTTools{
    /*
    public static int retrieve(TreeNode root){
        return root.value;
    }
    */
    public static void newBST(TreeNode root, int[] data){
        root.value = data[0];
        for(int i = 0; i < data.length; i++){
            BSTTools.insert(root, data[i]);
        }
    }
    public static void show(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(queue.peek() != null){
            TreeNode current = queue.poll();
            System.out.print(current.value + "  ");
            if(current.leftchild != null){
                queue.offer(current.leftchild);
            }
            if(current.rightchild != null){
                queue.offer(current.rightchild);
            }
        }
        System.out.println("");
    }
    public static boolean is_leaf(TreeNode root){
        if(root.leftchild == null && root.rightchild == null){
            return true;
        }
        return false;
    }

    public static int size(TreeNode root){
        if(BSTTools.is_leaf(root)){
            return 1;
        }
        else{
            return (root.leftchild == null ? 0 : BSTTools.size(root.leftchild)) 
                 + (root.rightchild == null ? 0 : BSTTools.size(root.rightchild)) 
                 + 1;
        }
    }

    public static int height(TreeNode root){
        if(BSTTools.is_leaf(root)){
            return 0;
        }
        else{
            int leftheight = (root.leftchild == null ? 0 : BSTTools.height(root.leftchild));
            int rightheight = (root.rightchild == null ? 0 : BSTTools.height(root.rightchild));
            return Math.max(leftheight, rightheight) + 1;
        }
    }

    public static int front(TreeNode root){
        return (root.leftchild == null) ? root.value : BSTTools.front(root.leftchild);
    }

    public static int back(TreeNode root){
        return (root.rightchild == null) ? root.value : BSTTools.back(root.rightchild);
    }

    public static boolean find(TreeNode root, int value){
        if(root.value == value){
            return true;
        }
        else{
            if(value < root.value){
                return (root.leftchild == null) ? false : BSTTools.find(root.leftchild, value);
            }
            else{
                return (root.rightchild == null) ? false : BSTTools.find(root.rightchild, value);
            }
        }
    }

    public static TreeNode findNode(TreeNode root, int value){
        if(root.value == value){
            return root;
        }
        else{
            if(value < root.value){
                return (root.leftchild == null) ? null : BSTTools.findNode(root.leftchild, value);
            }
            else{
                return (root.rightchild == null) ? null : BSTTools.findNode(root.rightchild, value);
            }
        }
    }
    
    public static boolean insert(TreeNode root, int value){
        if(BSTTools.find(root, value)){
            return false;
        }
        else{
            if(value < root.value){
                if(root.leftchild == null){
                    TreeNode k = new TreeNode(value);
                    k.parent = root;
                    root.leftchild = k;
                    return true;
                }
                else{
                    return BSTTools.insert(root.leftchild, value);
                }
            }
            else{
                if(root.rightchild == null){
                    TreeNode k = new TreeNode(value);
                    k.parent = root;
                    root.rightchild = k;
                    return true;
                }
                else{
                    return BSTTools.insert(root.rightchild, value);
                }
            }
        }
    }
    
    public static boolean erase(TreeNode root, int value){
        TreeNode temp = BSTTools.findNode(root, value);
        if(temp == null){
            return false;
        }
        else{
            if(BSTTools.is_leaf(temp)){
                if(temp.parent == null){  //only one node in a BST
                    root.value = null;
                    return true;
                }
                if(temp.value < temp.parent.value){
                    temp.parent.leftchild = null;
                }
                else{
                    temp.parent.rightchild = null;
                }
                return true;
            }
            
            else if(temp.leftchild != null && temp.rightchild != null){
                int rightmin = BSTTools.front(temp.rightchild);
                BSTTools.erase(temp.rightchild, rightmin);
                temp.value = rightmin;
                return true;
            }
            else{
                TreeNode child = (temp.leftchild == null) ? temp.rightchild : temp.leftchild;
                if(temp.parent == null){
                    temp = child;
                    temp.parent = null;
                    return true;
                }
                if(temp.value < temp.parent.value){
                    child.parent = temp.parent;
                    temp.parent.leftchild = child;
                }
                else{
                    child.parent = temp.parent;
                    temp.parent.rightchild = child;
                }
                return true;
            }
        }
    }
    public static int nextLarge(TreeNode root, int obj){
        if(root.value == obj){
            return (root.rightchild == null) ? obj : BSTTools.front(root.rightchild);
        }
        else if(root.value > obj){
            if(root.leftchild == null){
                return root.value;
            }
            else{
                int tmp = BSTTools.nextLarge(root.leftchild, obj);
                return (tmp == obj) ? root.value : tmp;
            }
        }
        else{
            return (root.rightchild == null) ? obj : BSTTools.nextLarge(root.rightchild, obj);
        }
    }
}