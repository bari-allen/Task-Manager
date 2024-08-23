//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    TaskQueue Tester
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
 * Tester methods for the TaskQueue class
 *
 * @author karl haidinyak
 */
public class TaskQueueTester {

    /**
     * Tests the compareTo() method in the Task class with respect to their ETAs
     *
     * @return false if any of the tests fail or true otherwise
     */
    public static boolean testCompareToTime() {
        Task task1 = new Task("Chrome", "Google Chrome",
                10, PriorityLevel.MEDIUM);
        Task task2 = new Task("Firefox", "Firefox", 20, PriorityLevel.MEDIUM);
        Task task3 = new Task("Canvas", "UW Canvas", 100, PriorityLevel.URGENT);
        Task task4 = new Task("Default", "default", 10, PriorityLevel.LOW);

        if (task1.compareTo(task2, CompareCriteria.TIME) >= 0) return false;
        if (task1.compareTo(task3, CompareCriteria.TIME) >= 0) return false;
        if (task3.compareTo(task2, CompareCriteria.TIME) <= 0) return false;
        if (task1.compareTo(task4, CompareCriteria.TIME) != 0) return false;

        return true;
    }

    /**
     * Tests the compareTo() method in the Task class with respect to their titles
     *
     * @return false if any of the tests fail or true otherwise
     */
    public static boolean testCompareToTitle() {
        Task task1 = new Task("A", "Task A", 10, PriorityLevel.MEDIUM);
        Task task2 = new Task("B", "Task B", 20, PriorityLevel.MEDIUM);
        Task task3 = new Task("A", "Task A", 40, PriorityLevel.MEDIUM);
        Task task4 = new Task("a", "Task a", 40, PriorityLevel.MEDIUM);

        if (task1.compareTo(task2, CompareCriteria.TITLE) <= 0) return false;
        if (task2.compareTo(task3, CompareCriteria.TITLE) >= 0) return false;
        if (task1.compareTo(task3, CompareCriteria.TITLE) != 0) return false;
        if (task1.compareTo(task4, CompareCriteria.TITLE) <= 0) return false;

        return true;
    }

    /**
     * Tests the compareTo() method in the Task class with respect to their priority levels
     *
     * @return false if any of the tests fail or true otherwise
     */
    public static boolean testCompareToLevel() {
        Task task1 = new Task("Chrome", "Google Chrome",
                10, PriorityLevel.OPTIONAL);
        Task task2 = new Task("Firefox", "Firefox", 20, PriorityLevel.MEDIUM);
        Task task3 = new Task("Canvas", "UW Cancas", 100, PriorityLevel.URGENT);
        Task task4 = new Task("Default", "Default Task", 10,
                PriorityLevel.OPTIONAL);

        if (task1.compareTo(task2, CompareCriteria.LEVEL) >= 0) return false;
        if (task1.compareTo(task3, CompareCriteria.LEVEL) >= 0 ) return false;
        if (task3.compareTo(task2, CompareCriteria.LEVEL) <= 0 ) return false;
        if (task1.compareTo(task4, CompareCriteria.LEVEL) != 0) return false;

        return true;
    }

    /**
     * Tests the enqueue() method in the TaskQueue class with respect to all the comparisons
     *
     * @return false if any of the tests fail or true otherwise
     */
    public static boolean testEnqueue() {
        Task task1 = new Task("X", "Default", 50, PriorityLevel.URGENT);
        Task task2 = new Task("a", "DEFAULT", 40, PriorityLevel.LOW);
        Task task3 = new Task("b", "dEFAULT", 60, PriorityLevel.OPTIONAL);
        Task task5 = new Task("A", "Completed", 55, PriorityLevel.HIGH);
        TaskQueue titleQueue = new TaskQueue(4, CompareCriteria.TITLE);
        TaskQueue levelQueue = new TaskQueue(4, CompareCriteria.LEVEL);
        TaskQueue timeQueue = new TaskQueue(4, CompareCriteria.TIME);
        TaskQueue fullQueue = new TaskQueue(1, CompareCriteria.LEVEL);
        TaskQueue completeQueue = new TaskQueue(1, CompareCriteria.TIME);
        Task[] array;
        int numTasks = 0;

        titleQueue.enqueue(task2);
        titleQueue.enqueue(task1);
        titleQueue.enqueue(task3);
        titleQueue.enqueue(task5);
        array = titleQueue.getHeapData();
        if (array[0] != task5) return false;
        if (array[0].compareTo(array[1],CompareCriteria.TITLE) < 0
                || array[0].compareTo(array[2], CompareCriteria.TITLE) < 0) return false;
        if (array[1].compareTo(array[3], CompareCriteria.TITLE) < 0) return false;
        if (titleQueue.size() != 4) return false;

        levelQueue.enqueue(task2);
        levelQueue.enqueue(task1);
        levelQueue.enqueue(task3);
        levelQueue.enqueue(task5);
        array = levelQueue.getHeapData();
        if (array[0] != task1) return false;
        if (array[0].compareTo(array[1],CompareCriteria.LEVEL) < 0
                || array[0].compareTo(array[2], CompareCriteria.LEVEL) < 0) return false;
        if (array[1].compareTo(array[3], CompareCriteria.LEVEL) < 0) return false;
        if (levelQueue.size() != 4) return false;

        timeQueue.enqueue(task2);
        timeQueue.enqueue(task1);
        timeQueue.enqueue(task3);
        timeQueue.enqueue(task5);
        array = timeQueue.getHeapData();
        if (array[0] != task3) return false;
        if (array[0].compareTo(array[1],CompareCriteria.TIME) < 0
                || array[0].compareTo(array[2], CompareCriteria.TIME) < 0) return false;
        if (array[1].compareTo(array[3], CompareCriteria.TIME) < 0) return false;
        if (timeQueue.size() != 4) return false;


        try {
            fullQueue.enqueue(task2);
            fullQueue.enqueue(task1);
            return false;
        } catch (IllegalStateException e) {
            if (fullQueue.size() != 1) return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            task5.markCompleted();
            completeQueue.enqueue(task5);
            return false;
        } catch (IllegalArgumentException e) {
            if (!completeQueue.isEmpty()) return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    /**
     * Tests the dequeue() method in the TaskQueue class with respect to the time and level
     * comparisons
     *
     * @return false if any of the tests fail or true otherwise
     */
    public static boolean testDequeue() {
        Task task1 = new Task("A", "Default", 50, PriorityLevel.MEDIUM);
        Task task2 = new Task("B", "Default", 40, PriorityLevel.LOW);
        Task task3 = new Task("C", "Default", 10, PriorityLevel.HIGH);
        Task task4 = new Task("D", "Default", 15, PriorityLevel.OPTIONAL);
        Task task5 = new Task("a", "Default", 20, PriorityLevel.URGENT);
        TaskQueue levelQueue = new TaskQueue(5, CompareCriteria.LEVEL);
        TaskQueue titleQueue = new TaskQueue(5, CompareCriteria.TITLE);
        TaskQueue emptyQueue = new TaskQueue(1, CompareCriteria.TIME);
        Task[] array;

        levelQueue.enqueue(task5);
        levelQueue.enqueue(task2);
        levelQueue.enqueue(task3);
        levelQueue.enqueue(task4);
        levelQueue.enqueue(task1);

        if (!levelQueue.dequeue().equals(task5)) return false;
        array = levelQueue.getHeapData();
        if (array[0] != task3 || array[0].compareTo(array[1], CompareCriteria.LEVEL) < 0
                || array[0].compareTo(array[2],CompareCriteria.LEVEL) < 0
                || array[1].compareTo(array[3], CompareCriteria.LEVEL) < 0) return false;
        if (levelQueue.size() != 4) return false;
        if (array[4] != null) return false;

        titleQueue.enqueue(task5);
        titleQueue.enqueue(task1);
        titleQueue.enqueue(task4);
        titleQueue.enqueue(task3);
        titleQueue.enqueue(task2);

        if (!titleQueue.dequeue().equals(task1)) return false;
        array = titleQueue.getHeapData();
        if (array[0] != task2 || array[0].compareTo(array[1], CompareCriteria.TITLE) < 0
                || array[0].compareTo(array[2],CompareCriteria.TITLE) < 0
                || array[1].compareTo(array[3], CompareCriteria.TITLE) < 0) return false;
        if (levelQueue.size() != 4) return false;
        if (array[4] != null) return false;

        try {
            emptyQueue.dequeue();
            return false;
        } catch (NoSuchElementException e) {
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Tests the peekBest() method in the TaskQueue class to ensure its functionality
     *
     * @return false if any of the tests fail or true otherwise
     */
    public static boolean testPeek() {
        Task task1 = new Task("Default", "Default", 10, PriorityLevel.OPTIONAL);
        Task task2 = new Task("Default", "Default", 20, PriorityLevel.OPTIONAL);
        TaskQueue testerQueue = new TaskQueue(4, CompareCriteria.TIME);
        Task[] array;

        try {
            testerQueue.peekBest();
            return false;
        } catch (NoSuchElementException e) {
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        testerQueue.enqueue(task1);
        testerQueue.enqueue(task2);
        array = testerQueue.getHeapData();
        if (testerQueue.peekBest() != array[0]) return false;
        if (testerQueue.size() != 2) return false;

        return true;
    }

    /**
     * Tests the reprioritize() method in the TaskQueue class and reprioritizes the queue with
     * respect to the level and time comparisons
     *
     * @return false if any of the tests fail or true otherwise
     */
    public static boolean testReprioritize() {
        Task task1 = new Task("A", "Default", 60, PriorityLevel.OPTIONAL);
        Task task2 = new Task("B", "Default", 30, PriorityLevel.MEDIUM);
        Task task3 = new Task("C", "Default", 20, PriorityLevel.LOW);
        TaskQueue testerQueue = new TaskQueue(4, CompareCriteria.TITLE);
        Task[] array;
        testerQueue.enqueue(task1);
        testerQueue.enqueue(task2);
        testerQueue.enqueue(task3);

        testerQueue.reprioritize(CompareCriteria.LEVEL);
        array = testerQueue.getHeapData();
        if (array[0].compareTo(array[1], CompareCriteria.LEVEL) < 0
                || array[0].compareTo(array[2], CompareCriteria.LEVEL) < 0) return false;

        testerQueue.reprioritize(CompareCriteria.TIME);
        array = testerQueue.getHeapData();
        if (array[0].compareTo(array[1], CompareCriteria.TIME) < 0
                || array[0].compareTo(array[2], CompareCriteria.TIME) < 0) return false;

        return true;
    }


    /**
     * Runs all the methods and prints the result to the console
     * @param args (unused)
     */
    public static void main(String[] args) {
        System.out.println("Test Cases:");
        System.out.println("testCompareToTime(): " + testCompareToTime());
        System.out.println("testCompareToTitle(): " + testCompareToTitle());
        System.out.println("testCompareToLevel(): " + testCompareToLevel());
        System.out.println("testEnqueue(): " + testEnqueue());
        System.out.println("testDequeue(): " + testDequeue());
        System.out.println("testPeek(): " + testPeek());
        System.out.println("testReprioritize(): " + testReprioritize());
    }
}
