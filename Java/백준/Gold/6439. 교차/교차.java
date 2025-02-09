//1시간 30분 소요

import java.util.*;
import java.io.*;

public class Main {
    static double epsilon = 0.000001;
    static boolean isInRange(int a, int b, int v) {
        return a <= v + epsilon && v - epsilon <= b;
    }
    static boolean isInRange(double a, double b, double v) {
        return a <= v + epsilon && v - epsilon <= b;
    }
    
    public static void main(String[] args) throws IOException {
        var br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        while (T-- != 0) {
            var st = new StringTokenizer(br.readLine());
            int[] input = new int[8];
            int idx = 0;
            while (idx != 8)
                input[idx++] = Integer.parseInt(st.nextToken());
            
            int rectLeft = Math.min(input[4], input[6]);
            int rectRight = Math.max(input[4], input[6]);
            int rectBottom = Math.min(input[5], input[7]);
            int rectTop = Math.max(input[5], input[7]);
            
            int lineLeft = Math.min(input[0], input[2]);
            int lineRight = Math.max(input[0], input[2]);
            int lineBottom = Math.min(input[1], input[3]);
            int lineTop = Math.max(input[1], input[3]);
            
            boolean isInRectLeft = isInRange(rectLeft, rectRight, lineLeft);
            boolean isInRectRight = isInRange(rectLeft, rectRight, lineRight);
            boolean isInRectTop = isInRange(rectBottom, rectTop, lineTop);
            boolean isInRectBottom = isInRange(rectBottom, rectTop, lineBottom);
            
            boolean isLineXSame = input[0] == input[2];
            if (isLineXSame) {
                //y를 x에 대한 식으로 표현 불가능한 경우
                if (isInRectLeft && lineTop >= rectBottom && lineBottom <= rectTop)
                    System.out.println("T");
                else
                    System.out.println("F");
                continue;
            }
            
            double ratio = (double)(input[3] - input[1]) / (input[2] - input[0]);
            double intercept = input[1] - ratio * input[0];
            double rectLeftCoord = ratio * rectLeft + intercept;
            double rectRightCoord = ratio * rectRight + intercept;
            double rectTopCoord = (rectTop - intercept) / ratio;
            double rectBottomCoord = (rectBottom - intercept) / ratio;
            
            double[][] points = new double[][]{
                {rectLeft, rectLeftCoord, input[0], input[2], rectLeft},
                {rectRight, rectRightCoord, input[0], input[2], rectRight},
                {rectTopCoord, rectTop, input[1], input[3], rectTop},
                {rectBottomCoord, rectBottom, input[1], input[3], rectBottom}
            };
            boolean result = false;
            for (double[] point : points) {
                if (!isInRange(rectLeft, rectRight, point[0]))
                    continue;
                if (!isInRange(rectBottom, rectTop, point[1]))
                    continue;
                if (isInRange(point[2], point[3], point[4])
                    || isInRange(point[3], point[2], point[4])) {
                    result = true;
                    break;
                }
            }
            if (!result)
                if (lineLeft >= rectLeft && lineRight <= rectRight)
                    if (lineTop <= rectTop && lineBottom >= rectBottom)
                        result = true;
            System.out.println(result ? "T" : "F");
        }
    }
}