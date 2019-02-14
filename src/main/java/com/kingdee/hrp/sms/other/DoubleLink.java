package com.kingdee.hrp.sms.other;

/**
 * 双向链表实现
 * <p>
 * 有一个头部位置first( 非具体元素)，其next指向第一个元素，prev指向null。整个链表是一个环,最后一个元素的next指向first
 *
 * @author le.xiao
 */
public class DoubleLink<E> {

    /**
     * 链表头
     */
    private Node<E> first;
    /**
     * 链表大小
     */
    private int size;

    public DoubleLink() {
        first = new Node<>(null, null, null);
        size = 0;
    }

    /**
     * 链表元素包装类
     *
     * @param <E>
     */
    private static class Node<E> {

        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * 在链表的前端插入
     *
     * @param e E
     */
    public void insertFront(E e) {

        if (size == 0) {
            // 第一个元素
            Node<E> node = new Node<E>(first, e, first);
            first.next = node;
            //first.prev = node;
            size++;
            return;
        }

        // 将first.next指向新加的元素，新加的元素next指向first.next

        Node<E> node = new Node<E>(first, e, first.next);

        first.next = node;

        size++;

    }

    /**
     * 插入到链表的后端
     *
     * @param e E
     */
    public void insertLast(E e) {

        if (size == 0) {
            // 第一个元素
            Node<E> node = new Node<E>(first, e, first);
            first.next = node;
            //first.prev = node;
            size++;
            return;
        }

        // 将first.next指向新加的元素，新加的元素next指向first.next
        Node<E> last = first;
        // 找出最后一个元素,最后一个元素的next指向first
        while (first != last.next) {
            last = last.next;
        }

        Node<E> node = new Node<E>(last, e, first);

        last.next = node;
        size++;
    }

    /**
     * 在指定位置
     *
     * @param e     待插入的元素
     * @param index 插入的位置
     */
    public void insert(E e, int index) {

        if (index > size || size < 0) {
            throw new RuntimeException("index大于链表size，插入失败");
        }
        // 找到插入位置
        Node<E> indexNode = first;
        for (int i = 1; i < index; i++) {
            indexNode = indexNode.next;
        }

        Node<E> node = new Node<E>(indexNode, e, indexNode.next);

        indexNode.next = node;
        indexNode.next.prev = node;

        size++;

    }

    public int size() {
        return size;
    }

    /**
     * 一次打印出链表元素
     */
    public void print() {

        if (size == 0) {
            System.out.println("链表没有元素");
        }
        Node<E> index = first;
        // 从first的next 依次遍历
        while (first != index.next) {
            index = index.next;
            System.out.println(index.item.toString());
        }
    }

    public static void main(String[] args) {

        DoubleLink<Integer> link = new DoubleLink<>();

        link.insertFront(2);
        link.insertFront(5);
        link.insertFront(1);
        link.insertLast(10);
        link.insert(3, 2);

        link.print();

        System.out.println(link.size());

    }

}
