//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    TaskQueue
// Course:   CS 300 Spring 2024
//
// Author:   Karl Haidinyak
// Email:    haidinyak@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    N/A
// Partner Email:   N/A
// Partner Lecturer's Name: N/A
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         N/A
// Online Sources:  N/A
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * Makes a prioritized task manager using a prioritized queue
 *
 * @author karl haidinyak
 */
public class TaskQueue {

    /**
     * A max-heap array containing all the Tasks
     */
    private Task[] heapData;

    /**
     * The Comparison Criteria being used
     */
    private CompareCriteria priorityCriteria;

    /**
     * The size of the max-heap array
     */
    private int size;

    /**
     * Constructs a new instance of TaskQueue with the given capacity and Comparison Criteria
     *
     * @param capacity the max capacity of the max-heap array
     * @param priorityCriteria the comparison criteria that this instance will use
     * @throws IllegalArgumentException if the capacity is less than or equal to zero
     */
    public TaskQueue(int capacity, CompareCriteria priorityCriteria) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity cannot be less than or equal to zero!");
        heapData = new Task[capacity];
        this.priorityCriteria = priorityCriteria;
    }

    /**
     * Returns the priorityCriteria
     *
     * @return the priorityCriteria
     */
    public CompareCriteria getPriorityCriteria() {return this.priorityCriteria;}

    /**
     * Returns whether the max-heap array is empty or not
     *
     * @return true if the size is equal to zero or false otherwise
     */
    public boolean isEmpty() {return size == 0;}

    /**
     * Returns the size of the max-heap array
     *
     * @return the size of the max-heap array
     */
    public int size() {return this.size;}

    /**
     * Returns the task at the root of the max-heap without removing it
     *
     * @return the task at the root of the max-heap (index 0 of the max-heap array)
     * @throws NoSuchElementException if the max-heap array is empty
     */
    public Task peekBest() {
        if (isEmpty()) throw new NoSuchElementException("The TaskQueue is empty!");
        return heapData[0];
    }

    /**
     * Adds the new task to the end of the max-heap array and calls to the percolateUp() method to
     * resolve any max-heap violations (the new task being greater than its parent task)
     *
     * @param newTask the task to be added
     * @throws IllegalArgumentException if the new task is already complete
     * @throws IllegalStateException if the max-heap array is full
     */
    public void enqueue(Task newTask) {
        if (newTask.isCompleted()) throw new IllegalArgumentException("Task is already completed!");
        if (size == heapData.length) throw new IllegalStateException("Heap is full!");
        final int ADDED_INDEX = size();

        heapData[ADDED_INDEX] = newTask;
        ++size;
        percolateUp(ADDED_INDEX);


    }

    /**
     * Resolves any max-heap violations (a child task being greater than its parent task)
     *
     * @param index the index of the violation
     */
    protected void percolateUp(int index) {
        //Base-case where the added Task is now the root of the max-heap
        if (index == 0) return;

        final int PARENT_INDEX = (index - 1) / 2;

        //Base-case where the parent Task is greater or equal to the addedTask and no
        //heap rules are violated
        if (heapData[PARENT_INDEX].compareTo(heapData[index], priorityCriteria) >= 0) return;

        Task savedTask = heapData[PARENT_INDEX];
        Task changedTask = heapData[index];
        heapData[PARENT_INDEX] = changedTask;
        heapData[index] = savedTask;

        //Recursive case where we call percolateUp with the parent index to ensure no
        //heap rules are being violated
        percolateUp(PARENT_INDEX);
    }

    /**
     * Removes the highest priority task (at index 0 of the max-heap array) and calls to
     * percolateDown() to resolve any max-heap violations (a child task being greater than
     * its parent task)
     *
     * @return the dequeued task
     * @throws NoSuchElementException if the max-heap is empty
     */
    public Task dequeue() {
        if (isEmpty()) throw new NoSuchElementException("The heap is empty!");
        final int LAST_TASK = size() - 1;
        Task highestPriority = heapData[0];

        heapData[0] = heapData[LAST_TASK];
        heapData[LAST_TASK] = null;
        --size;
        percolateDown(0);
        return highestPriority;

    }

    /**
     * Resolves any max-heap violations (a child task being greater than its parent task)
     *
     * @param index the index of the violation
     */
    protected void percolateDown(int index) {
        final int LEFT_CHILD = (2 * index) + 1; //the root's left child (may not exist)
        final int RIGHT_CHILD = (2 * index) + 2; //the root's right child (may not exist)
        Task highestChild; //the child with the highest value
        int highestIndex; //the index of the highest child

        //base case where either the left or right children are out of bounds
        if(LEFT_CHILD >= size() || RIGHT_CHILD >= size()) return;

        //Finds the child with the highest value given the comparison criteria
        if (heapData[LEFT_CHILD].compareTo(heapData[RIGHT_CHILD], priorityCriteria) > 0) {
            highestChild = heapData[LEFT_CHILD];
            highestIndex = LEFT_CHILD;
        }
        else {
            highestChild = heapData[RIGHT_CHILD];
            highestIndex = RIGHT_CHILD;
        }

        //Base case where the task at the index is greater or equal to its highest child
        if (heapData[index].compareTo(highestChild, priorityCriteria) >= 0) return;

        Task savedTask = heapData[index];
        heapData[index] = highestChild;
        heapData[highestIndex] = savedTask;

        //Recurs using the index of the highest child as input
        percolateDown(highestIndex);
    }

    /**
     * Reprioritizes the max-heap array with the given comparison criteria
     *
     * @param priorityCriteria the new comparison criteria
     */
    public void reprioritize(CompareCriteria priorityCriteria) {
        this.priorityCriteria = priorityCriteria;
        Task[] originalArray = getHeapData();
        heapData = new Task[size];
        size = 0;

        for (Task task : originalArray) {
            if (task != null) enqueue(task);
        }
    }

    /**
     * Returns a deep copy of the heapData array
     *
     * @return a deep copy of the heapData array
     */
    public Task[] getHeapData() {
        Task[] copyArray = new Task[heapData.length];

        System.arraycopy(heapData, 0, copyArray, 0, heapData.length);
        return copyArray;
    }
}
