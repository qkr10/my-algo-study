/*
1. 08:20 부터 풀이 시작
2. 테스트에서 0만 나오는 버그발견
3. 09:00 ideal.compareTo(real) >= 0; // 이 코드에서 부등호 방향이 반대였음
4. 09:05 int saturday = (12 - startday) % 7; // 이 코드에서 ()안이 startday + 5 였음
5. 09:17 for (int employee = 0; employee < schedules.length; employee++) // < 가 == 였음
*/

import java.io.*;

class Solution {
    static class Time implements Comparable<Time> {
        private int hour, minute, time;
        public Time(int time) {
            hour = time / 100;
            minute = time % 100;
            this.time = time;
        }
        public void add(int minute) {
            this.minute += minute;
            if (this.minute >= 60) {
                hour += this.minute / 60;
                this.minute %= 60;
            }
            this.time = hour * 100 + this.minute;
        }
        public int compareTo(Time o) {
            if (time > o.time)
                return 1;
            else if (time == o.time)
                return 0;
            return -1;
        }
    }
    
    private boolean isEligibleForEvent(int schedule, int[] logs, int startday) {
        int saturday = (12 - startday) % 7;
        boolean result = true;
        Time ideal = new Time(schedule);
        ideal.add(10);
        for (int i = 0; i < 7; i++) {
            if (saturday == i || (saturday + 1) % 7 == i)
                continue;
            Time real = new Time(logs[i]);
            result &= ideal.compareTo(real) >= 0;
        }
        return result;
    }
    
    public int solution(int[] schedules, int[][] timelogs, int startday) {
        int answer = 0;
        for (int employee = 0; employee < schedules.length; employee++)
            if (isEligibleForEvent(schedules[employee], timelogs[employee], startday-1))
                answer++;
        return answer;
    }
}