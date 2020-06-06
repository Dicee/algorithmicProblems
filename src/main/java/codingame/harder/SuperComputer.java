package codingame.harder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SuperComputer {
	private static class Task implements Comparable<Task> {
		public int begin, duration;
		
		public Task(int begin, int end) {
			this.begin    = begin;
			this.duration = end;
		}

		@Override
		public int compareTo(Task t) {
			return Integer.compare(duration + begin,t.duration + t.begin);
		}
	}
	public static void main(String[] args) {
		Scanner in       = new Scanner(System.in);
		int n            = in.nextInt();
		List<Task> tasks = new ArrayList<>();
		
		for (int i=0 ; i<n ; i++)
			tasks.add(new Task(in.nextInt(),in.nextInt()));
		in.close();
		
		Collections.sort(tasks);
		
		int count       = 0;
		int availableOn = 0;
		for (Task task : tasks) 
			if (task.begin >= availableOn) {
				count++;
				availableOn = task.duration + task.begin;
			}
		System.out.println(count);
	}
}
