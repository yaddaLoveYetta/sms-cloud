package com.kingdee.hrp.sms.other;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 二叉搜索树实现
 * <p>
 * 二叉搜索树的特点是，一个节点的左子节点的关键字值小于这个节点，
 * 右子节点的关键字值大于或等于这个父节点
 *
 * @author le.xiao
 */
public class BinaryTree<E extends Comparable> {

    /**
     * 根节点
     */
    Node<E> root;

    /**
     * 树节点
     *
     * @param <E>
     */
    private class Node<E extends Comparable> implements Comparable<Node<E>> {

        /**
         * 数据
         */
        private E data;
        /**
         * 左子树
         */
        private Node<E> left;
        /**
         * 右子树
         */
        private Node<E> right;

        public Node(E data) {
            this.data = data;
        }

        @Override
        public int compareTo(Node<E> e) {
            return this.data.compareTo(e.data);
        }
    }

    public BinaryTree() {
        root = null;
    }

    /**
     * 增加一个树节点
     * <p>
     * 找到要插入的位置之后，将父节点的左子节点或者右子节点指向新节点即可
     *
     * @param element E
     */
    public void insert(E element) {

        Node<E> node = new Node<>(element);

        if (root == null) {
            root = node;
            return;
        }

        Node<E> cur = root;

        while (true) {

            if (node.compareTo(cur) >= 0) {
                // 插入的节点大于当前节点-在右边
                if (cur.right == null) {
                    // 没有右子树-cur就是要插入节点的父节点
                    cur.right = node;
                    return;
                }

                // 当前的节点跳到cur.right继续比较
                cur = cur.right;
            } else if (node.compareTo(cur) < 0) {
                // 插入的节点小于当前节点-在左边
                if (cur.left == null) {
                    // 没有左子树-cur就是要插入节点的父节点
                    cur.left = node;
                    return;
                }

                // 当前的节点跳到cur.left继续比较
                cur = cur.left;
            }

        }

    }

    /**
     * 前序遍历
     * <p>
     * 先访问自己，再访问左子节点，最后访问右子节点
     *
     * @param node 起始节点
     */
    public void preOrder(Node node) {

        if (node != null) {
            System.out.println(node.data);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    /**
     * 中序遍历
     * <p>
     * 先访问左子节点，再访问自己，最后访问右子节点
     * <p>
     * 中序遍历二叉搜索树会使所有的节点按关键字升序被访问到
     *
     * @param node 起始节点
     */
    public void inOrder(Node node) {

        if (node != null) {
            //先对当前节点的左子树对进行中序遍历
            inOrder(node.left);
            //然后访问当前节点
            System.out.println(node.data);
            //最后对当前节点的右子树对进行中序遍历
            inOrder(node.right);
        }
    }

    /**
     * 后续遍历
     * <p>
     * 先访问左子节点，再访问自己，最后访问右子节点
     *
     * @param node 起始节点
     */
    public void postOrder(Node node) {

        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.data);
        }

    }

    /**
     * 查找节点是否存在，不存在返回null
     *
     * @param e 节点数据
     * @return Node<E>
     */
    public Node<E> find(E e, Node cur) {

        Node<E> node = new Node<>(e);

        if (cur != null) {
            if (node.compareTo(cur) == 0) {
                // 多个相同值返回第一个
                return cur;
            } else if (node.compareTo(cur) > 0) {
                return find(e, cur.right);
            } else {
                return find(e, cur.left);
            }
        }

        return null;
    }

    /**
     * 查找是否存在子节点
     *
     * @param e 节点数据
     * @return true or false
     */
    public boolean isExist(E e) {

        // 从root开始查找
        return isExist(e, root);
    }

    /**
     * 查找是否存在子节点
     *
     * @param e   节点数据
     * @param cur 开始查找的节点
     * @return true or false
     */
    private boolean isExist(E e, Node cur) {

        Node<E> node = new Node<>(e);

        if (cur != null) {
            if (node.compareTo(cur) == 0) {
                // 多个相同值返回第一个
                return true;
            } else if (node.compareTo(cur) > 0) {
                return isExist(e, cur.right);
            } else {
                return isExist(e, cur.left);
            }
        }

        return false;
    }

    /**
     * 删除一个节点
     *
     * @param e 节点数据
     * @return true:删除成功 false:删除失败，如不存在删除节点
     */
    public boolean delete(E e) {

        Node<E> node = new Node<>(e);

        // 要删除的节点
        Node<E> cur = root;
        // 要删除节点的父节点
        Node<E> parent = null;
        // 要删除节点cur是parent的左子节点还是右子节点
        NodeType nodeType = NodeType.LEFT;

        if (cur == null) {
            throw new RuntimeException("树中没有节点，不能删除");
            // return false;
        }

        while (node.compareTo(cur) != 0) {

            // cur设置为parent，继续比较右子节点
            parent = cur;

            if (node.compareTo(cur) > 0) {
                nodeType = NodeType.RIGHT;
                cur = cur.right;
            } else if (node.compareTo(cur) < 0) {
                nodeType = NodeType.LEFT;
                cur = cur.left;
            }

            if (cur == null) {
                //没有找到要删除的节点
                return false;
            }
        }

        if (cur.left == null && cur.right == null) {
            // 删除节点没有左子节点也没有右子节点(节点为叶子节点)，直接删除即可,将parent的left或right设置为null
            if (cur.compareTo(root) == 0) {
                root = null;
            }
            if (nodeType == NodeType.LEFT) {
                parent.left = null;
            } else {
                parent.right = null;
            }

            return true;
        }

        if (cur.left != null && cur.right == null) {
            // 删除节点cur有左子节点没有右子节点，将parent指向cur的左子节点即可
            if (cur.compareTo(root) == 0) {
                root = cur.left;
            } else if (nodeType == NodeType.LEFT) {
                parent.left = cur.left;
            } else {
                parent.right = cur.left;
            }

            return true;
        }

        if (cur.left == null && cur.right != null) {

            // 删除节点cur没有左子节点有右子节点.
            // cur.parent指向cur.right,改方法采用第2中方式
            if (cur == root) {
                root = cur.right;
            } else if (nodeType == NodeType.LEFT) {
                parent.left = cur.right;
            } else {
                parent.right = cur.right;
            }

            return true;

        }

        if (cur.left != null && cur.right != null) {
            // 删除节点cur有左子节点也有右子节点.
            // 1:找到cur右节点数的最小值node_min->将cur的parent指向node_min->将cur的right的left指向node_min的right->将node_min的right指向cur的right

            // 保存删除节点的left的最大值
            Node leftMaxNode = findMax(cur.left);

            if (cur == root) {
                root = cur.left;
            } else if (nodeType == NodeType.LEFT) {
                parent.left = cur.left;
            } else {
                parent.right = cur.left;
            }

            leftMaxNode.right = cur.right;

            return true;
        }

        return false;
    }

    /**
     * 查找最小值
     *
     * @param node 从此节点开始查找最大值(已此节点为root查找) ,如果node==null 将从root开始查找
     * @return 最小值
     */
    public Node<E> findMin(Node node) {

        if (null == root) {
            throw new RuntimeException("数中没有节点");
        }

        if (null == node) {
            node = root;
        }

        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    /**
     * 查找最大值
     *
     * @param node 从此节点开始查找最大值(已此节点为root查找) ,如果node==null 将从root开始查找
     * @return 最大值
     */
    public Node<E> findMax(Node node) {

        if (null == root) {
            throw new RuntimeException("数中没有节点");
        }

        if (null == node) {
            node = root;
        }

        while (node.right != null) {
            node = node.right;
        }

        return node;
    }

    public static void main(String[] args) {
        BinaryTree<Student> binaryTree = new BinaryTree<>();

        binaryTree.insert(new Student("g", 30));
        binaryTree.insert(new Student("m", 38));
        binaryTree.insert(new Student("j", 35));
        binaryTree.insert(new Student("k", 36));
        binaryTree.insert(new Student("l", 37));
        binaryTree.insert(new Student("h", 33));
        binaryTree.insert(new Student("i", 34));
        binaryTree.insert(new Student("c", 20));
        binaryTree.insert(new Student("e", 25));
        binaryTree.insert(new Student("a", 15));
        binaryTree.insert(new Student("d", 24));
        binaryTree.insert(new Student("f", 26));
        binaryTree.insert(new Student("b", 18));

/*        System.out.println("==============前序遍历结果================");
        binaryTree.preOrder(binaryTree.root);
        System.out.println("==============中序遍历结果================");
        binaryTree.inOrder(binaryTree.root);
        System.out.println("==============后序遍历结果================");
        binaryTree.postOrder(binaryTree.root);

        System.out.println("==============判断是否存在================");
        System.out.println(binaryTree.isExist(new Student("yadda", 20)));

        System.out.println("==============查找结果================");
        System.out.println(binaryTree.find(new Student("yadda", 20), binaryTree.root).data);

        System.out.println("==============删除节点================");
        System.out.println(binaryTree.delete(new Student("yadda", 25)));*/

        System.out.println("==============中序遍历结果================");
        binaryTree.inOrder(binaryTree.root);
        System.out.println("==============删除18后中序遍历结果================");
        binaryTree.delete(new Student("yadda", 18));
        binaryTree.inOrder(binaryTree.root);

        System.out.println("==============删除24后中序遍历结果================");
        binaryTree.delete(new Student("yadda", 24));
        binaryTree.inOrder(binaryTree.root);

        System.out.println("==============删除38后中序遍历结果================");
        binaryTree.delete(new Student("yadda", 38));
        binaryTree.inOrder(binaryTree.root);

        System.out.println("==============删除36后中序遍历结果================");
        binaryTree.delete(new Student("yadda", 36));
        binaryTree.inOrder(binaryTree.root);

        System.out.println("==============删除35后中序遍历结果================");
        binaryTree.delete(new Student("yadda", 35));
        binaryTree.inOrder(binaryTree.root);

        System.out.println("==============删除30后中序遍历结果================");
        binaryTree.delete(new Student("yadda", 30));
        binaryTree.inOrder(binaryTree.root);

    }

    /**
     * 节点类型
     */
    private enum NodeType {
        LEFT, RIGHT;
    }
}

@Getter
@Setter
@ToString
class Student implements Comparable<Student> {

    private String name;
    private int age;

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.age, other.age);
    }
}
