package implementation.benchmark;

public class Timer {

  public long measureTime(Runnable task) {
    long startTime = System.nanoTime();
    task.run();
    long endTime = System.nanoTime();
    return (endTime - startTime) / 1000000; // milissegundos
  }
}
