package com.kingdee.hrp.sms.other;

/**
 * 队列数据结构实现.
 * <p>
 * 队列是“先进先出”（FIFO，First InFirst Out）的数据结构
 *
 * @author le.xiao
 */
public class Queue {
    /**
     * 队列的长度
     * <p>
     * 已入队列的元素数量
     */
    private int length;
    /**
     * 队列的最大长度
     */
    private int maxSize;
    /**
     * 对头元素的下标
     */
    private int front;
    /**
     * 队尾元素的下标
     */
    private int rear;

    /**
     * 存储队列元素
     */
    private int[] queueArray;

    public Queue(int maxSize) {

        this.maxSize = maxSize;
        this.length = 0;
        this.front = 0;
        // 队尾初始指向-1
        this.rear = -1;

        queueArray = new int[maxSize];
    }

    /**
     * 向队列中加入一个元素
     *
     * @param e queue element
     */
    public void insert(int e) {
        if (this.isFull()) {
            throw new RuntimeException("队列已满，不能再插入元素");
        }

        if (rear == maxSize - 1 && front > 0) {
            // 如果队尾指针已到达数组的末端且对头元素已经出队列，则插入到数组的第一个位置(循环插入，循环读取)
            rear = -1;
        }

        queueArray[++rear] = e;
        length++;

    }

    /**
     * 从队列中拿出一个元素
     *
     * @return element
     */
    public int remove() {

        if (this.isEmpty()) {
            throw new RuntimeException("队列中没有元素可出列");
        }

        int element = queueArray[front++];
        length--;

        if (front == maxSize) {
            //如果队头指针已到达数组末端，则移到数组第一个位置(即下一个出列的是第0个元素)
            front = 0;
        }

        return element;

    }

    /**
     * 判断队列是否已满
     *
     * @return true or false
     */
    public boolean isFull() {
        return length == maxSize;
    }

    /**
     * 判断队列是否为空
     *
     * @return true or false
     */
    public boolean isEmpty() {
        return length == 0;
    }

    /**
     * 获取队列的长度
     *
     * @return queue size
     */
    public int size() {
        return length;
    }

    public static void main(String[] args) {
        Queue queue = new Queue(10);

        for (int i = 0; i < 10; i++) {
            queue.insert(i + 1);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(queue.remove());
        }
    }
}
