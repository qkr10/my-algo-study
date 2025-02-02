# [Gold I] Is-A? Has-A? Who Knowz-A? - 15089 

[문제 링크](https://www.acmicpc.net/problem/15089) 

### 성능 요약

메모리: 102236 KB, 시간: 1880 ms

### 분류

플로이드–워셜, 그래프 이론, 그래프 탐색, 최단 경로

### 제출 일자

2025년 2월 2일 18:42:15

### 문제 설명

<p>Two familiar concepts in object oriented programming are the is-a and has-a relationships. Given two classes A and B, we say that A is-a B if A is a subclass of B; we say A has-a B if one of the fields of A is of type B. For example, we could imagine an object-oriented language (call it ICPC++) with code like that in Figure E.1, where the class <code>Day</code> is-a <code>Time</code>, the class <code>Appointment</code> is both a <code>DateBook</code> and a <code>Reminder</code>, and class <code>Appointment</code> has-a <code>Day</code>.</p>

<pre>class Day extends Time        class Appointment extends Datebook, Reminder
{                             {
    ...                           private Day date;
}                                 ...
                              }</pre>

<p style="text-align:center">Figure E.1: Two ICPC++ classes.</p>

<p>These two relationships are transitive. For example if A is-a B and B is-a C then it follows that A is-a C. This holds as well if we change all the is-a’s in the last sentence to has-a’s. It also works with combinations of is-a’s and has-a’s: in the example above, <code>Appointment</code> has-a <code>Time</code>, since it has-a <code>Day</code> and Day is-a <code>Time</code>. Similarly, if class <code>DateBook</code> has-a <code>Year</code> then <code>Appointment</code> has-a <code>Year</code>, since <code>Appointment</code> is-a <code>DateBook</code>.</p>

<p>In this problem you will be given a set of is-a and has-a relationships and a set of queries of the form A is/has-a B. You must determine if each query is true or false.</p>

### 입력 

 <p>Input starts with two integers n and m, (1 ≤ n, m ≤ 10 000), where n specifies the number of given is-a and has-a relationships and m specifies the number of queries. The next n lines each contain one given relationship in the form c<sub>1</sub> r c<sub>2</sub> where c<sub>1</sub> and c<sub>2</sub> are single-word class names, and r is either the string “is-a” or “has-a”. Following this are m queries, one per line, using the same format. There will be at most 500 distinct class names in the n + m lines, and all class names in the last m lines will appear at least once in the initial n lines. All is-a and has-a relationships between the given classes can be deduced from the n given relationships. Is-a relationships can not be circular (apart from the trivial identity “x is-a x”).</p>

### 출력 

 <p>For each query, display the query number (starting at one) and whether the query is true or false.</p>

